package de.tubs.skeditor.preferences;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class SkeditorSettingsComposite extends Composite {

	private ProverSettingsGroup selectProverGroup;

	public SkeditorSettingsComposite(Composite parent, int style) {
		super(parent, style);
		createProverGroup(this);
	}

	private void createProverGroup(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		selectProverGroup = new ProverSettingsGroup(parent, 0);
	}

	public ProverSettingsGroup getSelectProverGroup() {
		return this.selectProverGroup;
	}

	@Override
	protected void checkSubclass() {
		// allow subclass
	}
}
