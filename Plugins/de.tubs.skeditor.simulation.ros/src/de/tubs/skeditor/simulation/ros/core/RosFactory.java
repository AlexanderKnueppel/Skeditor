package de.tubs.skeditor.simulation.ros.core;

import java.io.PrintStream;
import java.util.HashMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.ui.console.MessageConsole;

import SkillGraph.Graph;

import java.io.File;
import java.io.IOException;
//import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;

//import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
//import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
//import org.eclipse.debug.core.ILaunch;
//import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
//import org.eclipse.emf.transaction.TransactionalEditingDomain;
//import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
//import org.eclipse.ui.console.MessageConsole;
import org.osgi.framework.Bundle;
//import scala.collection.immutable.HashMap;
import SkillGraph.Category;
//import SkillGraph.Graph;
import SkillGraph.Node;
import SkillGraph.Parameter;
import de.tubs.skeditor.simulation.core.ASimulatorFactory;
import de.tubs.skeditor.simulation.core.SettingsObject;
import de.tubs.skeditor.simulation.core.SimConfigGroup;
//import de.tubs.skeditor.simulation.launch.CMDRunner;
//import de.tubs.skeditor.simulation.launch.CodeGenerator;
//import de.tubs.skeditor.simulation.launch.LaunchConfigurationAttributes;
//import de.tubs.skeditor.simulation.launch.LaunchException;
import de.tubs.skeditor.utils.FileUtils;

//import de.tubs.skeditor.simulation.core.ASimulatorFactory;
//import de.tubs.skeditor.simulation.core.SettingsObject;
import de.tubs.skeditor.simulation.core.launch.LaunchConfigAttributes;
import de.tubs.skeditor.simulation.core.launch.LaunchException;
import de.tubs.skeditor.simulation.ros.CMDRunner;
import de.tubs.skeditor.simulation.ros.CodeGenerator;

public class RosFactory extends ASimulatorFactory {

	private String skedPath;
	private PrintStream out;
	private String catkinWorkspacePath;
	private String worldPath;
	private boolean isWindows;
	private File resourcesDir;

	private Graph graph;
	private HashMap<String, String> parameters = new HashMap<>();

	private IProgressMonitor monitor;
	
	@Override
	public SimConfigGroup buildSimConfigGroup(Composite parent) {
		return new RosConfigGroup(parent, 0);
	}

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		CMDRunner.configuration = configuration;
		MessageConsole console = findConsole("Skill");
		console.activate();
		console.clearConsole();
		out = new PrintStream(console.newOutputStream());
		this.monitor = monitor;

		out.println("===Attributes===");
		configuration.getAttributes().forEach((key, value) -> out.println(key + " = " + value));
		out.println("================");
		out.println();

		skedPath = configuration.getAttribute(RosLaunchConfigAttributes.SKED_PATH, (String) null);
		catkinWorkspacePath = configuration.getAttribute(RosLaunchConfigAttributes.CATKIN_WORKSPACE_PATH,
				(String) null);
		if (catkinWorkspacePath != null) {
			int chop = catkinWorkspacePath.length() - ("\\.catkin_workspace").length();
			catkinWorkspacePath = catkinWorkspacePath.substring(0, chop);
		}
		CMDRunner.workingDirectory = catkinWorkspacePath;
		
		worldPath = configuration.getAttribute(RosLaunchConfigAttributes.WORLD_PATH, (String) null);

		isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
		
		Bundle bundle = Platform.getBundle(de.tubs.skeditor.Activator.PLUGIN_ID);
		URL fileURL = bundle.getEntry("resources");
		try {
			resourcesDir = new File(FileLocator.resolve(fileURL).toURI());
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			try {
				File workspaceFolder = new File(catkinWorkspacePath);   // creating folder
				if (!workspaceFolder.exists()) {
					workspaceFolder.mkdirs();
				}
				
				if (isWindows) {
					CMDRunner.cmd("roscore").dir(catkinWorkspacePath).run("roscore");
				} else {
					CMDRunner.cmd("/opt/ros/noetic/bin/roscore").dir(catkinWorkspacePath).run("roscore");
				}
				
				try {
					buildWorkspace(workspaceFolder);
				} catch (IOException e) {
					e.printStackTrace();
				}

				Thread.sleep(1000); // make sure roscore initialized

				parseSked(skedPath);

				buildPrograms(graph.getNodes());
				buildHybridPrograms(graph.getNodes());

				if (isWindows) {
					CMDRunner.cmd("cd devel && setup.bat && cd.. && roslaunch myrobot_gazebo world.launch --wait")
							.dir(catkinWorkspacePath).run("gazebo");
				} else {
					CMDRunner.cmd("rosrun gazebo_ros gazebo " + worldPath).dir(catkinWorkspacePath).run("gazebo");
				}

				Thread.sleep(10000); // make sure gazebo is inizialized

				compileAndRun(graph.getNodes());
				out.println("====DONE====");

			} catch (LaunchException e) {
				out.println();
				out.println("Build failed: ");
				out.println(e.getMessage());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} finally {

			try {
				while (!CMDRunner.shutdownAndWait(1000)) {
					if (monitor.isCanceled()) {
						out.println("Stopping all processes");
						CMDRunner.interruptAndWait();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			out.println("All tasks are done, exiting launcher...");

		}
	}

	private void compileAndRun(EList<Node> nodes) throws LaunchException, CoreException {
		out.print("CATKIN_MAKE ");
		try {
			if (CMDRunner.cmd("catkin_make").dir(catkinWorkspacePath).logFile("SKEDITOR_CMAKE.log")
					.runBlocking() != 0) {
				throw new LaunchException("Could not run catkin_make in workspace directory, see "
						+ new File(catkinWorkspacePath, "SKEDITOR_CMAKE.log").getAbsolutePath());
			}
		} catch (IOException | LaunchException e) {
			throw new LaunchException("could not cmake project");
		}
		out.print("- OK");
		out.println();

		out.println("RUN PROGRAMS");
		for (Node node : nodes) {
			if (monitor.isCanceled())
				return;

			if (node.getCategory().equals(Category.MAIN)) {
				continue;
			}

			File execPath = new File(catkinWorkspacePath, "devel/lib/" + node.getName().replace(" ", "_") + "/"
					+ node.getName().replace(" ", "_") + (isWindows ? ".exe" : ""));

			if (execPath.exists())
				execute(node);

		}
	}

	private void buildHybridPrograms(EList<Node> nodes) throws LaunchException {
		for (Node node : nodes) {
			if (monitor.isCanceled())
				return;

			if (node.getController() != null) {
				for (int i = 0; i < node.getController().size(); i++) {
					String ctrl = node.getController().get(i).getCtrl(); // TODO
					String name = node.getName().replace(" ", "_");

					out.print("generate executable for kyx script");
					new CodeGenerator(name).setBuildPath(catkinWorkspacePath + "/src/" + name)
							.setGraphParameters(parameters).setHybridProgram(ctrl).build();
					out.println(" - RUN");
				}
			}
		}
	}

	private void buildPrograms(EList<Node> nodes) throws LaunchException {
		out.println();
		out.println("Building programs...");
		for (int i = 0; i < nodes.size(); i++) {
			if (monitor.isCanceled())
				return;

			Node node = nodes.get(i);
			String name = node.getName().replace(" ", "_");
			out.print(name);
			// out.print(" - ");

			// Controller node
			if (nodes.get(i).getCategory().equals(Category.ACTION) || nodes.get(i).getCategory().equals(Category.MAIN)
					|| nodes.get(i).getCategory().equals(Category.OBSERVABLE_EXTERNAL_BEHAVIOR)) {
				out.println(" - Skip");
				continue;
			}

			// Package does not exist yet!
			File pkg = new File(catkinWorkspacePath + "/src/" + name);
			if (!new File(pkg, "CMakeLists.txt").exists()) {
				out.print(" - DOES NOT EXIST ");

				pkg.mkdir();
				new File(pkg, "src").mkdir();

				

				if (resourcesDir == null)
					throw new LaunchException("Resouces do not exist!");

				try {
					FileUtils.copyFromResource(resourcesDir.getAbsolutePath() + "/project_template/CMakeLists.txt",
							pkg.getAbsolutePath() + "/CMakeLists.txt");
					FileUtils.copyFromResource(resourcesDir.getAbsolutePath() + "/project_template/package.xml",
							pkg.getAbsolutePath() + "/package.xml");
					FileUtils.copyFromResource(resourcesDir.getAbsolutePath() + "/project_template/src/Skill.h",
							pkg.getAbsolutePath() + "/src/Skill.h");
					FileUtils.copyFromResource(resourcesDir.getAbsolutePath() + "/project_template/src/main.cpp",
							pkg.getAbsolutePath() + "/src/main.cpp");

					FileUtils.replaceInFile(pkg.getAbsolutePath() + "/CMakeLists.txt", "SKILL_NAME", name);
					FileUtils.replaceInFile(pkg.getAbsolutePath() + "/package.xml", "SKILL_NAME", name);
					FileUtils.replaceInFile(pkg.getAbsolutePath() + "/src/main.cpp", "SKILL_NAME", name);
				} catch (IOException io) {
					io.printStackTrace();
					FileUtils.deleteDirectory(pkg);
					return;
				}
				out.println(" - CREATED");
			} else {
				out.println(" - ALREADY EXISTS (Skip)");
			}
		}
	}

	private void buildWorkspace(File workspace) throws LaunchException, IOException, CoreException {
		out.println();
		out.println("Check whether catkin workspace exists... ");

		//File workspace = new File(catkinWorkspacePath);
		
		if (!new File(catkinWorkspacePath + "/.catkin_workspace").exists()) {
			out.print("does not exist... build " + catkinWorkspacePath + "... ");
			FileUtils.copyFromResource(resourcesDir.getAbsolutePath() + "/.catkin_workspace", workspace.getAbsolutePath() + "/.catkin_workspace");
			String srcDir = workspace.getAbsolutePath() + "/src";
			new File(srcDir).mkdir();
			String stoDir = srcDir + "/Select_target_object";
			new File(stoDir).mkdir();
			new File(stoDir + "/src").mkdir();
			
			// Package does not exist yet!
			File pkg = new File(stoDir);
			if (!new File(pkg, "CMakeLists.txt").exists()) {
				out.print(" - DOES NOT EXIST ");

				pkg.mkdir();
				new File(pkg, "src").mkdir();


				if (resourcesDir == null)
					throw new LaunchException("Resources do not exist!");

				try {
					FileUtils.copyFromResource(resourcesDir.getAbsolutePath() + "/project_template/CMakeLists.txt",
							pkg.getAbsolutePath() + "/CMakeLists.txt");
					FileUtils.copyFromResource(resourcesDir.getAbsolutePath() + "/project_template/package.xml",
							pkg.getAbsolutePath() + "/package.xml");
					FileUtils.copyFromResource(resourcesDir.getAbsolutePath() + "/project_template/src/Skill.h",
							pkg.getAbsolutePath() + "/src/Skill.h");
					FileUtils.copyFromResource(resourcesDir.getAbsolutePath() + "/project_template/src/main.cpp",
							pkg.getAbsolutePath() + "/src/main.cpp");
				} catch (IOException io) {
					io.printStackTrace();
					FileUtils.deleteDirectory(pkg);
					return;
				}
				out.println(" - CREATED");
			} else {
				out.println(" - ALREADY EXISTS (Skip)");
			}


			try {
				CMDRunner.cmd("catkin_make").dir(workspace.getAbsolutePath()).runBlocking();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.println("Done!");
		} else if (!workspace.isDirectory()) {
			throw new LaunchException("Not a directory!");
		} else {
			out.println(catkinWorkspacePath + " exists!");
		}
	}

	private void execute(Node node) throws LaunchException {
		String name = node.getName().replace(" ", "_");

		out.print(name + " ");
		File buildPath = new File(this.catkinWorkspacePath);
		File execPath = new File(buildPath, "devel/lib/" + name + "/" + name + (isWindows ? ".exe" : ""));

		if (!execPath.exists())
			throw new LaunchException("could not find executable " + execPath.getAbsolutePath());

		if (isWindows) {
			CMDRunner.cmd("cd devel && setup.bat && cd.. && rosrun " + name + " " + name).dir(catkinWorkspacePath)
					.run(name);
		} else {
			CMDRunner.cmd(execPath.getAbsolutePath()).dir(buildPath.getAbsolutePath()).run(name);
		}

		out.println("- OK");
	}

	private void parseSked(String skedFile) throws LaunchException {
		try {
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
					graph = (Graph) o;
					for (Parameter param : graph.getParameterList()) {
						out.println("Found " + param.getAbbreviation() + "=" + param.getDefaultValue());
						parameters.put(param.getAbbreviation(), param.getDefaultValue());
					}
				}
			}

		} catch (IOException e) {
			throw new LaunchException("Could not load sked file");
		}
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
