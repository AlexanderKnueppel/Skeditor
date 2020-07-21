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
import SkillGraph.Graph;
import SkillGraph.SkillGraphFactory;




/**
 * Class that allows to add and edit code of methods
 * @author Tobias
 *
 */
public class SetRootNodeFeature extends AbstractCustomFeature {
	
	public SetRootNodeFeature(IFeatureProvider fp) {
		super(fp);
		// TODO Auto-generated constructor stub
	}

	/**
	 * boolean that indicate if something changes
	 */
    private boolean hasDoneChanges = false;
    

 
    @Override
    public String getName() {
        return "Set Root Node";
    }
 
    @Override
    public String getDescription() {
        return "Set this Node as Root Node";
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
            	this.hasDoneChanges = true;
            	Node node = (Node) bo;
            	Graph g = (Graph) node.eContainer();
            	g.setRootNode(node);
            	getFeatureProvider().getDiagramTypeProvider().getDiagramBehavior().refresh();
                
            	
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