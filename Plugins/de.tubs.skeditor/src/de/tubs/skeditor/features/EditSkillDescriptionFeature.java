/**
* The feature gives the opportunity to add a controller to a skill and to edit the controller. 
* 
*
* @author  Arne Windeler
* @version 1.0
* @since   2020-01-14 
*/

package de.tubs.skeditor.features;

import java.io.IOException;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import SkillGraph.Controller;
import SkillGraph.Node;
import SkillGraph.SkillGraphFactory;
import de.tubs.skeditor.sdl.SDLModel;
import de.tubs.skeditor.ui.handler.SkillDescriptionLanguageDialog;
import de.tubs.skeditor.ui.handler.SkillDescriptionLanguageHandler;

/**
 * Class that allows to add and edit code of methods
 * 
 * @author Alex
 *
 */
public class EditSkillDescriptionFeature extends AbstractCustomFeature {

	public EditSkillDescriptionFeature(IFeatureProvider fp) {
		super(fp);
	}

	/**
	 * boolean that indicate if something changes
	 */
	private boolean hasDoneChanges = false;

	@Override
	public String getName() {
		return "Skill Description Language";
	}

	@Override
	public String getDescription() {
		return "Edit Skill Description of a Node";
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
	
	private String getSDLCategory(String category) {
		switch(category) {
		case "main":
			return "Main";
		case "sensor":
			return "Sensor";
		case "actuator":
			return "Actuator";
		case "action":
			return "Action";
		case "perception":
			return "Perception";
		case "planning":
			return "Planning";
		case "observable_external_behavior":
			return "ObservableBehavior";
		}
		return "unknown";
	}

	@Override
	public void execute(ICustomContext context) {
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof Node) {
				Node node = (Node) bo;
				
				String content = "";
				
				if(node.getSDLModel() != null) {
					content = node.getSDLModel();
				} else {
					String name = node.getName();
					String type = getSDLCategory(node.getCategory().getName());
					
					//content += "/* Empty skill representation */";
					content += "skill \"" + name + "\" : " + type + " {\n"
							+ "/* Empty Skill Description */ \n"
							+ "}";
				}
				
				SkillDescriptionLanguageHandler descHandler = new SkillDescriptionLanguageHandler();
				try {
					String changedModel = descHandler.openDialog(content);
					if(changedModel != null) {
						node.setSDLModel(changedModel);
					}
					getFeatureProvider().getDiagramTypeProvider().getDiagramBehavior().refresh();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public boolean hasDoneChanges() {
		return this.hasDoneChanges;
	}

	/**
	 * Returns the currently active Shell.
	 * 
	 * @return The currently active Shell.
	 */
	private static Shell getShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}
}