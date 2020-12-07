package de.tubs.skeditor.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

/**
 * Group of the widgets to select a prover and specify their paths
 * @author Dibo Gonda
 *
 */
public class SelectProverGroup extends Group {
	
	public SelectProverGroup(Composite parent, int style) {
		super(parent, style);
		setText("Select preferred prover");
		setLayout(new RowLayout(SWT.VERTICAL));
		createWidgets();
	}
	
	private void createWidgets() {
		RadioButtonAndFileBrowserComposite z3Composite = new RadioButtonAndFileBrowserComposite(this, 0, "z3");
		z3Composite.pack();
		
		RadioButtonAndFileBrowserComposite mathematicaComposite = new RadioButtonAndFileBrowserComposite(this, 0, "Mathematica");
		mathematicaComposite.pack();
		
		this.pack();	
	}
	
	private class RadioButtonAndFileBrowserComposite extends Composite {		
		public RadioButtonAndFileBrowserComposite(Composite parent, int style, String radioButtonName) {
			super(parent, style);
			setLayout(new RowLayout(SWT.HORIZONTAL));
			
			Button radioButton = new Button(this, SWT.RADIO);
			radioButton.setText(radioButtonName);
			RowData buttonLayoutData = new RowData();
			buttonLayoutData.width = 100;
	        radioButton.setLayoutData(buttonLayoutData);
			radioButton.pack();
						
			Text directoryText = new Text(this, SWT.BORDER);
			RowData textLayoutData = new RowData();
			textLayoutData.width = 200;
	        directoryText.setLayoutData(textLayoutData);
			directoryText.pack();
			
			Button browseButton = new Button(this, SWT.PUSH);
			browseButton.setText("Browse...");
			browseButton.pack();
		}
	}
		
    @Override
    protected void checkSubclass() {
        //  allow subclass
    }

}
