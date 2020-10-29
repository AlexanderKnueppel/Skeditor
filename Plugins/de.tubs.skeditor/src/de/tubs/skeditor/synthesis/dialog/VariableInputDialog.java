package de.tubs.skeditor.synthesis.dialog;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListSelectionDialog;

import SkillGraph.Node;
import SkillGraph.Parameter;

public class VariableInputDialog extends ListSelectionDialog {
	static final int REQUIRED = 33;
	static final int DEFINED = 44;
	private int variableType = 0;
	Button reqRadio, defRadio;
	
	public VariableInputDialog(Shell parentShell, Object input, IStructuredContentProvider contentProvider, ILabelProvider labelProvider, String message) {
		super(parentShell, input, contentProvider, labelProvider, message);
	}
		
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		reqRadio = new Button(parent, SWT.RADIO);
		reqRadio.setText("required");
		reqRadio.setSelection(true);
		variableType = REQUIRED;
		defRadio = new Button(parent, SWT.RADIO);
		defRadio.setText("defined");
		super.createButtonsForButtonBar(parent);
	}
	
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
        		if(((Node)getViewer().getInput()).getDefinedVariables().contains(param.getAbbreviation())) {
        			MessageDialog.openError(getShell(), "Error", "variable \""+param.getAbbreviation()+"\" already defined!");
        			return;
        		}
        	}
        } else if(defRadio.getSelection()) {
        	variableType = DEFINED;
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
