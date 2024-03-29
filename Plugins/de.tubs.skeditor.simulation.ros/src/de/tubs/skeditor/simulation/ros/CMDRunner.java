package de.tubs.skeditor.simulation.ros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;

import de.tubs.skeditor.simulation.ros.core.RosLaunchConfigAttributes;
import de.tubs.skeditor.utils.FileUtils;

public class CMDRunner {
	
	public static void main(String[] args) {
//		final ArrayList<String> command = new ArrayList<String>();
//		System.out.println("Batch file path : " + new File("resources/ros_cmd_win64.sh").getAbsolutePath());
//		command.add("cmd");
//		command.add("/c");
//		//command.add("start");
//		command.add("cmd.exe");
//		command.add("/c");
//		command.add("\"C:\\Program Files (x86)\\Microsoft Visual Studio\\2019\\Community\\Common7\\Tools\\VsDevCmd.bat\" -arch=amd64 -host_arch=amd64 && set ChocolateyInstall=c:\\opt\\chocolatey && c:\\opt\\ros\\noetic\\x64\\setup.bat");
//		command.add("&& cd F:\\Homeoffice\\skeditor\\Ros\\catkin_ws\\build\\Estimate_motion");
//		command.add("&& devenv Estimate_motion.sln /build");
//		final ProcessBuilder builder = new ProcessBuilder(command).inheritIO();
//		try {
//			builder.redirectErrorStream(true);
//			Process p = builder.start();
//			try {
//				p.waitFor();
//				p.destroy();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			//builder.
//		} catch (IOException e) {
//			System.out.println("Could not start process: " + e.getMessage());
//		}

//		String name = "Estimate_motion";
//		File buildPath = new File("F:\\Homeoffice\\skeditor\\Ros\\catkin_ws\\build\\Estimate_motion");
//
//		String command = "make";
//
//		if (System.getProperty("os.name").toLowerCase().startsWith("windows"))
//			command = "devenv " + name + ".sln /build";
//
//		try {
//
//			if (CMDRunner.cmd("cd devel && setup.bat && cd.. && roslaunch myrobot_gazebo world.launch").logFile("GAZEBO.log").dir("F:\\Homeoffice\\skeditor\\Ros\\catkin_ws")
//					.runBlocking() != 0) {
//				throw new LaunchException(
//						"hmmm");
//			}
//		} catch (LaunchException | IOException e) {
//
//		}

		try {
			FileUtils.copyFromResource("resources/project_template/CMakeLists.txt",
					"F:\\Homeoffice\\skeditor\\Ros\\catkin_ws\\src\\Control_Robot/CMakeLists.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// private static String WINDOWS_COMMAND_LINE = "C:\\Program Files
	// (x86)\\Microsoft Visual
	// Studio\\2019\\Community\\Common7\\Tools\\VsDevCmd.bat";
	private static ExecutorService EXECUTOR = Executors.newCachedThreadPool();
	private static List<Runnable> SCHEDULED_TASKS = new ArrayList();

	private String path;
	private String cmd;
	private String logFile;
	private MessageConsoleStream out;
	private MessageConsole console;

	public static ILaunchConfiguration configuration;
	public static String workingDirectory;

	private CMDRunner() {
	}

	public static CMDRunner cmd(String cmd) {
		CMDRunner instance = new CMDRunner();
		instance.cmd = cmd;
		return instance;
	}

	public CMDRunner dir(String path) {
		this.path = path;
		return this;
	}

	public CMDRunner logFile(String log) {
		if (out != null)
			throw new RuntimeException("cannot use console() and logFile() for the same CMDRunner");
		this.logFile = log;
		return this;
	}

	public int runBlocking() throws IOException, CoreException {
		boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

		ProcessBuilder pb = null;
		if (isWindows) {
			final ArrayList<String> command = new ArrayList<String>();
			command.add("cmd");
			command.add("/c");
			// command.add("start");
			command.add("cmd.exe");
			command.add("/c");
//			command.add(
//					"\"C:\\Program Files (x86)\\Microsoft Visual Studio\\2019\\Community\\Common7\\Tools\\VsDevCmd.bat\" -arch=amd64 -host_arch=amd64 && set ChocolateyInstall=c:\\opt\\chocolatey && c:\\opt\\ros\\noetic\\x64\\setup.bat && "
//							+ cmd);
//			command.add(
//					"\"C:\\Program Files (x86)\\Microsoft Visual Studio\\2019\\Community\\Common7\\Tools\\VsDevCmd.bat\" -arch=amd64 -host_arch=amd64 && C:\\Users\\dibol\\gazebo\\devel\\setup.bat && "
//							+ cmd);
			// && set ChocolateyInstall=C:\\ProgramData\\chocolatey

			// String vscCmdPath =
			// configuration.getAttribute(RosLaunchConfigAttributes.VSC_CMD_PATH, "");
			// String setupPath = workingDirectory + "\\devel\\setup.bat";
			
			if (Paths.get(workingDirectory, "devel", "setup.bat").toFile().exists() /*&& !cmd.equals("catkin_make")*/) {
				command.add("\"" + configuration.getAttribute(RosLaunchConfigAttributes.VSC_CMD_PATH, (String) null)
				+ "\" -arch=amd64 -host_arch=amd64 && " + workingDirectory + "\\devel\\setup.bat" + " && " + cmd);	
			} else {
				command.add("\"" + configuration.getAttribute(RosLaunchConfigAttributes.VSC_CMD_PATH, (String) null)
				+ "\" -arch=amd64 -host_arch=amd64 && c:\\opt\\ros\\noetic\\x64\\setup.bat && " + cmd);
			}
			
			pb = new ProcessBuilder(command).redirectErrorStream(true);
		} else {
			pb = new ProcessBuilder("bash", "-ic", cmd).redirectErrorStream(true);
		}
		
		if (path != null) {
			pb = pb.directory(new File(path));
		}

		System.out.printf("start \"%s\" with args:%n%s%n", cmd, pb.command().stream().collect(Collectors.joining(" ")));
		Process p = pb.start();
		
		InputStream is = p.getInputStream();
		InputStream er = p.getErrorStream();
		
		if (out != null) {
			new StreamGobbler(is, out, cmd).start();
			new StreamGobbler(er, out, cmd).start();
		}

		if (logFile != null) {
			FileOutputStream fos = new FileOutputStream(new File(path + "/" + logFile));
			new StreamGobbler(is, fos, cmd).start();
			new StreamGobbler(er, fos, cmd).start();
		}

		try {
			int r = p.waitFor();
			System.out.printf("%s exit code: %d%n", cmd, r);
			return r;
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			p.destroy();
			return -1;
		}
	}

	public void run(String name) {
		if (EXECUTOR.isShutdown()) {
			EXECUTOR = Executors.newCachedThreadPool();
		}
		EXECUTOR.execute(new Runnable() {
			public void run() {
				console(name);
				try {
					out.println(name + " started");
					runBlocking();
					out.println(name + " ended");
				} catch (IOException e) {
					out.println(name + " failed");
					System.err.println(name + " failed");
					e.printStackTrace(new PrintStream(out));
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	public synchronized static void runAllSheduled() {
		for (Runnable runnable : SCHEDULED_TASKS) {
			runnable.run();
		}
		SCHEDULED_TASKS.clear();
	}

	public void scheduleTask(String name) {
		SCHEDULED_TASKS.add(new Runnable() {
			@Override
			public void run() {
				CMDRunner.this.run(name);
			}
		});
	}

	public static boolean shutdownAndWait(long time) throws InterruptedException {
		if (!EXECUTOR.isShutdown())
			EXECUTOR.shutdown();
		return EXECUTOR.awaitTermination(time, TimeUnit.MILLISECONDS);
	}

	public static boolean interruptAndWait() throws InterruptedException {
		EXECUTOR.shutdownNow();
		return EXECUTOR.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
	}

	public CMDRunner console(String name) {
		if (logFile != null)
			throw new RuntimeException("cannot use console() and logFile() for the same CMDRunner");
		ConsolePlugin plugin = ConsolePlugin.getDefault();
		IConsoleManager conMan = plugin.getConsoleManager();
		IConsole[] existing = conMan.getConsoles();
		console = null;
		for (int i = 0; i < existing.length; i++) {
			if (name.equals(existing[i].getName())) {
				console = ((MessageConsole) existing[i]);
			}
		}
		if (console == null) {
			console = new MessageConsole(name, null);
			// console.activate();
			console.clearConsole();
			conMan.addConsoles(new IConsole[] { console });
		}
		out = console.newMessageStream();
		return this;
	}

	private static class StreamGobbler extends Thread {
		InputStream is;
		OutputStream os;

		String prefix;
		
		private StreamGobbler(InputStream is, OutputStream os) {
			this(is, os, "");
		}
		
		private StreamGobbler(InputStream is, OutputStream os, String prefix) {
			this.is = is;
			this.os = os;
			this.prefix = prefix;
		}

		@Override
		public void run() {
			try {
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
				String line = null;
				while ((line = br.readLine()) != null) {
					if (line.contains("does not follow the naming conventions. It should start with a lower case letter and only contain lower case letters, digits, underscores, and dashes.")) {
						continue;
					}
					synchronized (os) {
						bw.write(String.format("%s%n", line));	
						bw.flush();	
					}
					System.out.printf("%s %s%n", prefix, line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}