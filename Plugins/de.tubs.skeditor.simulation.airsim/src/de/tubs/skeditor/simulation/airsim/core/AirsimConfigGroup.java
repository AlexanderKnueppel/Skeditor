package de.tubs.skeditor.simulation.airsim.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.widgets.Composite;

import de.tubs.skeditor.simulation.core.SimConfigGroup;

public class AirsimConfigGroup extends SimConfigGroup {

	public AirsimConfigGroup(Composite parent, int style) {
		super(parent, style);
		this.setText("AirSim");
		// TODO configuration GUI
	}
	
	@Override
	public void initializeAttributes(ILaunchConfiguration configuration) throws CoreException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void applyAttributes(ILaunchConfigurationWorkingCopy configuration) throws CoreException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void checkSubclass() {
		// allow subclass
	}

}
