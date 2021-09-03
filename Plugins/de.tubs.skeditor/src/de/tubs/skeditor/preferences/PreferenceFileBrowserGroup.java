package de.tubs.skeditor.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.osgi.service.prefs.Preferences;

import de.tubs.skeditor.keymaera.KeYmaeraBridge;
import de.tubs.skeditor.simulation.utils.FileDialogSelectionListener;

/**
 * SWT Group for File Browsing
 */
public class PreferenceFileBrowserGroup extends Group {

	private Button browseButton;
	private Text pathText;

	public PreferenceFileBrowserGroup(Composite parent, int style, String name, String desc, String[] filterExtensions,
			Runnable callback) {
		super(parent, style);
		this.setText(desc);
		this.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
		this.setLayout(new GridLayout(2, false));

		Text text = new Text(this, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Preferences preferences = InstanceScope.INSTANCE.getNode("de.tubs.skeditor.preferences.page");
		Preferences sub = preferences.node("node");

		switch (name) {
		case "z3":
			text.setText(sub.get(PreferenceSettings.Z3_PATH.toString(), ""));
			break;
		case "Mathematica":
			text.setText(sub.get(PreferenceSettings.MATHEMATICA_PATH.toString(), ""));
			break;
		}

		// pathText = text;

		text.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				switch (name) {
				case "z3":
					String newZ3Path = text.getText();
					System.out.println("put " + newZ3Path);
					sub.put(PreferenceSettings.Z3_PATH.toString(), newZ3Path);
					SkeditorSettingsManager.getInstance().setZ3Exe(newZ3Path);
					break;
				case "Mathematica":
					String newMathematicaPath = text.getText();
					System.out.println("put " + newMathematicaPath);
					sub.put(PreferenceSettings.MATHEMATICA_PATH.toString(), text.getText());
					SkeditorSettingsManager.getInstance().setMathematicaExe(newMathematicaPath);
					SkeditorSettingsManager.getInstance().refreshPath();
					break;
				}

				if (callback != null) {
					callback.run();
				}
			}
		});

		try {
			// forces the application to save the preferences
			preferences.flush();
		} catch (Exception e) {
			e.printStackTrace();

		}
		
		text.pack();

		browseButton = new Button(this, SWT.PUSH);
		browseButton.setText("Browse...");
		browseButton.addSelectionListener(new FileDialogSelectionListener(this.getShell(), text, filterExtensions));
		browseButton.pack();
	}

	public Text getPathText() {
		return this.pathText;
	}

	public Button getBrowseButton() {
		return this.browseButton;
	}

	@Override
	protected void checkSubclass() {
		// allow subclass
	}

}