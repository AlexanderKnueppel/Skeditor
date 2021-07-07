package de.tubs.skeditor.simulation.plugin.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * An abstract class to create a GUI for Simulator Configurations.
 * Using a SWT Group to visually encapsulate each Simulator Configuration.
 */
public abstract class ASimConfigGroup extends Group {
	
	public ASimConfigGroup(Composite parent, int style) {
		super(parent, style);
	}
	
	/**
	 * To set the settings from persistent file to GUI components.
	 * @param configuration
	 * @throws CoreException
	 */
	public abstract void initializeAttributes(ILaunchConfiguration configuration) throws CoreException;
	
	/**
	 * To save settings to persistent file.
	 * @param configuration
	 * @throws CoreException
	 */
	public abstract void applyAttributes(ILaunchConfigurationWorkingCopy configuration) throws CoreException;
	
	/**
	 * A little hack to permit deriving from Group. 
	 * No implementation required!
	 */
	@Override
	protected void checkSubclass() {
		// allow subclass
	}

}
