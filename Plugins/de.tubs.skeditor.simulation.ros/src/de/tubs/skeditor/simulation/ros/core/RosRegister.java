package de.tubs.skeditor.simulation.ros.core;

import org.eclipse.ui.IStartup;

import de.tubs.skeditor.simulation.core.ASimulatorFactory;
import de.tubs.skeditor.simulation.core.loader.SimulatorDescription;
import de.tubs.skeditor.simulation.core.loader.SimulatorLoader;
import de.tubs.skeditor.simulation.core.launch.ConfigurationTab;

public class RosRegister implements IStartup {

	@Override
	public void earlyStartup() {
		System.out.println("register ROS plugin");
		SimulatorLoader.simulatorList.add(new SimulatorDescription<ASimulatorFactory>(new RosFactory(), "ros", "handle", "ROS (Robot Operating System)"));
	}

}
