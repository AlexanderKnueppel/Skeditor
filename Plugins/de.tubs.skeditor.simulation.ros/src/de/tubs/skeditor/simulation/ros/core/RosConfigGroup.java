package de.tubs.skeditor.simulation.ros.core;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import de.tubs.skeditor.simulation.core.FileBrowserGroup;
import de.tubs.skeditor.simulation.core.SimConfigGroup;

public class RosConfigGroup extends SimConfigGroup {

	private Map<String, FileBrowserGroup> configMap;

	public RosConfigGroup(Composite parent, int style) {
		super(parent, style);
		this.setText("ROS");
		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.spacing = 3;
		fillLayout.marginHeight = 3;
		fillLayout.marginWidth = 3;
		this.setLayout(fillLayout);

		Composite comp = new Composite(this, SWT.NONE);
		comp.setLayout(fillLayout);

		configMap = new HashMap<>();
		FileBrowserGroup skedFileBrowser = new FileBrowserGroup(comp, SWT.NONE, "*.sked", new String[] { "*.sked" });
		skedFileBrowser.pack();
		configMap.put(RosLaunchConfigAttributes.SKED_PATH, skedFileBrowser);

		FileBrowserGroup catkinWorkspaceFileBrowser = new FileBrowserGroup(comp, SWT.NONE, ".catkin_workspace",
				new String[] { ".catkin_workspace" });
		catkinWorkspaceFileBrowser.pack();
		configMap.put(RosLaunchConfigAttributes.CATKIN_WORKSPACE_PATH, catkinWorkspaceFileBrowser);

		FileBrowserGroup worldFileBrowser = new FileBrowserGroup(comp, SWT.NONE, "*.world", new String[] { "*.world" });
		worldFileBrowser.pack();
		configMap.put(RosLaunchConfigAttributes.WORLD_PATH, worldFileBrowser);

		FileBrowserGroup vscCmdFileBrowser = new FileBrowserGroup(comp, SWT.NONE, "VsDevCmd.bat",
				new String[] { "*.bat" });
		configMap.put(RosLaunchConfigAttributes.VSC_CMD_PATH, vscCmdFileBrowser);
		vscCmdFileBrowser.pack();

		comp.pack();
	}

	@Override
	public void initializeAttributes(ILaunchConfiguration configuration) throws CoreException {
		String skedPath = configuration.getAttribute(RosLaunchConfigAttributes.SKED_PATH, "");
		this.configMap.get(RosLaunchConfigAttributes.SKED_PATH).getPathText().setText(skedPath);
		
		String catkinPath = configuration.getAttribute(RosLaunchConfigAttributes.CATKIN_WORKSPACE_PATH, "");
		this.configMap.get(RosLaunchConfigAttributes.CATKIN_WORKSPACE_PATH).getPathText().setText(catkinPath);
		
		String worldPath = configuration.getAttribute(RosLaunchConfigAttributes.WORLD_PATH, "");
		this.configMap.get(RosLaunchConfigAttributes.WORLD_PATH).getPathText().setText(worldPath);
		
		String vscCmdPath = configuration.getAttribute(RosLaunchConfigAttributes.VSC_CMD_PATH, "");
		this.configMap.get(RosLaunchConfigAttributes.VSC_CMD_PATH).getPathText().setText(vscCmdPath);
	}

	@Override
	public void applyAttributes(ILaunchConfigurationWorkingCopy configuration) throws CoreException {
		configuration.setAttribute(RosLaunchConfigAttributes.SKED_PATH,
				this.configMap.get(RosLaunchConfigAttributes.SKED_PATH).getPathText().getText());
		
		configuration.setAttribute(RosLaunchConfigAttributes.CATKIN_WORKSPACE_PATH,
				this.configMap.get(RosLaunchConfigAttributes.CATKIN_WORKSPACE_PATH).getPathText().getText());
		
		configuration.setAttribute(RosLaunchConfigAttributes.WORLD_PATH,
				this.configMap.get(RosLaunchConfigAttributes.WORLD_PATH).getPathText().getText());
		
		configuration.setAttribute(RosLaunchConfigAttributes.VSC_CMD_PATH,
				this.configMap.get(RosLaunchConfigAttributes.VSC_CMD_PATH).getPathText().getText());
	}

	public Map<String, FileBrowserGroup> getCompositeMap() {
		return this.configMap;
	}

	@Override
	protected void checkSubclass() {
		// allow subclass
	}

}