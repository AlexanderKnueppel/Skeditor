package de.tubs.skeditor.simulation.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import de.tubs.skeditor.simulation.utils.FileDialogSelectionListener;

/**
 * SWT Group for File Browsing
 */
public class FileBrowserGroup extends Group {

	private Text pathText;

	public FileBrowserGroup(Composite parent, int style, String desc, String[] filterExtensions, Runnable callback) {
		super(parent, style);
		this.setText(desc);
        this.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
        this.setLayout(new GridLayout(2, false));
		
		Text text = new Text(this, SWT.BORDER);
        text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        pathText = text;

		text.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				if(callback != null) {
					callback.run();
				}
			}
		});
		text.pack();

		Button browseButton = new Button(this, SWT.PUSH);
		browseButton.setText("Browse...");
		browseButton.addSelectionListener(new FileDialogSelectionListener(this.getShell(), text, filterExtensions));
		browseButton.pack();
	}

	public Text getPathText() {
		return this.pathText;
	}
	
	@Override
	protected void checkSubclass() {
		// allow subclass
	}

}