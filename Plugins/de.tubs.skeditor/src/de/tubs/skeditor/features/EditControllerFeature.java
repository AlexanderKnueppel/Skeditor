/**
* The feature gives the opportunity to add a controller to a skill and to edit the controller. 
* 
*
* @author  Arne Windeler
* @version 1.0
* @since   2020-01-14 
*/

package de.tubs.skeditor.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import SkillGraph.Controller;
import SkillGraph.Node;
import SkillGraph.SkillGraphFactory;

/**
 * Class that allows to add and edit code of methods
 * 
 * @author Tobias
 *
 */
public class EditControllerFeature extends AbstractCustomFeature {

	public EditControllerFeature(IFeatureProvider fp) {
		super(fp);
		// TODO Auto-generated constructor stub
	}

	/**
	 * boolean that indicate if something changes
	 */
	private boolean hasDoneChanges = false;

	@Override
	public String getName() {
		return "Edit Controller";
	}

	@Override
	public String getDescription() {
		return "Edit Controller of a Node";
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
				String current = "";
				if (!node.getController().isEmpty()) {
					current = node.getController().get(0).getCtrl();
				}

				// ask user for a new comment
				String newComment = askString(getName(), getDescription(), current);
				if (newComment == null || newComment.isEmpty() || newComment.length() == 0) {
					this.hasDoneChanges = true;
					node.getController().clear();
					updatePictogramElement(((Shape) pes[0]).getContainer());
					getFeatureProvider().getDiagramTypeProvider().getDiagramBehavior().refresh();

				} else if (newComment != null && !newComment.equals(current)) {
					this.hasDoneChanges = true;
					node.getController().clear();
					Controller controller = SkillGraphFactory.eINSTANCE.createController();
					controller.setCtrl(newComment);

					controller.setNode(node);
					node.getController().add(controller);
//                    updateController(node);
					updatePictogramElement(((Shape) pes[0]).getContainer());
					getFeatureProvider().getDiagramTypeProvider().getDiagramBehavior().refresh();

				}
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