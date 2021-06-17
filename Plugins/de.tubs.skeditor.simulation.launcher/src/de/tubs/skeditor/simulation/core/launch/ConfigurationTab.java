package de.tubs.skeditor.simulation.core.launch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.core.util.ArrayUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import de.tubs.skeditor.simulation.core.SimConfigGroup;
import de.tubs.skeditor.simulation.core.loader.SimulatorDescription;
import de.tubs.skeditor.simulation.core.loader.SimulatorLoader;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;

public class ConfigurationTab extends AbstractLaunchConfigurationTab {

	List<SimConfigGroup> configGroups;

//	private Text skedPath;
//	private Button skedPathBtn;
//
//	private Text catkinWorkspacePath;
//	private Button catkinWorkspacePathBtn;
//
//	private Text worldPath;
//	private Button worldPathBtn;

	private Combo dropDownMenu;

	@Override
	public void createControl(Composite parent) {
		configGroups = new ArrayList<>();

		Group generalGroup = new Group(parent, SWT.NONE);
		generalGroup.setText("Simulator Settings");
		generalGroup.setLayout(new GridLayout(1, false));
		setControl(generalGroup);

		initSimulatorSelectionComponent(generalGroup); // drop down

//        GridLayoutFactory.swtDefaults().numColumns(3).applyTo(comp);
//        initSkedPathComponents(comp);
//        initBuildPathComponents(comp);
//        initWorldPathComponents(comp);

		SimulatorLoader.simulatorList.stream().forEach(simulatorDesc -> {
			SimConfigGroup configGroup = simulatorDesc.getSimulator().buildSimConfigGroup(generalGroup);
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

//	private void initWorldPathComponents(Composite comp) {
//		Label label = new Label(comp, SWT.NONE);
//		label.setText(".world File:");
//
//		worldPath = new Text(comp, SWT.BORDER);
//		worldPath.addModifyListener(new ModifyListener() {
//
//			@Override
//			public void modifyText(ModifyEvent e) {
//				setDirty(true);
//				updateLaunchConfigurationDialog();
//			}
//		});
//
//		worldPathBtn = new Button(comp, SWT.BORDER);
//		worldPathBtn.setText("...");
//		worldPathBtn.addSelectionListener(new SelectionListener() {
//
//			public void widgetDefaultSelected(SelectionEvent e) {
//			}
//
//			public void widgetSelected(SelectionEvent e) {
//				FileDialog dlg = new FileDialog(worldPathBtn.getShell(), SWT.OPEN);
//				dlg.setText("Open");
//				dlg.setFilterExtensions(new String[] { "*.world" });
//				String path = dlg.open();
//				if (path == null)
//					return;
//				worldPath.setText(path);
//				setDirty(true);
//				updateLaunchConfigurationDialog();
//			}
//		});
//
//		GridDataFactory.swtDefaults().applyTo(label);
//		GridDataFactory.fillDefaults().grab(true, false).applyTo(worldPathBtn);
//		GridDataFactory.swtDefaults().applyTo(worldPathBtn);
//	}

//	private void initSkedPathComponents(Composite comp) {
//		Label label = new Label(comp, SWT.NONE);
//		label.setText(".sked File:");
//
//		skedPath = new Text(comp, SWT.BORDER);
//		skedPath.addModifyListener(new ModifyListener() {
//
//			@Override
//			public void modifyText(ModifyEvent e) {
//				setDirty(true);
//				updateLaunchConfigurationDialog();
//			}
//		});
//
//		skedPathBtn = new Button(comp, SWT.BORDER);
//		skedPathBtn.setText("...");
//		skedPathBtn.addSelectionListener(new SelectionListener() {
//
//			public void widgetDefaultSelected(SelectionEvent e) {
//			}
//
//			public void widgetSelected(SelectionEvent e) {
//				FileDialog dlg = new FileDialog(skedPathBtn.getShell(), SWT.OPEN);
//				dlg.setText("Open");
//				dlg.setFilterExtensions(new String[] { "*.sked" });
//				String path = dlg.open();
//				if (path == null)
//					return;
//				skedPath.setText(path);
//				setDirty(true);
//				updateLaunchConfigurationDialog();
//			}
//		});
//
//		GridDataFactory.swtDefaults().applyTo(label);
//		GridDataFactory.fillDefaults().grab(true, false).applyTo(skedPathBtn);
//		GridDataFactory.swtDefaults().applyTo(skedPathBtn);
//	}

//	private void initBuildPathComponents(Composite comp) { // TODO: Workspace?
//		Label label = new Label(comp, SWT.NONE);
//		label.setText("Catkin Base Path:");
//
//		catkinWorkspacePath = new Text(comp, SWT.BORDER);
//		catkinWorkspacePath.addModifyListener(new ModifyListener() {
//
//			@Override
//			public void modifyText(ModifyEvent e) {
//				setDirty(true);
//				updateLaunchConfigurationDialog();
//			}
//		});
//
//		catkinWorkspacePathBtn = new Button(comp, SWT.BORDER);
//		catkinWorkspacePathBtn.setText("...");
//		catkinWorkspacePathBtn.addSelectionListener(new SelectionListener() {
//
//			public void widgetDefaultSelected(SelectionEvent e) {
//			}
//
//			public void widgetSelected(SelectionEvent e) {
//				DirectoryDialog dlg = new DirectoryDialog(catkinWorkspacePathBtn.getShell(), SWT.OPEN);
//				dlg.setText("Open");
//				String path = dlg.open();
//				if (path == null)
//					return;
//				catkinWorkspacePath.setText(path);
//				setDirty(true);
//				updateLaunchConfigurationDialog();
//			}
//		});
//
//		GridDataFactory.swtDefaults().applyTo(label);
//		GridDataFactory.fillDefaults().grab(true, false).applyTo(catkinWorkspacePathBtn);
//		GridDataFactory.swtDefaults().applyTo(catkinWorkspacePathBtn);
//	}

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

//        try {
//            String skedPath = configuration.getAttribute(LaunchConfigurationAttributes.SKED_PATH,"");
//            this.skedPath.setText(skedPath);
//
//            String buildPath = configuration.getAttribute(LaunchConfigurationAttributes.CATKIN_WORKSPACE_PATH,"");
//            this.catkinWorkspacePath.setText(buildPath);
//
//            String worldPath = configuration.getAttribute(LaunchConfigurationAttributes.WORLD_PATH,"");
//            this.worldPath.setText(worldPath);
//            
//            String simulatorName = configuration.getAttribute(LaunchConfigurationAttributes.SIMULATOR_NAME,"");
//            this.dropDown.select(Arrays.asList(this.dropDown.getItems()).indexOf(simulatorName));
//        } catch (CoreException e) {
//            // ignore here
//        }
	}

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

//        configuration.setAttribute(LaunchConfigurationAttributes.SKED_PATH, skedPath.getText());
//        configuration.setAttribute(LaunchConfigurationAttributes.CATKIN_WORKSPACE_PATH, catkinWorkspacePath.getText());
//        configuration.setAttribute(LaunchConfigurationAttributes.WORLD_PATH, worldPath.getText());
//        configuration.setAttribute(LaunchConfigurationAttributes.SIMULATOR_NAME, dropDown.getText());
	}

	@Override
	public String getName() {
		return "Skill Graph Runner";
	}
}
