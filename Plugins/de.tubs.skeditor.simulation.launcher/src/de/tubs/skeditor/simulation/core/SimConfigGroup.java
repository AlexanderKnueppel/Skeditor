package de.tubs.skeditor.simulation.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public abstract class SimConfigGroup extends Group {

	public SimConfigGroup(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}
	
	public abstract void initializeAttributes(ILaunchConfiguration configuration) throws CoreException;
	public abstract void applyAttributes(ILaunchConfigurationWorkingCopy configuration) throws CoreException;
	
	@Override
	protected void checkSubclass() {
		// allow subclass
	}

}
