package de.tubs.skeditor.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.Preferences;

/**
 * 
 * @author Dibo Gonda
 *
 */
public class SkeditorSettings {
	
	private static String z3Path;
	private static String mathematicaPath;
	private static SelectedProver prover;
	
	public static void init() {
		Preferences preferences = InstanceScope.INSTANCE.getNode("de.tubs.skeditor.preferences.page");
		
		// TODO set member (default values or persistent settings)
		
		/*
		// Reacting to changes
		preferences.addPropertyChangeListener(new IPropertyChangeListener() {
		        @Override
		        public void propertyChange(PropertyChangeEvent event) {
		            if (event.getProperty() == "MySTRING1") {
		                String value = event.getNewValue().toString();
		                // do something with the new value
		            }
		        }
		    });
		  */
	}
	
	public static String getSelectedProverPath() {
		String proverPath = "";
		switch (prover) {
		case z3 :
			proverPath = z3Path;
		case Mathematica:
			return proverPath = mathematicaPath;
		}
		return proverPath;
	}
	
	public static SelectedProver getSelectedProver() {
		return prover;
	}
}
