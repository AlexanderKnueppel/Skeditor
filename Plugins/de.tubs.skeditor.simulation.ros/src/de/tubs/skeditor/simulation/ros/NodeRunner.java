package de.tubs.skeditor.simulation.ros;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.console.MessageConsoleStream;

import SkillGraph.Node;
import de.tubs.skeditor.simulation.plugin.handle.LaunchException;

public class NodeRunner {

	private Node node;
	private String name;
	private String buildPath;
	private String programPath;
	private MessageConsoleStream out;

	public NodeRunner(Node node) {
		this.node = node;
		this.name = node.getName().replace(" ", "_");
		this.programPath = node.getProgramPath();
	}

	public void buildAndRun(String buildPath) throws LaunchException, CoreException {
		this.buildPath = buildPath;
		new File(buildPath).mkdir();

		File program = new File(programPath);
		if (!program.exists()) {
			throw new LaunchException("Program (" + programPath + ") does not exist");
		}

		if (program.isFile()) {
			if (program.canExecute()) {
				out.println("Executable (" + program.getName() + ")");
			} else {
				throw new LaunchException("File (" + program.getName() + ") is not executable");
			}
		} else {
			initCMake();
			makeProject();
			execute();
		}

	}

	private void initCMake() throws LaunchException, CoreException {
		try {
			if (CMDRunner.cmd("cmake " + node.getProgramPath()).dir(buildPath).logFile("cmake.log")
					.runBlocking() != 0) {
				throw new LaunchException("Could not initialize cmake build directory, see "
						+ new File(buildPath, "SKEDITOR_CMAKE.log").getAbsolutePath());
			}
		} catch (IOException e) {
			throw new LaunchException("Could not initialize cmake build directory");
		}
	}

	private void makeProject() throws LaunchException, CoreException {

		try {
			if (CMDRunner.cmd("make").dir(buildPath).logFile("cmake.log").runBlocking() != 0) {
				throw new LaunchException(
						"Could not make project, see " + new File(buildPath, "SKEDITOR_MAKE.log").getAbsolutePath());
			}
		} catch (IOException e) {
			throw new LaunchException("could not make project");
		}
	}

	private void execute() throws LaunchException {
		File execPath = new File(buildPath, "devel/lib/" + name + "/" + name);

		if (!execPath.exists())
			throw new LaunchException("could not find executable " + execPath.getAbsolutePath());

		CMDRunner.cmd(execPath.getAbsolutePath()).dir(buildPath).logFile(name + ".log").run(name);
	}

	public NodeRunner withOutputStream(MessageConsoleStream out) {
		this.out = out;
		return this;
	}

}
