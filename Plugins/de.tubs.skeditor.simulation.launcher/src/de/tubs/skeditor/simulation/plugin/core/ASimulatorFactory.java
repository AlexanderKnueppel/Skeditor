package de.tubs.skeditor.simulation.plugin.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.swt.widgets.Composite;

public abstract class ASimulatorFactory {
	
	/**
	 * Singleton Pattern
	 */
	public abstract ASimulatorFactory getInstance();

	/**
	 * Creates an instance of a simulators configuration group.
	 */
	public abstract ASimConfigGroup buildSimConfigGroup(Composite parent, Runnable callBack);

	/**
	 * To launch the simulator application
	 */
	public abstract void launch(ILaunchConfiguration configuration, String mode, ILaunch launch,
			IProgressMonitor monitor) throws CoreException;
	
	/**
	 * To clean processes that run outside of eclipse
	 */
	public abstract void cleanAfterClose();

}
