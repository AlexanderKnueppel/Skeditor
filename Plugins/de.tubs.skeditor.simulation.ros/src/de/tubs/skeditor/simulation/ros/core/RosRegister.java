package de.tubs.skeditor.simulation.ros.core;

import org.eclipse.ui.IStartup;

import de.tubs.skeditor.simulation.plugin.core.ASimulatorFactory;
import de.tubs.skeditor.simulation.plugin.handle.SimulatorDescription;
import de.tubs.skeditor.simulation.plugin.handle.SimulatorLoader;

public class RosRegister implements IStartup {

	@Override
	public void earlyStartup() {
		System.out.println("register ROS plugin");
		SimulatorLoader.simulatorList.add(new SimulatorDescription<ASimulatorFactory>(new RosFactory(), "ros", "handle", "ROS (Robot Operating System)"));
	}

}
