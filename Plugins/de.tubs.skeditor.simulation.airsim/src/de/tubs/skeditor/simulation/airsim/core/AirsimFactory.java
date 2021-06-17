package de.tubs.skeditor.simulation.airsim.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.swt.widgets.Composite;

import de.tubs.skeditor.simulation.core.ASimulatorFactory;
import de.tubs.skeditor.simulation.core.SimConfigGroup;


public class AirsimFactory extends ASimulatorFactory {

	@Override
	public SimConfigGroup buildSimConfigGroup(Composite parent) {
		return new AirsimConfigGroup(parent, 0);
	}

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		// TODO implement launch
		
	}

}
