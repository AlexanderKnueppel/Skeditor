package de.tubs.skeditor.simulation.plugin.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Layout;

/**
 * An abstract class to create a GUI for Simulator Configurations.
 * Using a SWT Group to visually encapsulate each Simulator Configuration.
 */
public abstract class ASimConfigGroup extends Group {
	
	final private GridData data;
	final private String name;
	
	public ASimConfigGroup(String name, Composite parent, int style) {
		super(parent, style);
		this.setText(name);
		super.setLayout(new GridLayout(1, true));
		data = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		this.setLayoutData(data);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public void setLayout(Layout layout) {
		System.out.println("ignored set layout");
	}
	
	@Override
	public void setVisible(boolean visible) {
		data.exclude = !visible;
		super.setVisible(visible);
		this.getParent().pack();	
		System.out.println("setVisible: " + visible);
	}
	
	/**
	 * To set the settings from persistent file to GUI components.
	 * @param configuration
	 * @throws CoreException
	 */
	public abstract void initializeAttributes(ILaunchConfiguration configuration) throws CoreException;
	
	/**
	 * To save settings to persistent file.
	 * @param configuration
	 * @throws CoreException
	 */
	public abstract void applyAttributes(ILaunchConfigurationWorkingCopy configuration) throws CoreException;
	
	/**
	 * A little hack to permit deriving from Group. 
	 * No implementation required!
	 */
	@Override
	protected void checkSubclass() {
		// allow subclass
	}

}
