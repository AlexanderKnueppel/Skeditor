package de.tubs.skeditor.simulation.ros;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import de.tubs.skeditor.keymaera.KeYmaeraBridge;
import de.tubs.skeditor.simulation.plugin.handle.LaunchException;
import de.tubs.skeditor.utils.FileUtils;

public class CodeGenerator {

	private final Set<String> parameters = new HashSet<>();
	private final Set<String> inputs = new HashSet<>();
	private final Set<String> states = new HashSet<>();

	private String generatedCppCode;
	private String hybridProgram;
	private File buildPath;
	private String name;
	private HashMap<String, String> graphParameters;

	public CodeGenerator(String name) {
		this.name = name;
	}

	public CodeGenerator setGraphParameters(HashMap<String, String> graphParameters) {
		this.graphParameters = graphParameters;
		return this;
	}

	public CodeGenerator setHybridProgram(String code) {
		hybridProgram = code;
		return this;
	}

	public CodeGenerator setBuildPath(String path) {
		this.buildPath = new File(path);
		buildPath.mkdir();
		return this;
	}

	public void build() throws LaunchException {
		try {
			System.out.println("Try to build");
			generatedCppCode = KeYmaeraBridge.getInstance().generateCCode(hybridProgram).get(0);
			parseCCode();
			generateCProject();
			//makeCProject();
			//return getExecutablePath();
		} catch (LaunchException e) {
			throw e;
		} catch (IOException e) {
			throw new LaunchException("Failed to generate Executable for kyx file due to (ioexception) " + e.getMessage());
		} catch (Exception e) {
			throw new LaunchException("Failed to generate Executable for kyx file due to " + e.getMessage());
		}
	}

//	private String getExecutablePath() {
//		return new File(buildPath, "build/devel/lib/" + name + "/" + name).getAbsolutePath();
//	}
//
//	private void makeCProject() throws IOException, InterruptedException {
//		File buildDir = new File(buildPath, "build");
//		if (!buildDir.mkdir()) {
//			throw new RuntimeException("Could not mkdir " + buildDir.getAbsolutePath());
//		}
//
//		CMDRunner.cmd("cmake ..").dir(buildDir.getAbsolutePath()).logFile("CMAKE.log").runBlocking();
//		CMDRunner.cmd("make").dir(buildDir.getAbsolutePath()).logFile("MAKE.log").runBlocking();
//	}

	private void generateCProject() throws IOException, LaunchException {
		FileUtils.deleteDirectory(buildPath);

		buildPath.mkdir();
		new File(buildPath, "src").mkdir();
		
		Bundle bundle = FrameworkUtil.getBundle(this.getClass()); // Platform.getBundle(de.tubs.skeditor.Activator.PLUGIN_ID);
		URL fileURL = bundle.getEntry("resources");
		File file = null;
		try {
		    file = new File(FileLocator.resolve(fileURL).toURI());
		} catch (URISyntaxException e1) {
		    e1.printStackTrace();
		} catch (IOException e1) {
		    e1.printStackTrace();
		}
		
		if(file == null)
			throw new LaunchException("Resouces do not exist!");

		FileUtils.copyFromResource(file.getAbsolutePath() + "/project_template/CMakeLists.txt", buildPath.getAbsolutePath() + "/CMakeLists.txt");
		FileUtils.copyFromResource(file.getAbsolutePath() + "/project_template/package.xml", buildPath.getAbsolutePath() + "/package.xml");
		FileUtils.copyFromResource(file.getAbsolutePath() + "/project_template/src/Skill.h", buildPath.getAbsolutePath() + "/src/Skill.h");

		FileUtils.replaceInFile(buildPath.getAbsolutePath() + "/CMakeLists.txt", "SKILL_NAME", name);
		FileUtils.replaceInFile(buildPath.getAbsolutePath() + "/package.xml", "SKILL_NAME", name);

		try (PrintWriter writer = new PrintWriter(new FileOutputStream(buildPath + "/src/main.cpp"))) {
			writer.println("#include \"Skill.h\"");
			writer.println("#include <string>");
			writer.println("#include <iostream>");
			writer.println("#include <math.h>");
			writer.println("#include <stdbool.h>");
			writer.println();
			writer.println();
			// parameters
			writer.println("typedef struct parameters {");
			this.parameters.forEach(p -> {
				writer.println("  long double " + p + ";");
			});
			writer.println("} Parameters;");
			writer.println();

			// states
			writer.println("typedef struct state {");
			this.states.forEach(p -> {
				writer.println("  long double " + p + " = 0;");
			});
			writer.println("} State;");
			writer.println();

			// input
			writer.println("typedef struct input {");
			this.inputs.forEach(p -> {
				writer.println("  long double " + p + " = 0;");
			});
			writer.println("} Input;");
			writer.println();

			int ctrlStepStart = generatedCppCode.indexOf("state ctrlStep(");
			writer.println("/* START OF KEYMAERAX GENERATED CODE */");
			writer.println(generatedCppCode.substring(ctrlStepStart).replace("state state;", "State state;"));
			writer.println("/* END OF KEYMAERAX GENERATED CODE */");

			writer.println("int main(int argc, const char **argv) {");

			// init structs
			writer.println("    parameters params;");
			writer.println("    state states;");
			writer.println("    input inputs;");
			writer.println("	memset(&states, 0, sizeof(states));");
			writer.println();

			if (parameters.contains("ep")) {
				writer.println("    params.ep = EP;");
				parameters.remove("ep");
				writer.println();
			}
			
			writer.println("    init(\"" + name.replace(" ", "_") + "\");");
			writer.println();

			for (String param : parameters) {
				String value = graphParameters.getOrDefault(param, null);
				if (value != null) {
					writer.println("    params." + param + " = " + value + ";");
				} else {
					//throw new LaunchException("could not find Parameter " + param + " in sked file");
					// KeYmaera X sometimes expects programvariables to be constant and moves them to parameters
					// but probably they are variable and set by other skills
					writer.println("    registerInputVar(\"" + param + "\", &params." + param + ");");
				}
			}

			// init
			writer.println("    init(\"" + name.replace(" ", "_") + "\");");
			writer.println();

			// init input variables
			states.forEach(state -> writer.println("    registerInputVar(\"" + state + "\", &states." + state + ");"));
			inputs.forEach(input -> writer.println("    registerInputVar(\"" + input + "\", &inputs." + input + ");"));
			writer.println();

			// init output variables
			states.forEach(state -> writer.println("    registerOutputVar(\"" + state + "\", &states." + state + ");"));
			writer.println();
			writer.println();

			// while loop
			writer.println("    while(loop()) {");
			writer.println("        states = ctrlStep(states, &params, &inputs);   ");
			writer.println("    }");

			writer.println();
			writer.println("    return 0;");
			writer.println("}");

		}
	}

	private void parseCCode() {
		Set<String> varType = null;
		for (String line : generatedCppCode.split("\n")) {
			if (line.contains("typedef struct state"))
				varType = states;
			if (line.contains("typedef struct input")) {
				if (!line.contains(";")) {
					varType = inputs;
				}
			}
			if (line.contains("typedef struct parameter"))
				varType = parameters;
			if (line.contains("}"))
				varType = null;

			if (varType != null && line.contains("long double")) {
				String varName = line.split("long double")[1].split(";")[0].trim();
				varType.add(varName);
			}
		}

	}
}
