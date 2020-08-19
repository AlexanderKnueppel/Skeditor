package de.tubs.skeditor.synthesis.dialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListSelectionDialog;

import SkillGraph.Node;
import SkillGraph.Parameter;

public class VariableInputDialog extends ListSelectionDialog {
	static final int REQUIRED = 33;
	static final int PROVIDED = 44;
	private int variableType = 0;
	Button reqRadio, provRadio;
	
	public VariableInputDialog(Shell parentShell, Object input, IStructuredContentProvider contentProvider, ILabelProvider labelProvider, String message) {
		super(parentShell, input, contentProvider, labelProvider, message);
	}
		
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		reqRadio = new Button(parent, SWT.RADIO);
		reqRadio.setText("required");
		reqRadio.setSelection(true);
		variableType = REQUIRED;
		provRadio = new Button(parent, SWT.RADIO);
		provRadio.setText("provided");
		super.createButtonsForButtonBar(parent);
	}
	
	/*@Override
	protected Control createDialogArea(Composite parent) {
		Composite comp = (Composite) super.createDialogArea(parent);
		getViewer().addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				for(Object checked : getViewer().getCheckedElements()) {
					if(checked != event.getElement()) {
						getViewer().setChecked(checked, false);
					}
				}
			}
		});
		return comp;
	}*/
	
	@Override
    protected void okPressed() {
		if(getViewer().getCheckedElements().length == 0) {
			MessageDialog.openError(getShell(), "Error", "You must at least check one variable");
			return;
		}
		
		Parameter param;
        if(reqRadio.getSelection()) {
        	variableType = REQUIRED;
        	for(Object elem : getViewer().getCheckedElements() ) {
        		param = (Parameter) elem;
        		if(((Node)getViewer().getInput()).getProvidedVariables().contains(param.getAbbreviation())) {
        			MessageDialog.openError(getShell(), "Error", "variable \""+param.getAbbreviation()+"\" already provided!");
        			return;
        		}
        	}
        } else if(provRadio.getSelection()) {
        	variableType = PROVIDED;
        	for(Object elem : getViewer().getCheckedElements() ) {
        		param = (Parameter) elem;
        		if(((Node)getViewer().getInput()).getRequiredVariables().contains(param.getAbbreviation())) {
        			MessageDialog.openError(getShell(), "Error", "variable \""+param.getAbbreviation()+"\" already required!");
        			return;
        		}
        	}
        }
        super.okPressed();
    }
	
	
	public int getVariableType() {
		return variableType;
	}
	
	public void addCheckStateListener(ICheckStateListener listener) {
		getViewer().addCheckStateListener(listener);
	}
}
