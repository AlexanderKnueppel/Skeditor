package de.tubs.skeditor.simulation.ros.core;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;

import SkillGraph.Graph;

import java.io.PrintStream;
import java.io.File;
import java.io.IOException;

import java.net.URISyntaxException;
import java.net.URL;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import SkillGraph.Category;
import SkillGraph.Node;
import SkillGraph.Parameter;
import de.tubs.skeditor.simulation.plugin.core.ASimConfigGroup;
import de.tubs.skeditor.simulation.plugin.core.ASimulatorFactory;
import de.tubs.skeditor.simulation.plugin.handle.LaunchException;
import de.tubs.skeditor.utils.FileUtils;
import de.tubs.skeditor.simulation.ros.CMDRunner;
import de.tubs.skeditor.simulation.ros.CodeGenerator;

public class RosFactory extends ASimulatorFactory {

	private RosFactory instance;

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
	public ASimulatorFactory getInstance() {
		if (instance == null) {
			instance = new RosFactory();
		}
		return instance;
	}

	@Override
	public ASimConfigGroup buildSimConfigGroup(Composite parent, Runnable callback) {
		return new RosConfigGroup(parent, SWT.NONE, callback);
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

		Bundle bundle = FrameworkUtil.getBundle(this.getClass());
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
				File workspaceFolder = new File(catkinWorkspacePath); // creating folder
				
				try {
					buildWorkspace(workspaceFolder);
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (isWindows) {
					CMDRunner.cmd("roscore").dir(catkinWorkspacePath).run("roscore");
				} else {
					CMDRunner.cmd("/opt/ros/noetic/bin/roscore").dir(catkinWorkspacePath).run("roscore");
				}
				
				Thread.sleep(1000); // make sure roscore initialized

				parseSked(skedPath);

				buildPrograms(graph.getNodes());
				buildHybridPrograms(graph.getNodes());

				
				//Thread.sleep(10000); // make sure gazebo is inizialized

				compileAndRun(graph.getNodes());
				
				if (isWindows) {
					CMDRunner.cmd(/*"cd devel && setup.bat && cd.. && " + */"roslaunch myrobot_gazebo world.launch --wait")
							.dir(catkinWorkspacePath).run("gazebo");
				} else {
					CMDRunner.cmd("rosrun gazebo_ros gazebo " + worldPath).dir(catkinWorkspacePath).run("gazebo");
				}
				
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
	
	@Override
	public void cleanAfterClose() {
		List<Node> nodeList = graph.getNodes();

		if (isWindows) {
			System.out.println("Cleaning " + nodeList.size() + " Nodes");
			Runtime rt = Runtime.getRuntime();
			for (Node node : nodeList) {
				String killTaskCommand = "taskkill /im " + node.getName().replaceAll(" ", "_") + ".exe /t /f";
				System.out.println(killTaskCommand);
				try {
					rt.exec("cmd /c " + killTaskCommand);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				rt.exec("cmd /c taskkill /im roslaunch.exe /t /f");
				rt.exec("cmd /c taskkill /im roscore.exe /t /f");
				rt.exec("cmd /c taskkill /im rosmaster.exe /t /f");
				rt.exec("cmd /c taskkill /im rosout.exe /t /f");
				rt.exec("cmd /c taskkill /im gzclient.exe /t /f");
				rt.exec("cmd /c taskkill /im gzserver.exe /t /f");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			System.err.println(e.getMessage());
			e.printStackTrace();
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
					String ctrl = node.getController().get(i).getCtrl(); // TODO Skill Definition
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

				if (resourcesDir == null)
					throw new LaunchException("Resouces do not exist!");

				try {
					FileUtils.copyFolder(resourcesDir.getAbsolutePath() + "/project_template/", pkg.getAbsolutePath());
					
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

		// File workspace = new File(catkinWorkspacePath);

		if (!workspace.exists()) {
			System.out.println("create workspace folder");
			workspace.mkdirs();
		}
		
		if (!new File(workspace.getAbsoluteFile() + "/.catkin_workspace").exists()) {
			out.print("does not exist... build " + catkinWorkspacePath + "... ");
			FileUtils.copyFolder(resourcesDir.getAbsolutePath() + "/project_root/", workspace.getAbsolutePath());
			
			System.out.println("run catkin_make");
			
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
			// cd devel && setup.bat && cd.. &&
			CMDRunner.cmd("rosrun " + name + " " + name).dir(catkinWorkspacePath)
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
						if (param.getDefaultValue().trim().isEmpty()) {
							System.err.println("Warning " + param.getAbbreviation() + " has no default value!"); // use 0 instead");
							out.println("Warning " + param.getAbbreviation() + " has no default value!"); // use 0 instead");
						//	parameters.put(param.getAbbreviation(), "0");
						} //else {
							parameters.put(param.getAbbreviation(), param.getDefaultValue());	
						//}
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
