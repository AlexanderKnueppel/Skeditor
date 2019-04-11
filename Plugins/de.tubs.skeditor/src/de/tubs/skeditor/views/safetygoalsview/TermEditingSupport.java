package de.tubs.skeditor.views.safetygoalsview;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

import SkillGraph.Requirement;

public class TermEditingSupport extends EditingSupport {

	public TermEditingSupport(ColumnViewer viewer) {
		super(viewer);
	}

	@Override
	protected boolean canEdit(Object element) {
		return element instanceof Requirement;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return new TextCellEditor((Composite) getViewer().getControl());
	}

	@Override
	protected Object getValue(Object element) {
		return ((Requirement) element).getTerm();
	}

	@Override
	protected void setValue(Object element, Object inputValue) {
		Requirement req = ((Requirement) element);
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(req);
		domain.getCommandStack().execute(new RecordingCommand(domain) {
			@Override
			protected void doExecute() {
				req.eSet(req.eClass().getEStructuralFeature("term"), inputValue.toString());

			}
		});
		getViewer().update(element, null);
	}

}
