package de.tubs.skeditor.simulation.airsim.core;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

import de.tubs.skeditor.simulation.plugin.core.ASimConfigGroup;
import de.tubs.skeditor.simulation.utils.FileBrowserGroup;

public class AirsimConfigGroup extends ASimConfigGroup {

	private Map<String, FileBrowserGroup> configMap;

	public AirsimConfigGroup(Composite parent, int style, Runnable callback) {
		super("AirSim", parent, style);
		//this.setText("AirSim");

		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.spacing = 3;
		fillLayout.marginHeight = 3;
		fillLayout.marginWidth = 3;
		//this.setLayout(fillLayout);

		Composite comp = new Composite(this, SWT.NONE);
		comp.setLayout(fillLayout);

		configMap = new HashMap<>();
		FileBrowserGroup skedFileBrowser = new FileBrowserGroup(comp, SWT.NONE, "*.sked", new String[] { "*.sked" },
				callback);
		skedFileBrowser.pack();
		configMap.put(AirsimLaunchConfigAttributes.SKED_PATH, skedFileBrowser);

		FileBrowserGroup airsimFolderFileBrowser = new FileBrowserGroup(comp, SWT.NONE, "AirSim.sln",
				new String[] { "AirSim.sln" }, callback);
		airsimFolderFileBrowser.pack();
		configMap.put(AirsimLaunchConfigAttributes.AIRSIM_PATH, airsimFolderFileBrowser);

		FileBrowserGroup projektFolderFileBrowser = new FileBrowserGroup(comp, SWT.NONE, "Project Folder", null, callback);
		projektFolderFileBrowser.pack();
		configMap.put(AirsimLaunchConfigAttributes.PROJECT_PATH, projektFolderFileBrowser);

		comp.pack();
	}

	@Override
	public void initializeAttributes(ILaunchConfiguration configuration) throws CoreException {
		String skedPath = configuration.getAttribute(AirsimLaunchConfigAttributes.SKED_PATH, "");
		this.configMap.get(AirsimLaunchConfigAttributes.SKED_PATH).getPathText().setText(skedPath);

		String airsimFolderPath = configuration.getAttribute(AirsimLaunchConfigAttributes.AIRSIM_PATH, "");
		this.configMap.get(AirsimLaunchConfigAttributes.AIRSIM_PATH).getPathText().setText(airsimFolderPath);

		String projektFolderPath = configuration.getAttribute(AirsimLaunchConfigAttributes.PROJECT_PATH, "");
		this.configMap.get(AirsimLaunchConfigAttributes.PROJECT_PATH).getPathText().setText(projektFolderPath);
	}

	@Override
	public void applyAttributes(ILaunchConfigurationWorkingCopy configuration) throws CoreException {
		configuration.setAttribute(AirsimLaunchConfigAttributes.SKED_PATH,
				this.configMap.get(AirsimLaunchConfigAttributes.SKED_PATH).getPathText().getText());

		configuration.setAttribute(AirsimLaunchConfigAttributes.AIRSIM_PATH,
				this.configMap.get(AirsimLaunchConfigAttributes.AIRSIM_PATH).getPathText().getText());

		configuration.setAttribute(AirsimLaunchConfigAttributes.PROJECT_PATH,
				this.configMap.get(AirsimLaunchConfigAttributes.PROJECT_PATH).getPathText().getText());

	}

	public Map<String, FileBrowserGroup> getCompositeMap() {
		return this.configMap;
	}

}
