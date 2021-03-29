package de.tubs.skeditor.utils;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class CoreUtil {
	public static String DEFAULT_CONSOLE = "skeditor.console.default";

	public static MessageConsole getConsole(String name) {
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

	public static MessageConsole getConsole() {
		return getConsole(DEFAULT_CONSOLE);
	}

	public static void writeToConsole(String name, String content) {
		MessageConsole myConsole = getConsole(name);
		MessageConsoleStream out = myConsole.newMessageStream();
		out.println(content);
	}
	
	public static void writeToConsole(String content) {
		MessageConsole myConsole = getConsole();
		MessageConsoleStream out = myConsole.newMessageStream();
		out.println(content);
	}
}
