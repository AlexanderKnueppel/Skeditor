package de.tubs.skeditor.simulation.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;

import de.tubs.skeditor.simulation.plugin.core.ASimConfigGroup;
import de.tubs.skeditor.simulation.plugin.handle.SimulatorDescription;
import de.tubs.skeditor.simulation.plugin.handle.SimulatorLoader;

public class ConfigurationTab extends AbstractLaunchConfigurationTab {

	private List<ASimConfigGroup> configGroups;
	private Combo dropDownMenu;

	@Override
	public void createControl(Composite parent) {
		configGroups = new ArrayList<>();

		Group generalGroup = new Group(parent, SWT.NONE);
		generalGroup.setText("Simulator Settings");
		generalGroup.setLayout(new GridLayout(1, false));
		setControl(generalGroup);

		initSimulatorSelectionComponent(generalGroup);

		SimulatorLoader.simulatorList.stream().forEach(simulatorDesc -> {
			ASimConfigGroup configGroup = simulatorDesc.getSimulator().buildSimConfigGroup(generalGroup);
			this.configGroups.add(configGroup);
			configGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
			configGroup.addListener(0, new Listener() {
				public void handleEvent(Event event) {
					setDirty(true);
					updateLaunchConfigurationDialog();
				}
			});
			configGroup.pack();
		});

		this.setDirty(true);
	}

	private void initSimulatorSelectionComponent(Composite comp) {
		List<String> items = new ArrayList<>();
		items.add("None");
		items.addAll(SimulatorLoader.simulatorList.stream().map(SimulatorDescription::getDescription)
				.collect(Collectors.toList()));

		// Create the group
		Group selectSimulatorGroup = new Group(comp, SWT.SHADOW_ETCHED_IN);
		selectSimulatorGroup.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
		selectSimulatorGroup.setLayout(new GridLayout(1, false));
		selectSimulatorGroup.setText("Select Simulator");

		dropDownMenu = new Combo(selectSimulatorGroup, SWT.BORDER);
		dropDownMenu.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// Default
		dropDownMenu.setItems(items.toArray(new String[0]));
		dropDownMenu.select(0);

		dropDownMenu.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				System.out.println(dropDownMenu.getText());
				setDirty(true);
				updateLaunchConfigurationDialog();
			}
		});
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
	}

	/**
	 * Initialize
	 */
	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		String simulatorName = "";
		try {
			simulatorName = configuration.getAttribute(LaunchConfigAttributes.SIMULATOR_NAME, "");
		} catch (CoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (("none").equalsIgnoreCase(simulatorName)) {
			dropDownMenu.select(0);
		} else {
			dropDownMenu.select(SimulatorLoader.simulatorList.stream().map(SimulatorDescription::getName)
					.collect(Collectors.toList()).indexOf(simulatorName) + 1);
		}

		this.configGroups.stream().forEach(configGroup -> {
			try {
				configGroup.initializeAttributes(configuration);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	/**
	 * Save settings to persistent file.
	 */
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		if (dropDownMenu.getSelectionIndex() == 0) {
			configuration.setAttribute(LaunchConfigAttributes.SIMULATOR_NAME, "None");
		} else {
			configuration.setAttribute(LaunchConfigAttributes.SIMULATOR_NAME,
					SimulatorLoader.simulatorList.get(dropDownMenu.getSelectionIndex() - 1).getName());
		}

		this.configGroups.stream().forEach(configGroup -> {
			try {
				configGroup.applyAttributes(configuration);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	@Override
	public String getName() {
		return "Skill Graph Runner";
	}
}
