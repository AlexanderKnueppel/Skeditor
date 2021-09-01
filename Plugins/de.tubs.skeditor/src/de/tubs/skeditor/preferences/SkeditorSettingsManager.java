package de.tubs.skeditor.preferences;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.Preferences;

import de.tubs.skeditor.keymaera.KeYmaeraBridge;

/**
 *  To manage the Skeditor preference settings
 */
public class SkeditorSettingsManager {

	private static final SkeditorSettingsManager instance = new SkeditorSettingsManager();

	public static SkeditorSettingsManager getInstance() {
		return instance;
	}

	private String selectedProver;
	private String z3Exe;
	private String mathematicaExe;
	private String mathematicaLibs;

	private String currentOs;

	private SkeditorSettingsManager() {
		IEclipsePreferences pref = InstanceScope.INSTANCE.getNode("de.tubs.skeditor.preferences.page");
		Preferences sub = pref.node("node");
//		pref.addPreferenceChangeListener(new IPreferenceChangeListener() {
//			@Override
//			public void preferenceChange(PreferenceChangeEvent event) {
//				System.out.println("preference change");
//				
//				selectedProver = sub.get(PreferenceSettings.SELECTED_PROVER.toString(), "");
//				refreshPath();
//				KeYmaeraBridge.getInstance().setProver();
//			}
//		});

		// Initialize values on program start
		selectedProver = sub.get(PreferenceSettings.SELECTED_PROVER.toString(), "");
		z3Exe = sub.get(PreferenceSettings.Z3_PATH.toString(), "");
		mathematicaExe = sub.get(PreferenceSettings.MATHEMATICA_PATH.toString(), "");
		
		currentOs = System.getProperty("os.name").toLowerCase();
		
		System.out.println("Skeditor Settings " + selectedProver);
		System.out.println("Skeditor Settings " + z3Exe);
		System.out.println("Skeditor Settings " + mathematicaExe);
		System.out.println("Skeditor Settings " + currentOs);

	}

	public void refreshPath() {
		currentOs = System.getProperty("os.name").toLowerCase();
		if (currentOs.contains("windows")) {
			// Windows, 64bit, Mathematica 10.4+
			mathematicaLibs = mathematicaExe.replace("MathKernel.exe", "");
			mathematicaLibs = mathematicaLibs + "SystemFiles\\Links\\JLink\\SystemFiles\\Libraries\\Windows-x86-64\\";
		} else if (currentOs.contains("linux")) {
			// Linux, 64bit, Mathematica 10.4+
			mathematicaLibs = mathematicaExe.replace("Executables\\MathKernel", "");
			mathematicaLibs = mathematicaLibs + "SystemFiles\\Links\\JLink\\SystemFiles\\Libraries\\Linux-x86-64";
		}
	}

	public String getSelectedProver() {
		return selectedProver;
	}

	public String getZ3Exe() {
		return z3Exe;
	}

	public String getMathematicaExe() {
		return mathematicaExe;
	}

	public String getMathematicaLibs() {
		return mathematicaLibs;
	}
	
	public void setSelectedProver(String selectedProver) {
		this.selectedProver = selectedProver;
	}
	
	public void setZ3Exe(String z3Exe) {
		this.z3Exe = z3Exe;
	}
	
	public void setMathematicaExe(String mathematicaExe) {
		this.mathematicaExe = mathematicaExe;
	}
}
