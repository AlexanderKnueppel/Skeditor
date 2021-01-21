package de.tubs.skeditor.preferences;

import java.util.List;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class SkeditorPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private SkeditorSettingsComposite settingsComposite;

	@Override
	protected Control createContents(Composite parent) {
		this.settingsComposite = new SkeditorSettingsComposite(parent, 0);
		//setDefaultValues();
		return this.settingsComposite;
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, "de.tubs.skeditor.preferences.page"));
	}

	private void setDefaultValues() {
		this.settingsComposite.getSelectProverGroup().getRadioButtons().get(0).setSelection(true);
		List<Text> textAreas = this.settingsComposite.getSelectProverGroup().getTextAreas();
		List<Button> browseButtons = this.settingsComposite.getSelectProverGroup().getBrowseButtons();
		for (int i = 0; i < textAreas.size(); i++) {
			if (i != 0) {
				textAreas.get(i).setEnabled(false);
				browseButtons.get(i).setEnabled(false);
			}
		}
	}

}
