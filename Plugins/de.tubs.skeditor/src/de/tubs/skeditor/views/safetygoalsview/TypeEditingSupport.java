package de.tubs.skeditor.views.safetygoalsview;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.swt.widgets.Composite;

import SkillGraph.Requirement;
import SkillGraph.Type;

public class TypeEditingSupport extends EditingSupport {

	public TypeEditingSupport(ColumnViewer viewer) {
		super(viewer);
	}

	@Override
	protected boolean canEdit(Object element) {
		return element instanceof Requirement;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		String[] types = new String[Type.values().length];
		for (int i = 0; i < Type.values().length; i++) {
			types[i] = Type.values()[i].getLiteral();
		}
		return new ComboBoxCellEditor((Composite) getViewer().getControl(), types);
	}

	@Override
	protected Object getValue(Object element) {
		Requirement req = ((Requirement) element);
		return req.getType().getValue();
	}

	@Override
	protected void setValue(Object element, Object inputValue) {
		Requirement req = ((Requirement) element);
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(req);
		domain.getCommandStack().execute(new RecordingCommand(domain) {
			@Override
			protected void doExecute() {
				req.setType((Type.get((int) inputValue)));
			}
		});
		getViewer().update(element, null);
	}
}
