package de.tubs.skeditor.wizards;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

import de.tubs.skeditor.synthesis.Requirement;

public class GraphSynthesisEditingSupport extends EditingSupport {

    
	public GraphSynthesisEditingSupport(TableViewer viewer) {
		super(viewer);
	}
	
	@Override
	protected CellEditor getCellEditor(Object element) {
		// TODO Auto-generated method stub
		return new TextCellEditor((Composite) getViewer().getControl());
	}

	@Override
	protected boolean canEdit(Object element) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		// TODO Auto-generated method stub
		return ((Requirement) element).getFormula();
	}

	@Override
	protected void setValue(Object element, Object value) {
		if(element instanceof Requirement) {
			Requirement req = (Requirement) element;
			req.setFormula(String.valueOf(value));
			getViewer().update(element, null);
		}
	}

}
