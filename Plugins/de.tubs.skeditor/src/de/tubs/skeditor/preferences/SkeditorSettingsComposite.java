package de.tubs.skeditor.preferences;

import org.eclipse.swt.widgets.Composite;

public class SkeditorSettingsComposite extends Composite {

	private SelectProverGroup selectProverGroup;

	public SkeditorSettingsComposite(Composite parent, int style) {
		super(parent, style);
		createProverGroup(this);
	}

	private void createProverGroup(Composite parent) {
		selectProverGroup = new SelectProverGroup(parent, 0);
	}

	public SelectProverGroup getSelectProverGroup() {
		return this.selectProverGroup;
	}

	@Override
	protected void checkSubclass() {
		// allow subclass
	}
}
