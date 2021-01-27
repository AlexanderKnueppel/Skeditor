package de.tubs.skeditor.preferences;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.Preferences;

import de.tubs.skeditor.keymaera.KeYmaeraBridge;

/**
 * 
 * @author Dibo Gonda
 *
 */
public class SkeditorSettings {

	private static final SkeditorSettings instance = new SkeditorSettings();

	public static SkeditorSettings getInstance() {
		return instance;
	}

	private String selectedProver;
	private String z3Exe;
	private String mathematicaExe;
	private String mathematicaLibs;

	private String currentOs;

	private SkeditorSettings() {
		IEclipsePreferences pref = InstanceScope.INSTANCE.getNode("de.tubs.skeditor.preferences.page");
		pref.addPreferenceChangeListener(new IPreferenceChangeListener() {
			@Override
			public void preferenceChange(PreferenceChangeEvent event) {
				refreshPath();
				KeYmaeraBridge.getInstance().setProver();
			}
		});

		// Initialize values on program start
		Preferences preferences = InstanceScope.INSTANCE.getNode("de.tubs.skeditor.preferences.page");
		Preferences sub = preferences.node("node");
		selectedProver = sub.get("selected_radio_button", "default");
		currentOs = System.getProperty("os.name").toLowerCase();
		z3Exe = sub.get("z3_exe", "");
		mathematicaExe = sub.get("mathematica_exe", "");
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
}
