package de.tubs.skeditor.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Group of the widgets to select a prover and specify their paths
 * 
 * @author Dibo Gonda
 *
 */
public class SelectProverGroup extends Group {

	private List<Button> radioButtons;
	private List<Text> textAreas;
	private List<Button> browseButtons;

	public SelectProverGroup(Composite parent, int style) {
		super(parent, style);
		setText("Select preferred prover");
		setLayout(new RowLayout(SWT.VERTICAL));
		createWidgets();
	}

	private void createWidgets() {
		this.radioButtons = new ArrayList<>();
		this.textAreas = new ArrayList<>();
		this.browseButtons = new ArrayList<>();

		RadioButtonAndFileBrowserComposite z3Composite = new RadioButtonAndFileBrowserComposite(this, 0, "z3");
		z3Composite.pack();

		RadioButtonAndFileBrowserComposite mathematicaComposite = new RadioButtonAndFileBrowserComposite(this, 0,
				"Mathematica");
		mathematicaComposite.pack();

		this.pack();
	}

	private class RadioButtonAndFileBrowserComposite extends Composite {
		public RadioButtonAndFileBrowserComposite(SelectProverGroup parent, int style, String radioButtonName) {
			super(parent, style);
			setLayout(new RowLayout(SWT.HORIZONTAL));

			Button radioButton = new Button(this, SWT.RADIO);
			radioButton.setText(radioButtonName);
			RowData buttonLayoutData = new RowData();
			buttonLayoutData.width = 100;
			radioButton.setLayoutData(buttonLayoutData);
			radioButton.addSelectionListener(new RadioButtonListener(parent));
			radioButton.pack();

			parent.getRadioButtons().add(radioButton);

			Text directoryText = new Text(this, SWT.BORDER);
			RowData textLayoutData = new RowData();
			textLayoutData.width = 200;
			directoryText.setLayoutData(textLayoutData);
			directoryText.pack();
			
			parent.getTextAreas().add(directoryText);

			Button browseButton = new Button(this, SWT.PUSH);
			browseButton.setText("Browse...");
			browseButton.addSelectionListener(new DirectoryDialogSelectionListener(this.getShell(), directoryText, ""));
			browseButton.pack();
			
			parent.getBrowseButtons().add(browseButton);
		}
	}

	public void selectButtonExclusively(Button selectedRadioButton) {
		for (int i = 0; i < this.radioButtons.size(); i++) {
			Button radioButton = this.radioButtons.get(i);
			Text text = this.textAreas.get(i);
			Button browseButton = this.browseButtons.get(i);
			if (!radioButton.equals(selectedRadioButton)) {
				radioButton.setSelection(false);
				text.setEnabled(false);
				browseButton.setEnabled(false);
			} else {
				selectedRadioButton.setSelection(true);
				text.setEnabled(true);
				browseButton.setEnabled(true);

			}
		}
	}

	public List<Button> getRadioButtons() {
		return this.radioButtons;
	}
	
	public List<Text> getTextAreas() {
		return this.textAreas;
	}
	
	public List<Button> getBrowseButtons() {
		return this.browseButtons;
	}

	@Override
	protected void checkSubclass() {
		// allow subclass
	}

}
