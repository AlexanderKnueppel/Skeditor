package de.tubs.skeditor.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class SkeditorPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private SkeditorSettingsComposite preferencesComposite;
	
    public SkeditorPreferencePage() {
    }

    /*
    public void createFieldEditors() {
    	addField(new RadioGroupFieldEditor("CHOICE", "Solver", 1,
                new String[][] { { "z3", "choice1" }, { "Mathematica", "choice2" } }, getFieldEditorParent()));
        addField(new DirectoryFieldEditor("PATH", "&z3 directory:", getFieldEditorParent()));
        addField(new DirectoryFieldEditor("PATH", "&Mathematica directory:", getFieldEditorParent()));
    }
    */
    
    @Override
    protected Control createContents(Composite parent) {
    	/*
        this.preferencesComposite = SkeditorPreferencesComposite.builder(parent)
                .build();
        */
    	
    	this.preferencesComposite = new SkeditorSettingsComposite(parent, 0);

        return this.preferencesComposite;
    }

    @Override
    public void init(IWorkbench workbench) {
        // second parameter is typically the plug-in id
        setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, "de.tubs.skeditor.preferences.page"));
        //setDescription("Select preferred solver");
    }

}
