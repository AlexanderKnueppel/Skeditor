package de.tubs.skeditor.simulation.airsim.core;

import org.eclipse.ui.IStartup;

import de.tubs.skeditor.simulation.plugin.core.ASimulatorFactory;
import de.tubs.skeditor.simulation.plugin.handle.SimulatorDescription;
import de.tubs.skeditor.simulation.plugin.handle.SimulatorLoader;

public class AirsimRegister implements IStartup {

	@Override
	public void earlyStartup() {
		System.out.println("register Airsim plugin");
		SimulatorLoader.simulatorList.add(new SimulatorDescription<ASimulatorFactory>(new AirsimFactory(), "airsim", "handle", "AirSim (Aerial Informatics and Robotics Simulation) by Microsoft"));
	}

}
