package de.tubs.skeditor.simulation.airsim.core;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import SkillGraph.Category;
import SkillGraph.Graph;
import SkillGraph.Node;
import SkillGraph.Parameter;
import de.tubs.skeditor.keymaera.KeYmaeraBridge;
import de.tubs.skeditor.simulation.airsim.utils.SensorAndActuator;
import de.tubs.skeditor.simulation.airsim.utils.StateMachine;
import de.tubs.skeditor.simulation.airsim.utils.Structs;
import de.tubs.skeditor.simulation.plugin.core.ASimConfigGroup;
import de.tubs.skeditor.simulation.plugin.core.ASimulatorFactory;
import de.tubs.skeditor.utils.FileUtils;

/**
 * This factory creates an AirSim project out of a Skill Graph.
 * The project can be run in an AirSim simulation environment and should only be used for cars.
 * -HOW IT WORKS-
 * The generated controller code is mapped to a single main.cpp file
 * The parameters, states and inputs lists of (possibly several) state machines are merged
 * The State Machines (keymaera generated code) are put into 'ctrlStep_(..)' methods
 * Instead of having an executable for every single sensor and actuator this class generates a method for each of them. (to run their code in separate threads at the same time)
 * Adds 'INSERT CODE HERE' annotation - The users have to implement the use of sensors and actuator by themselves
 * The 'int main()' contains declarations and default values.
 * -> Furthermore it contains AirSim API calls, the sensor and actuator methods are called in separate threads and it contains a loop to call the state machine steps
 * The generated main.cpp is based on the HelloCar Example from AirSim GitHub
 */
public class AirsimFactory extends ASimulatorFactory {

	private AirsimFactory instance;
	private PrintStream out;
	private IProgressMonitor monitor;
	private File resourcesDir;
	private static final String AIRSIM_FOLDER_PATH_PLACEHOLDER = "XXX_AIRSIM_FOLDER_XXX";

	@Override
	public ASimulatorFactory getInstance() {
		if (instance == null) {
			instance = new AirsimFactory();
		}
		return instance;
	}

	@Override
	public ASimConfigGroup buildSimConfigGroup(Composite parent, Runnable callback) {
		return new AirsimConfigGroup(parent, SWT.NONE, callback);
	}

	/**
	 * Creates a project to run in AirSim (based on a given Skill Graph)
	 */
	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		
		MessageConsole console = findConsole("Skill");
		console.activate();
		console.clearConsole();
		out = new PrintStream(console.newOutputStream());
		this.monitor = monitor;

		out.println("===Generating Project...===");
		out.println("");

		initRecourceFolder(); 

		String skedPath = configuration.getAttribute(AirsimLaunchConfigAttributes.SKED_PATH, (String) null);
		String project = configuration.getAttribute(AirsimLaunchConfigAttributes.PROJECT_PATH, (String) null);
		String airsim = configuration.getAttribute(AirsimLaunchConfigAttributes.AIRSIM_PATH, (String) null);

		try {
			if (project.endsWith("CMakeLists.txt")) {
				int chop = project.length() - ("\\CMakeLists.txt").length();
				project = project.substring(0, chop);
			}
			
			Path projectPath = Paths.get(project);
			
			initProjectFolder(projectPath);
			
			// point to the folder and not to the file
			if (airsim.endsWith("AirSim.sln")) {
				int chop = airsim.length() - ("\\AirSim.sln").length();
				airsim = airsim.substring(0, chop);
				airsim = airsim.replace("\\", "/");
			}
			
			insertAirsimRepoPath(projectPath, airsim);

			Graph graph = parseSked(skedPath);

			// get default values from graph
			Map<String, String> params = parseParameters(graph);

			// get all stateMachines from graph
			List<StateMachine> stateMachines = getStatemachines(graph);

			// merge the structs for the stateMachines
			Structs structs = new Structs();
			stateMachines.forEach(sm -> structs.parseCCode(sm));

			List<SensorAndActuator> sensorAndActuators = getSensorAndActuatorNames(graph);

			StringBuilder sb = new StringBuilder();

			writeIncludes(sb);
			structs.writeDefinitions(sb);
			writeStateMachineDefinitions(sb, stateMachines);
			writeSensorAndActuatorDefinitions(sb, sensorAndActuators);
			writeMain(sb, params, structs, sensorAndActuators, stateMachines);

			writeToFile(sb, projectPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.println("===Success!===");
	}

	@Override
	public void cleanAfterClose() {
		// No implementation required
	}

	/**
	 * Makes the resource folder in eclipse accessible
	 */
	private void initRecourceFolder() {
		if (resourcesDir == null) {
			Bundle bundle = FrameworkUtil.getBundle(this.getClass());
			URL fileURL = bundle.getEntry("resources");
			try {
				resourcesDir = new File(FileLocator.resolve(fileURL).toURI());
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * Checks if the project folder exists and recursively copies project template from resources
	 * @param projectPath
	 * @throws IOException
	 */
	private void initProjectFolder(Path projectPath) throws IOException {
		if (!Files.exists(projectPath)) {
			Files.createDirectories(projectPath);
		}

		FileUtils.copyFolder(resourcesDir.getAbsolutePath(), projectPath.toAbsolutePath().toString());
	}

	/**
	 * Sets the path to AirSim repo files in CMakeLists.txt of the project
	 * @param projectPath
	 * @param repoPath
	 * @throws IOException
	 */
	private void insertAirsimRepoPath(Path projectPath, String repoPath) throws IOException {
		FileUtils.replaceInFile(projectPath.resolve("CMakeLists.txt").toAbsolutePath().toString(),
				AIRSIM_FOLDER_PATH_PLACEHOLDER, repoPath);
	}

	/**
	 * Parse the Skill Graph from the .sked file and returns a Graph object
	 * @param skedFile
	 * @return
	 * @throws IOException
	 */
	private Graph parseSked(String skedFile) throws IOException {
		ResourceSet rs = new ResourceSetImpl();
		URI uri = URI.createFileURI(skedFile);
		TransactionalEditingDomain editingDomain = TransactionUtil.getEditingDomain(rs);
		if (editingDomain == null) {
			editingDomain = TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain(rs);
		}

		Resource r = rs.getResource(uri, true);
		r.load(null);

		for (Object o : r.getContents()) {
			if (o instanceof Graph) {
				return (Graph) o;
			}
		}

		return null;
	}

	/**
	 * Puts the parameters of the Skill Graph into a map
	 * @param graph
	 * @return
	 */
	private Map<String, String> parseParameters(Graph graph) {
		Map<String, String> parameters = new HashMap<>();
		out.println("> Parameter List");
		for (Parameter param : graph.getParameterList()) {
			String value = param.getDefaultValue().trim();
			if (value.isEmpty()) {
				System.err.println("Warning " + param.getAbbreviation() + " has no default value!");
			}
			parameters.put(param.getAbbreviation().trim(), value);
			out.println(param.getAbbreviation());
		}
		out.println("");
		return parameters;
	}

	/**
	 * Creates a list of controller code
	 * @param graph
	 * @return
	 */
	private List<StateMachine> getStatemachines(Graph graph) {
		List<StateMachine> stateMachines = new ArrayList<>();
		for (Node node : graph.getNodes()) {
			if (node.getController() != null) {
				for (int i = 0; i < node.getController().size(); i++) {
					String name = node.getName().replace(' ', '_');
					System.out.println("Control: " + node.getController().get(i).getCtrl());
					String ctrl = node.getController().get(i).getCtrl(); // controller code

					stateMachines.add(new StateMachine(name, ctrl));
				}
			}
		}
		return stateMachines;
	}

	/**
	 * Creates a list of Nodes from type 'Sensor' or 'Actuator' (retrieving names only)
	 * @param graph
	 * @return
	 */
	private List<SensorAndActuator> getSensorAndActuatorNames(Graph graph) {
		List<SensorAndActuator> sensorAndActuators = new ArrayList<>();

		out.println("> Nodes");
		for (Node node : graph.getNodes()) {

			// Controller node
			if (node.getCategory().equals(Category.ACTION) || node.getCategory().equals(Category.MAIN)
					|| node.getCategory().equals(Category.OBSERVABLE_EXTERNAL_BEHAVIOR)) {
				continue;
			}

			sensorAndActuators.add(new SensorAndActuator(node.getName().replace(' ', '_')));
			out.println(node.getName());
		}
		out.println("");
		return sensorAndActuators;
	}

	/**
	 * Writes the includes of .cpp file
	 * @param sb
	 */
	private void writeIncludes(StringBuilder sb) {
		// @formatter:off
		sb.append("#include \"common/common_utils/StrictMode.hpp\"\n" 
				+ "STRICT_MODE_OFF\n" 
				+ "#ifndef RPCLIB_MSGPACK\n"
				+ "#define RPCLIB_MSGPACK clmdep_msgpack\n" 
				+ "#endif // !RPCLIB_MSGPACK\n"
				+ "#include \"rpc/rpc_error.h\"\n" 
				+ "STRICT_MODE_ON\n" 
				+ "\n"
				+ "#include \"vehicles/car/api/CarRpcLibClient.hpp\"\n" 
				+ "#include <iostream>\n" 
				+ "\n"
				+ "#include \"Skill.h\"\n");
		// @formatter:on
	}

	/**
	 * Writes the State Machines
	 * @param sb
	 * @param stateMachines
	 */
	private void writeStateMachineDefinitions(StringBuilder sb, List<StateMachine> stateMachines) {
		// @formatter:off
		sb.append("/*\n" 
				+ " * #########################################\n" 
				+ " * START OF KEYMAERAX GENERATED STATEMACHINE\n"
				+ " * #########################################\n" 
				+ " */\n");

		stateMachines.forEach(s -> s.writeDefinition(sb));

		sb.append("/*\n" 
				+ " * #######################################\n" 
				+ " * END OF KEYMAERAX GENERATED STATEMACHINE\n"
				+ " * #######################################\n" 
				+ " */\n\n");
		// @formatter:on
	}

	/**
	 * Writes the sensor and actuator methods
	 * @param sb
	 * @param sensorAndActuatos
	 */
	private void writeSensorAndActuatorDefinitions(StringBuilder sb, List<SensorAndActuator> sensorAndActuatos) {
		// @formatter:off
		sb.append("/*\n" 
				+ " * ######################################\n" 
				+ " * GENERATE SENSOR AND ACTUATOR FUNCTIONS\n"
				+ " * ######################################\n" 
				+ " */\n");

		sensorAndActuatos.forEach(s -> s.writeDefinition(sb));

		sb.append("/*\n" 
				+ " * ##########################################\n"
				+ " * END GENERATE SENSOR AND ACTUATOR FUNCTIONS\n" 
				+ " * ##########################################\n"
				+ " */\n\n");
		// @formatter:on
	}

	/**
	 * Writes the main method
	 * @param sb
	 * @param params
	 * @param structs
	 * @param sensorAndActuators
	 * @param stateMachines
	 */
	private void writeMain(StringBuilder sb, Map<String, String> params, Structs structs,
			List<SensorAndActuator> sensorAndActuators, List<StateMachine> stateMachines) {
		// @formatter:off
		sb.append("int main()\n" 
				+ "{\n" 
				+ "    parameters params = {};\n" 
				+ "    state states = {};\n"
				+ "    input inputs = {};\n" 
				+ "\n");

		structs.writeMain(sb, params);

		sb.append("    using namespace msr::airlib;\n" 
				+ "\n" 
				+ "    msr::airlib::CarRpcLibClient client;\n" 
				+ "\n"
				+ "    try {\n" 
				+ "        client.confirmConnection();\n" 
				+ "\n" 
				+ "        //enable API control\n"
				+ "        client.enableApiControl(true);\n" 
				+ "\n");

		sb.append("        /*\n" 
				+ "         * ####################################\n"
				+ "         * GENERATE SENSOR AND ACTUATOR THREADS\n"
				+ "         * ####################################\n" 
				+ "         */\n");

		sensorAndActuators.forEach(s -> s.writeThread(sb));

		sb.append("        /*\n" 
				+ "         * ########################################\n"
				+ "         * END GENERATE SENSOR AND ACTUATOR THREADS\n"
				+ "         * ########################################\n" 
				+ "         */\n\n");

		sb.append("        while (loop()) {\n");
		sb.append("            /*\n" 
				+ "             * ########################\n" 
				+ "             * START CALL STATEMACHINES\n"
				+ "             * ########################\n" 
				+ "             */\n");

		stateMachines.forEach(s -> s.writeCall(sb));

		sb.append("            /*\n" 
				+ "             * ######################\n" 
				+ "             * END CALL STATEMACHINES\n"
				+ "             * ######################\n" 
				+ "             */\n\n");

		sb.append("        }\n" 
				+ "    }\n" 
				+ "    catch (rpc::rpc_error& e) {\n"
				+ "        std::string msg = e.get_error().as<std::string>();\n"
				+ "        std::cout << \"Exception raised by the API, something went wrong.\" << std::endl\n"
				+ "        << msg << std::endl;\n" 
				+ "        std::cin.get();\n" 
				+ "    }\n" 
				+ "\n" 
				+ "    return 0;\n"
				+ "}\n");
		// @formatter:on
	}
	
	/**
	 * Writes the main.cpp
	 * @param sb
	 * @param projectPath
	 * @throws IOException
	 */
	void writeToFile(StringBuilder sb, Path projectPath) throws IOException {
		Path mainCpp = projectPath.resolve("main.cpp");
		
		// backup old main.cpp if it already exists
		if (Files.exists(mainCpp)) {
			int i = 0;
			Path bakPath;
			do {
				i++;
				bakPath = projectPath.resolve(String.format("main.cpp.%d.bak", i));
			} while(Files.exists(bakPath));
				
			Files.move(mainCpp, bakPath);
		}
		
		//Files.writeString(mainCpp, sb);
		Files.write(mainCpp, sb.toString().getBytes());

	}
	
	private MessageConsole findConsole(String name) {
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		for (int i = 0; i < existing.length; i++)
			if (name.equals(existing[i].getName()))
				return (MessageConsole) existing[i];
		// no console found, so create a new one
		MessageConsole myConsole = new MessageConsole(name, null);
		conMan.addConsoles(new IConsole[] { myConsole });
		return myConsole;
	}
}
