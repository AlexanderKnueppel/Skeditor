package de.tubs.skeditor.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class SkeditorPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private SkeditorSettingsComposite settingsComposite;

	@Override
	protected Control createContents(Composite parent) {
		this.settingsComposite = new SkeditorSettingsComposite(parent, 0);
		return this.settingsComposite;
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, "de.tubs.skeditor.preferences.page"));
	}
}
