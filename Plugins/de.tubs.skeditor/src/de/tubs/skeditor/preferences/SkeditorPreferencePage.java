package de.tubs.skeditor.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.service.prefs.Preferences;

public class SkeditorPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private SkeditorSettingsComposite settingsComposite;

	@Override
	protected Control createContents(Composite parent) {
		this.settingsComposite = new SkeditorSettingsComposite(parent, 0);
		
//		Preferences preferences = InstanceScope.INSTANCE.getNode("de.tubs.skeditor.preferences.page");
//		Preferences sub = preferences.node("node");
//		
//		String prover = sub.get(PreferenceSettings.SELECTED_PROVER.toString(), "").toLowerCase();
//		if (!prover.equalsIgnoreCase("")) {
//			switch(prover) {
//			case "z3":
//				this.settingsComposite.getSelectProverGroup().getDropDownMenu().select(0);
//				break;
//			case "mathematica":
//				this.settingsComposite.getSelectProverGroup().getDropDownMenu().select(1);
//				break;
//			}
//		}
//		
//		String z3Path_1 = sub.get(PreferenceSettings.Z3_PATH.toString(), "");
//		System.out.println("TEST" + z3Path_1);
//		String z3Path = sub.get("z3_exe", "");
//		System.out.println("TEST" + z3Path);
//		this.settingsComposite.getSelectProverGroup().getTextAreasMap().get("z3").setText(z3Path);
//		
//		String mathematicaExe = sub.get(PreferenceSettings.MATHEMATICA_PATH.toString(), "");
//		this.settingsComposite.getSelectProverGroup().getTextAreasMap().get("mathematica").setText(mathematicaExe);
		
		return this.settingsComposite;
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, "de.tubs.skeditor.preferences.page"));
	}
}
