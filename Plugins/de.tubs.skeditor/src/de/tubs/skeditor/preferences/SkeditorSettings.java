package de.tubs.skeditor.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.util.IPropertyChangeListener;

import de.tubs.skeditor.keymaera.KeYmaeraBridge;
import scala.reflect.api.Trees.ThisExtractor;
import org.osgi.service.prefs.Preferences;


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

	private static String selectedRadioButton;
	private static SelectedProver prover;
	private static String z3Exe;
	private static String mathematicaExe;
	private static String mathematicaLibs;

	public SkeditorSettings() {
		
//		Preferences pref = InstanceScope.INSTANCE.getNode("de.tubs.skeditor.preferences.page");
//		pref.addPropertyChangeListener(new IPropertyChangeListener() {
//		        @Override
//		        public void propertyChange(PropertyChangeEvent event) {
//		            if (event.getProperty() == "MySTRING1") {
//		                String value = event.getNewValue().toString();
//		                // do something with the new value
//		            }
//		        }
//		    });
		
		// Load values after restart
		Preferences preferences = InstanceScope.INSTANCE
			    .getNode("de.tubs.skeditor.preferences.page");
		
		
			Preferences sub1 = preferences.node("node1");
			selectedRadioButton = sub1.get("selected_radio_button", "default");

			Preferences sub2 = preferences.node("node2");
			z3Exe = sub2.get("z3_exe", "default");
			mathematicaExe = sub2.get("mathematica_exe", "default");
			// todo set mathematicaLibs
			mathematicaLibs = mathematicaExe.replace("MathKernel.exe", "");
			mathematicaLibs = mathematicaLibs + "SystemFiles\\Links\\JLink\\SystemFiles\\Libraries\\Windows-x86-64\\";
	}

	/*
	 * public static String getSelectedProverPath() { String proverPath = ""; switch
	 * (prover) { case z3: proverPath = z3Path; case Mathematica: return proverPath
	 * = mathematicaPath; } return proverPath; }
	 */
	
	public static String getSelectedRadioButton() {
		return selectedRadioButton;
	}

	public static SelectedProver getSelectedProver() {
		return prover;
	}

	public static String getZ3Exe() {
		return z3Exe;
	}

	public static String getMathematicaExe() {
		return mathematicaExe;
	}

	public static String getMathematicaLibs() {
		return mathematicaLibs;
	}
}
