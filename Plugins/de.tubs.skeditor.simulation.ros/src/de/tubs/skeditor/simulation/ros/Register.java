package de.tubs.skeditor.simulation.ros;

import org.eclipse.ui.IStartup;

import de.tubs.skeditor.simulation.core.loader.SimulatorDescription;
import de.tubs.skeditor.simulation.core.loader.SimulatorLoader;

public class Register implements IStartup {

	@Override
	public void earlyStartup() {
		System.out.println("ros start up");
		SimulatorLoader.simulatorList.add(new SimulatorDescription<String>(RosFactory.class.getName(), "ros", "handle", "desc"));
	}

}
