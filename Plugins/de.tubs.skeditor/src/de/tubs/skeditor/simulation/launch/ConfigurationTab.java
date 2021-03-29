package de.tubs.skeditor.simulation.launch;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;


public class ConfigurationTab extends AbstractLaunchConfigurationTab {

    private Text skedPath;
    private Button skedPathBtn;

    private Text catkinWorkspacePath;
    private Button catkinWorkspacePathBtn;


    private Text worldPath;
    private Button worldPathBtn;

    @Override
    public void createControl(Composite parent) {

        Composite comp = new Group(parent, SWT.BORDER);
        setControl(comp);

        GridLayoutFactory.swtDefaults().numColumns(3).applyTo(comp);

        initSkedPathComponents(comp);
        initBuildPathComponents(comp);
        initWorldPathComponents(comp);
        
        this.setDirty(true);
    }


    private void initWorldPathComponents(Composite comp) {
        Label label = new Label(comp, SWT.NONE);
        label.setText(".world File:");

        worldPath = new Text(comp, SWT.BORDER);
        worldPath.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
		        setDirty(true);
		        updateLaunchConfigurationDialog();						
			}
        });

        worldPathBtn = new Button(comp, SWT.BORDER);
        worldPathBtn.setText("...");
        worldPathBtn.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				FileDialog dlg = new FileDialog(worldPathBtn.getShell(),  SWT.OPEN);
				dlg.setText("Open");
				dlg.setFilterExtensions(new String[] {"*.world"});
				String path = dlg.open();
				if (path == null) return;
				worldPath.setText(path);
		        setDirty(true);
		        updateLaunchConfigurationDialog();
			}
		});


        GridDataFactory.swtDefaults().applyTo(label);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(worldPathBtn);
        GridDataFactory.swtDefaults().applyTo(worldPathBtn);
	}



    private void initSkedPathComponents(Composite comp) {
        Label label = new Label(comp, SWT.NONE);
        label.setText(".sked File:");

        skedPath = new Text(comp, SWT.BORDER);
        skedPath.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
		        setDirty(true);
		        updateLaunchConfigurationDialog();						
			}
        });

        skedPathBtn = new Button(comp, SWT.BORDER);
        skedPathBtn.setText("...");
        skedPathBtn.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				FileDialog dlg = new FileDialog(skedPathBtn.getShell(),  SWT.OPEN);
				dlg.setText("Open");
				dlg.setFilterExtensions(new String[] {"*.sked"});
				String path = dlg.open();
				if (path == null) return;
				skedPath.setText(path);
		        setDirty(true);
		        updateLaunchConfigurationDialog();
			}
		});


        GridDataFactory.swtDefaults().applyTo(label);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(skedPathBtn);
        GridDataFactory.swtDefaults().applyTo(skedPathBtn);
	}

    private void initBuildPathComponents(Composite comp) { //TODO: Workspace?
        Label label = new Label(comp, SWT.NONE);
        label.setText("Catkin Base Path:");

        catkinWorkspacePath = new Text(comp, SWT.BORDER);
        catkinWorkspacePath.addModifyListener(new ModifyListener() {

 			@Override
 			public void modifyText(ModifyEvent e) {
 		        setDirty(true);
 		        updateLaunchConfigurationDialog();						
 			}
         });

        catkinWorkspacePathBtn = new Button(comp, SWT.BORDER);
        catkinWorkspacePathBtn.setText("...");
        catkinWorkspacePathBtn.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dlg = new DirectoryDialog(catkinWorkspacePathBtn.getShell(),  SWT.OPEN);
				dlg.setText("Open");
				String path = dlg.open();
				if (path == null) return;
				catkinWorkspacePath.setText(path);
		        setDirty(true);
		        updateLaunchConfigurationDialog();
			}
		});


        GridDataFactory.swtDefaults().applyTo(label);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(catkinWorkspacePathBtn);
        GridDataFactory.swtDefaults().applyTo(catkinWorkspacePathBtn);
	}

	@Override
    public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
    }

    @Override
    public void initializeFrom(ILaunchConfiguration configuration) {
        try {
            String skedPath = configuration.getAttribute(LaunchConfigurationAttributes.SKED_PATH,"");
            this.skedPath.setText(skedPath);

            String buildPath = configuration.getAttribute(LaunchConfigurationAttributes.CATKIN_WORKSPACE_PATH,"");
            this.catkinWorkspacePath.setText(buildPath);

            String worldPath = configuration.getAttribute(LaunchConfigurationAttributes.WORLD_PATH,"");
            this.worldPath.setText(worldPath);
        } catch (CoreException e) {
            // ignore here
        }
    }

    @Override
    public void performApply(ILaunchConfigurationWorkingCopy configuration) {
        configuration.setAttribute(LaunchConfigurationAttributes.SKED_PATH, skedPath.getText());
        configuration.setAttribute(LaunchConfigurationAttributes.CATKIN_WORKSPACE_PATH, catkinWorkspacePath.getText());
        configuration.setAttribute(LaunchConfigurationAttributes.WORLD_PATH, worldPath.getText());
    }

    @Override
    public String getName() {
        return "Skill Graph Runner";
    }
} 
