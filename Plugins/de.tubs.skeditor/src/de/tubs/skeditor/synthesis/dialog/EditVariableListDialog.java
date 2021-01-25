package de.tubs.skeditor.synthesis.dialog;

import org.eclipse.ui.dialogs.ListDialog;

import SkillGraph.Graph;
import SkillGraph.Node;
import SkillGraph.Parameter;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class EditVariableListDialog extends ListDialog {
	
	static final int ADD_ID = 32; //ID for add button
	static final int DELETE_ID = 35; //return code when add button was pressed
	private Node node;
	private boolean changed;
	
	public EditVariableListDialog(Shell shell, Node node) {
		super(shell);
		this.node = node;
		this.changed = false;
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, ADD_ID, "Add", false);
		createButton(parent, DELETE_ID, "Delete", false);
		super.createButtonsForButtonBar(parent);
	}
	
	@Override
	protected void buttonPressed(int buttonID) {
		if(buttonID == ADD_ID) {
			ILabelProvider labelProvider = new LabelProvider() {
				@Override 
				public String getText(Object element) {
					Parameter param = (Parameter) element;
					return param.getName()+" ["+param.getAbbreviation()+"]";
				}
			};
			IStructuredContentProvider contentProvider = new IStructuredContentProvider() {
				@Override 
				public Object[] getElements(Object input) {
					Node inputNode = (Node) input;
					List<Parameter> params = new ArrayList<Parameter>();
					if(inputNode.eContainer() instanceof Graph) {
						Graph graph = (Graph) inputNode.eContainer();
						params = graph.getParameterList();
					}
					return params.toArray();
				}
			};
			VariableInputDialog dialog = new VariableInputDialog(getShell(), node, contentProvider, labelProvider, "Add required/defined variables for skill");
			if(dialog.open() == Window.OK) {
				Object[] results = dialog.getResult();
				for(Object obj : results) {
					Parameter param = (Parameter) obj;
					switch(dialog.getVariableType()) {
					case  VariableInputDialog.REQUIRED: 
						node.getRequiredVariables().add(param.getAbbreviation()); 
						break;
					
					case VariableInputDialog.DEFINED:
						node.getDefinedVariables().add(param.getAbbreviation()); 
						break;
					}
					this.changed = true;
				}
			}
		} else if(buttonID == DELETE_ID) {
			IStructuredSelection iselection = getTableViewer().getStructuredSelection();
			VariableRow row = (VariableRow) iselection.getFirstElement();
			if(row != null) {
				switch(row.getType()) {
				case REQUIRED: node.getRequiredVariables().remove(row.getName());
						       break;
				case DEFINED: node.getDefinedVariables().remove(row.getName());
						       break;
				}
				changed = true;
			}
			
			
		}
		getTableViewer().refresh();
		super.buttonPressed(buttonID);
	}
	
	/**
	 * indicates whether this dialog changed variables of skill
	 * @return changed, indicator whether variables changed
	 */
	public boolean hasChanged() {
		return changed;
	}
}
