package de.tubs.skeditor.diagram;

import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;

public class SkeditorDiagramEditor extends DiagramEditor {
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		super.selectionChanged(part, selection);

		if (selection instanceof IStructuredSelection) {
			// Do nothing at the moment
		}
	}
}
