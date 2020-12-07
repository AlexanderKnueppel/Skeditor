package de.tubs.skeditor.preferences;

import org.eclipse.swt.widgets.Composite;


public class SkeditorSettingsComposite extends Composite {
	
	private SelectProverGroup selectProverGroup;


	public SkeditorSettingsComposite(Composite parent, int style) {
		super(parent, style);
		createProverGroup(this);
	}
	
	private void createProverGroup(Composite parent) {
		// TODO int
		selectProverGroup = new SelectProverGroup(parent, 0);
	}
	
    public static final SkeditorPreferencesCompositeBuilder builder(Composite parent) {
        return new SkeditorPreferencesCompositeBuilder(parent);
    }
    
    public static class SkeditorPreferencesCompositeBuilder {
        private Composite parent;
        private SkeditorPreferencesCompositeBuilder(Composite parent) {
            this.parent = parent;
        }

        public SkeditorSettingsComposite build() {
                return new SkeditorSettingsComposite(this.parent, 0);
            }
        }
    
    @Override
    protected void checkSubclass() {
        //  allow subclass
        System.out.println("info   : checking menu subclass");
    }
}


