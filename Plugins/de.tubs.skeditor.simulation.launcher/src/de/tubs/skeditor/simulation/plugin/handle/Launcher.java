package de.tubs.skeditor.simulation.plugin.handle;

import java.util.Optional;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;

import de.tubs.skeditor.simulation.config.LaunchConfigAttributes;
import de.tubs.skeditor.simulation.plugin.core.ASimulatorFactory;

public class Launcher extends LaunchConfigurationDelegate {

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		String name = configuration.getAttribute(LaunchConfigAttributes.SIMULATOR_NAME, "");

		if (("none").equalsIgnoreCase(name)) {
			System.out.println("No plug-in selected!");
		} else {
			Optional<SimulatorDescription<ASimulatorFactory>> simDescription = SimulatorLoader.simulatorList.stream()
					.filter(sim -> sim.getName().equalsIgnoreCase(name)).findFirst();
			if (simDescription.isPresent()) {
				simDescription.get().getSimulator().launch(configuration, mode, launch, monitor);
			} else {
				System.err.println("Selected plug-in not found!");
			}
		}
	}
}
