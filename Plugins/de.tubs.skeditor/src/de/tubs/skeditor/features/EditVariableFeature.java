package de.tubs.skeditor.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListDialog;

import SkillGraph.Controller;
import SkillGraph.Graph;
import SkillGraph.Node;
import SkillGraph.SkillGraphFactory;
import de.tubs.skeditor.synthesis.dialog.VariableContentProvider;
import de.tubs.skeditor.synthesis.dialog.EditVariableListDialog;
import de.tubs.skeditor.synthesis.dialog.VariableRow;


public class EditVariableFeature extends AbstractCustomFeature{
	public EditVariableFeature(IFeatureProvider fp) {
		super(fp);
		// TODO Auto-generated constructor stub
	}

	/**
	 * boolean that indicate if something changes
	 */
	private boolean hasDoneChanges = false;

	@Override
	public String getName() {
		return "Edit Variables";
	}

	@Override
	public String getDescription() {
		return "Edit required and provided variables of a Node";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof Node) {
				ret = true;
			}
		}
		return ret;
	}

	@Override
	public void execute(ICustomContext context) {
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof Node) {
				Node node = (Node) bo;
				//System.out.println("eResource: "+node.eResource().);
				EditVariableListDialog ldialog = new EditVariableListDialog(getShell(), node);
				ldialog.setTitle("Edit required and provided variables");
				ldialog.setContentProvider(new VariableContentProvider());
				ldialog.setLabelProvider(new ColumnLabelProvider() {
					@Override
					public String getText(Object element) {
						VariableRow row = (VariableRow) element;
						return row.toString();
					}
					
					@Override
		            public Image getImage(Object element) {
		               return null;
		            }
				});
				ldialog.setInput(node);
				ldialog.open();
				this.hasDoneChanges = ldialog.hasChanged();
				updatePictogramElement(((Shape) pes[0]).getContainer());
				getFeatureProvider().getDiagramTypeProvider().getDiagramBehavior().refresh();
			}

		}
	}

	@Override
	public boolean hasDoneChanges() {
		return this.hasDoneChanges;
	}

	/**
	 * Helper method that asks for a String as input
	 * 
	 * @param dialogTitle   The title of the dialog
	 * @param dialogMessage A message inside the dialog
	 * @param initialValue  The current code of the Method
	 * @return The new code of the Method
	 */
	public static String askString(String dialogTitle, String dialogMessage, String initialValue) {
		String ret = null;
		Shell shell = getShell();
		if (initialValue == null) {
			initialValue = "\n\n\n\n";
		}

		MyInputDialog inputDialog = new MyInputDialog(shell, dialogTitle, dialogMessage, initialValue, null);
		int retDialog = inputDialog.open();
		if (retDialog == Window.OK) {
			ret = inputDialog.getValue();
		}
		return ret;
	}

	/**
	 * Returns the currently active Shell.
	 * 
	 * @return The currently active Shell.
	 */
	private static Shell getShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}

//	public void updateController(Node node) {
//        Node n = node;
//		if(!n.getController().isEmpty()) {
//        	Controller cont = n.getController().get(0);
//        	n.getController().clear();
//        	n.getController().add(cont);
//        } else {
//        	Controller con = SkillGraphFactory.eINSTANCE.createController();
//            con.setCtrl(" ");
//            n.getController().add(con);
//        }
//        
//		for(int i = 0; i < n.getChildEdges().size(); i++) {
//        	Node child = n.getChildEdges().get(i).getChildNode();
//        	for(int x = 0; x < child.getController().size(); x++) {
//        		n.getController().add(child.getController().get(x));
//        	}
//        }
//		
//		for(int i = 0; i < n.getParentNodes().size(); i++) {
//			updateController(n.getParentNodes().get(i));
//		}
//	}
}
