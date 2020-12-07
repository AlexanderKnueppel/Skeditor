package de.tubs.skeditor.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

public class SelectProverGroup extends Group {
	
//    private Button selectZ3RadioButton;
//    private Button selectZ3BrowseButton;
//    private Button selectMathematicaRadioButton;
//    private Button selectMathematicaBrowseButton;

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
		
//		selectZ3RadioButton = new Button(this, SWT.RADIO);
//		selectZ3RadioButton.setText("z3");
//		selectZ3RadioButton.setLocation(20, 20);
//		selectZ3RadioButton.pack();
//		
//		selectZ3BrowseButton = new Button(this, SWT.PUSH);
//		selectZ3BrowseButton.setText("Browse...");
//		selectZ3BrowseButton.setLocation(60, 20);
//		selectZ3BrowseButton.pack();
//
//		selectMathematicaRadioButton = new Button(this, SWT.RADIO);
//		selectMathematicaRadioButton.setText("Mathematica");
//		selectZ3RadioButton.setLocation(20, 40);
//		selectMathematicaRadioButton.pack();
//		
//		selectMathematicaBrowseButton = new Button(this, SWT.PUSH);
//		selectMathematicaBrowseButton.setText("Browse...");
//		selectZ3RadioButton.setLocation(60, 40);
//		selectMathematicaBrowseButton.pack();

		this.pack();
		
	}
	
	private class RadioButtonAndFileBrowserComposite extends Composite {		
		public RadioButtonAndFileBrowserComposite(Composite parent, int style, String radioButtonName) {
			super(parent, style);
			setLayout(new RowLayout(SWT.HORIZONTAL));
			
			Button radioButton = new Button(this, SWT.RADIO);
			radioButton.setText(radioButtonName);
			radioButton.pack();
			
			// TODO right alignment
			
			//Text directoryText = new Text(this, style);
			//directoryText.pack();
			
			Button browseButton = new Button(this, SWT.PUSH);
			browseButton.setText("Browse...");
			browseButton.pack();
		}
	}
		
    @Override
    protected void checkSubclass() {
        //  allow subclass
        System.out.println("info   : checking menu subclass");
    }

}
