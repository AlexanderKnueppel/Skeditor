package de.tubs.skeditor.simulation.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import de.tubs.skeditor.simulation.plugin.core.ASimConfigGroup;
import de.tubs.skeditor.simulation.plugin.core.ASimulatorFactory;
import de.tubs.skeditor.simulation.plugin.handle.SimulatorDescription;
import de.tubs.skeditor.simulation.plugin.handle.SimulatorLoader;

public class ConfigurationTab extends AbstractLaunchConfigurationTab {

	private Map<String, ASimConfigGroup> configGroups;
	private Combo dropDownMenu;
	private String selectedSmulatorDesc;
	
	private ASimConfigGroup group;

	@Override
	public void createControl(Composite parent) {
		configGroups = new HashMap<>();

		Group generalGroup = new Group(parent, SWT.NONE);
		generalGroup.setText("Simulator Settings");
		generalGroup.setLayout(new GridLayout(1, false));
		setControl(generalGroup);

		initSimulatorSelectionComponent(generalGroup);

		SimulatorLoader.simulatorList.stream().forEach(simulatorDesc -> {
			ASimConfigGroup configGroup = simulatorDesc.getSimulator().buildSimConfigGroup(generalGroup, () -> {
				setDirty(true);
				updateLaunchConfigurationDialog();
			});
			this.configGroups.put(simulatorDesc.getDescription(), configGroup);
			//configGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
			configGroup.addListener(0, new Listener() {
				public void handleEvent(Event event) {
					setDirty(true);
					updateLaunchConfigurationDialog();
				}
			});
			configGroup.pack();
		});

		parent.pack();
		this.setDirty(true);

		// Kill simulator processes on window closing

		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();

		win.addPageListener(new IPageListener() {

			@Override
			public void pageActivated(IWorkbenchPage page) {
			}

			@Override
			public void pageClosed(IWorkbenchPage page) {
				Optional<SimulatorDescription<ASimulatorFactory>> simulator = SimulatorLoader.simulatorList.stream()
						.filter(sim -> sim.getName().equalsIgnoreCase("ros")).findFirst();
				if (simulator != null) {
					System.out.println(simulator.get().getName());
					try {
						simulator.get().getSimulator().cleanAfterClose();
					} catch (Exception e) {
						//
					}
				}
			}

			@Override
			public void pageOpened(IWorkbenchPage page) {
			}

		});
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
				selectedSmulatorDesc = dropDownMenu.getText();
				configGroups.entrySet().stream().forEach(entry -> {
					if (entry.getKey().equalsIgnoreCase(selectedSmulatorDesc)) {
						entry.getValue().setVisible(true);
					} else {
						entry.getValue().setVisible(false);
					}
				});
				
				comp.pack();
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
			final String copy = simulatorName;
			Optional<SimulatorDescription<ASimulatorFactory>> simulator = SimulatorLoader.simulatorList.stream()
					.filter(sim -> sim.getName().equalsIgnoreCase(copy)).findFirst();
			if (simulator.isPresent()) {
				this.selectedSmulatorDesc = simulator.get().getDescription();
			}
			
			this.configGroups.entrySet().stream().forEach(entry -> {
				if (entry.getKey().equalsIgnoreCase(selectedSmulatorDesc)) {
					entry.getValue().setVisible(true);
				} else {
					entry.getValue().setVisible(false);
				}
			});
			
			//this.configGroups.get(selectedSmulatorDesc).getParent().pack();
			
			
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

		this.configGroups.entrySet().forEach(entry -> {
			try {
				entry.getValue().initializeAttributes(configuration);
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

		this.configGroups.entrySet().stream().forEach(entry -> {
			try {
				entry.getValue().applyAttributes(configuration);
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
