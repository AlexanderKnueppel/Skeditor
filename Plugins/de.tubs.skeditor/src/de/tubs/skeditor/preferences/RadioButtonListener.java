package de.tubs.skeditor.preferences;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;

public class RadioButtonListener extends SelectionAdapter {

	private SelectProverGroup selectProverGroup;

	public RadioButtonListener(SelectProverGroup selectProverGroup) {
		this.selectProverGroup = selectProverGroup;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if (e.getSource() instanceof Button) {
			Button selectedRadioButton = (Button) e.getSource();
			//selectProverGroup.selectButtonExclusively(selectedRadioButton);
		}
	}

}
