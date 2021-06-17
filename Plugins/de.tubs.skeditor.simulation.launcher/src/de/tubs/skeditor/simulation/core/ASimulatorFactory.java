package de.tubs.skeditor.simulation.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public abstract class ASimulatorFactory {

	public static ASimulatorFactory factory;
	
	public static ASimulatorFactory getFactory() {
		return factory;
	}
	
	public static void setFactory(ASimulatorFactory abst) {
		ASimulatorFactory.factory = abst;
	}
	
	public abstract SimConfigGroup buildSimConfigGroup(Composite parent);
	public abstract void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor) throws CoreException;


}
