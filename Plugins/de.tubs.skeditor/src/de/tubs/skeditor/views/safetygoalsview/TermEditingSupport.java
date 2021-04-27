package de.tubs.skeditor.views.safetygoalsview;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

import SkillGraph.Assumption;
import SkillGraph.Requirement;

public class TermEditingSupport extends EditingSupport {

	public TermEditingSupport(ColumnViewer viewer) {
		super(viewer);
	}

	@Override
	protected boolean canEdit(Object element) {
		return element instanceof Requirement || element instanceof Assumption;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return new TextCellEditor((Composite) getViewer().getControl());
	}

	@Override
	protected Object getValue(Object element) {
		if(element instanceof Requirement) 
			return ((Requirement) element).getTerm();
		else
			return ((Assumption) element).getTerm();
	}

	@Override
	protected void setValue(Object element, Object inputValue) {
		if(element instanceof Requirement) {
			Requirement req = ((Requirement) element);
			TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(req);
			domain.getCommandStack().execute(new RecordingCommand(domain) {
				@Override
				protected void doExecute() {
					req.eSet(req.eClass().getEStructuralFeature("term"), inputValue.toString());

				}
			});
		} else if(element instanceof Assumption) {
			Assumption req = ((Assumption) element);
			TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(req);
			domain.getCommandStack().execute(new RecordingCommand(domain) {
				@Override
				protected void doExecute() {
					req.eSet(req.eClass().getEStructuralFeature("term"), inputValue.toString());

				}
			});
		}
		getViewer().update(element, null);
	}

}
