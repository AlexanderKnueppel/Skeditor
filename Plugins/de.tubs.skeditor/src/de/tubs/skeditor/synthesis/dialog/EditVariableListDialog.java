package de.tubs.skeditor.synthesis.dialog;

//import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListDialog;

import SkillGraph.Graph;
import SkillGraph.Node;
import SkillGraph.Parameter;

import org.eclipse.jface.dialogs.InputDialog;
//import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class EditVariableListDialog extends ListDialog {
	
	static final int ADD_ID = 32; //ID for add button
	static final int ADDPRESSED = 35; //return code when add button was pressed
	Node node;
	
	public EditVariableListDialog(Shell shell, Node node) {
		super(shell);
		this.node = node;
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, ADD_ID, "Add", false);
		super.createButtonsForButtonBar(parent);
		/*addButton.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e){
				VariableInputDialog dialog = new VariableInputDialog(getShell(), "Add Variable", "Add required/provided variables for node", "", null);
				Pattern pattern = Pattern.compile("\\w+");
				Matcher matcher;
				
				do {
					matcher = pattern.matcher(dialog.getValue());
					if (!matcher.matches()) {
						MessageDialog.openError(getShell(), "Error", "Bad name for variable");
					}
				}while(dialog.open() == Window.OK && !matcher.matches()); //repeat until vali
				
				switch(dialog.getVariableType()) {
					case  VariableInputDialog.REQUIRED: 
						node.getRequiredVariables().add(dialog.getValue()); 
						break;
					
					case VariableInputDialog.PROVIDED:
						node.getProvidedVariables().add(dialog.getValue()); 
						break;
				}
				
			}
		});*/
	}
	/*
	@Override
	protected Control createDialogArea(Composite parent) {
		setContentProvider(new ContentProvider());
		Control ctrl = super.createDialogArea(parent);
		TableViewerColumn columnName = new TableViewerColumn(getTableViewer(), SWT.NONE);
		columnName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				VariableRow row = (VariableRow) element;
				return row.getName();
			}
			
			@Override
            public Image getImage(Object element) {
               return null;
            }
		});
		TableViewerColumn columnType = new TableViewerColumn(getTableViewer(), SWT.NONE);
		columnType.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				VariableRow row = (VariableRow) element;
				return row.getTypeAsString();
			}
			
			@Override
            public Image getImage(Object element) {
               return null;
            }
		});
		columnName.getColumn().setWidth(200);
		columnName.getColumn().setText("Name");
		columnType.getColumn().setWidth(50);
		columnType.getColumn().setText("Type");
		
		return ctrl;
	}*/
	
	@Override
	protected void buttonPressed(int buttonID) {
		if(buttonID == ADD_ID) {
			ILabelProvider labelProvider = new LabelProvider() {
				@Override 
				public String getText(Object element) {
					Parameter param = (Parameter) element;
					return param.getName()+"["+param.getAbbreviation()+"]";
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
			VariableInputDialog dialog = new VariableInputDialog(getShell(), node, contentProvider, labelProvider, "Add required/provided variables for node");
			if(dialog.open() == Window.OK) {
				Object[] results = dialog.getResult();
				if(results[0] instanceof Parameter) {
					System.out.println("ist ein parameter");
				}
				for(Object obj : results) {
					Parameter param = (Parameter) obj;
					switch(dialog.getVariableType()) {
					case  VariableInputDialog.REQUIRED: 
						node.getRequiredVariables().add(param.getAbbreviation()); 
						break;
					
					case VariableInputDialog.PROVIDED:
						node.getProvidedVariables().add(param.getAbbreviation()); 
						break;
					}
				}
				/*Parameter param = (Parameter) results[0];
				switch(dialog.getVariableType()) {
				case  VariableInputDialog.REQUIRED: 
					node.getRequiredVariables().add(param.getAbbreviation()); 
					break;
				
				case VariableInputDialog.PROVIDED:
					node.getProvidedVariables().add(param.getAbbreviation()); 
					break;
				}*/
			}
			System.out.println("required: "+node.getRequiredVariables());
			System.out.println("provided: "+node.getProvidedVariables());
			if(node.eContainer() instanceof Graph) {
				System.out.println("eResource ist Graph ");
			}
		}
		getTableViewer().refresh();
		super.buttonPressed(buttonID);
	}
}
