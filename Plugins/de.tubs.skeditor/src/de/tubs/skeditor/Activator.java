package de.tubs.skeditor;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "de.tubs.skeditor"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	/**
	 * Convenience method for easy and clean logging. All messages collected by this method will be written to the eclipse log file.
	 *
	 * Messages are only written to the error log, if the debug option is set for this plug-in
	 *
	 * @param message A message that should be written to the eclipse log file
	 */
	public void logInfo(String message) {
		log(IStatus.INFO, message, new Exception());
	}

	/**
	 * Convenience method for easy and clean logging of warnings. All messages collected by this method will be written to the eclipse log file.
	 *
	 * @param message A message that should be written to the eclipse log file
	 */
	public void logWarning(String message) {
		log(IStatus.WARNING, message, new Exception());
	}

	/**
	 * Convenience method for easy and clean logging of exceptions. All messages collected by this method will be written to the eclipse log file. The
	 * exception's stack trace is added to the log as well.
	 *
	 * @param message A message that should be written to the eclipse log file
	 * @param exception Exception containing the stack trace
	 */
	public void logError(String message, Throwable exception) {
		log(IStatus.ERROR, message, exception);
	}

	/**
	 * Convenience method for easy and clean logging of exceptions. All messages collected by this method will be written to the eclipse log file. The
	 * exception's stack trace is added to the log as well.
	 *
	 * @param exception Exception containing the stack trace
	 */
	public void logError(Throwable exception) {
		if (exception != null) {
			logError(exception.getMessage(), exception);
		}
	}
	
	public String getID() {
		return PLUGIN_ID;
	}

	/**
	 * Logging any kind of message.
	 *
	 * @param severity
	 * @param message
	 * @param exception
	 */
	private void log(int severity, String message, Throwable exception) {
		if (isDebugging()) {
			getLog().log(new Status(severity, getID(), message, exception));
		}
	}

	public void reportBug(int ticket) {
		logWarning("This is a bug. Please report it. See Ticket #" + ticket + ".");
	}
}
