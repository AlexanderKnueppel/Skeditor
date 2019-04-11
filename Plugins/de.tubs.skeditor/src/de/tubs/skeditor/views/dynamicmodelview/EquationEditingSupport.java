package de.tubs.skeditor.views.dynamicmodelview;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

import SkillGraph.Equation;

public class EquationEditingSupport extends EditingSupport {

	public EquationEditingSupport(ColumnViewer viewer) {
		super(viewer);
	}

	@Override
	protected boolean canEdit(Object element) {
		DynamicModelRow diffRow = ((DynamicModelRow) element);
		return diffRow.getEquation() != null && diffRow.isEditable();
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return new TextCellEditor((Composite) getViewer().getControl());
	}

	@Override
	protected Object getValue(Object element) {
		return ((DynamicModelRow) element).getEquation().getEquation();
	}

	@Override
	protected void setValue(Object element, Object inputValue) {
		Equation eq = ((DynamicModelRow) element).getEquation();
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(eq);
		domain.getCommandStack().execute(new RecordingCommand(domain) {
			@Override
			protected void doExecute() {
				eq.eSet(eq.eClass().getEStructuralFeature("equation"), inputValue.toString());
			}
		});
		getViewer().update(element, null);
	}

}
