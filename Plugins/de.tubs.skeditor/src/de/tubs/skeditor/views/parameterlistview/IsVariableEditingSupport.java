package de.tubs.skeditor.views.parameterlistview;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.swt.SWT;

import SkillGraph.Parameter;

public class IsVariableEditingSupport extends EditingSupport {

	public IsVariableEditingSupport(ColumnViewer viewer) {
		super(viewer);
	}

	@Override
	protected boolean canEdit(Object arg0) {
		return true;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return new CheckboxCellEditor(null, SWT.CHECK | SWT.READ_ONLY);
	}

	@Override
	protected Object getValue(Object element) {
		Parameter parameter = (Parameter) element;
		return parameter.isVariable();
	}

	@Override
	protected void setValue(Object element, Object value) {
		Parameter parameter = ((Parameter) element);
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(parameter);
		domain.getCommandStack().execute(new RecordingCommand(domain) {
			@Override
			protected void doExecute() {
				parameter.eSet(parameter.eClass().getEStructuralFeature("variable"), (boolean) value);

			}
		});
		System.out.println((boolean) value);
		getViewer().update(element, null);
	}
}
