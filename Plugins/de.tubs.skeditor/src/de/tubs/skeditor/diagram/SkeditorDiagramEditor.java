package de.tubs.skeditor.diagram;

import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.graphiti.ui.platform.GraphitiShapeEditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;

import SkillGraph.Node;
import de.tubs.skeditor.utils.ViewUtil;

public class SkeditorDiagramEditor extends DiagramEditor {
	/*
	 * Update diff. equation view whenever a node is selected.
	 * 
	 * (non-Javadoc)
	 * @see org.eclipse.graphiti.ui.editor.DiagramEditor#selectionChanged(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		super.selectionChanged(part, selection);
		
		if(!(selection instanceof IStructuredSelection)) {
			return;
		}
		
		IStructuredSelection sel = (IStructuredSelection) selection;
		if (selection instanceof IStructuredSelection) {
			if (!sel.isEmpty() && sel.getFirstElement() instanceof GraphitiShapeEditPart) {
				GraphitiShapeEditPart ep = (GraphitiShapeEditPart) sel.getFirstElement();
				if (ep.getPictogramElement() instanceof PictogramElement) {
					PictogramElement pe = ep.getPictogramElement();
					if (pe.getLink() != null && pe.getLink().getBusinessObjects().size() == 1
							&& pe.getLink().getBusinessObjects().get(0) instanceof Node) {
						ViewUtil.updateDiffView((Node) pe.getLink().getBusinessObjects().get(0));
						ViewUtil.updateInfoView((Node) pe.getLink().getBusinessObjects().get(0));
					}
				}
			}
		}
	}
	
}
