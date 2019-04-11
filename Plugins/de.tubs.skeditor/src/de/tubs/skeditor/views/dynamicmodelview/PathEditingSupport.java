package de.tubs.skeditor.views.dynamicmodelview;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

import SkillGraph.Node;

public class PathEditingSupport extends EditingSupport {

	public PathEditingSupport(ColumnViewer viewer) {
		super(viewer);
	}

	@Override
	protected boolean canEdit(Object element) {
		return ((DynamicModelRow) element).getProgramPath() != null;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return new TextCellEditor((Composite) getViewer().getControl());
	}

	@Override
	protected Object getValue(Object element) {
		return ((DynamicModelRow) element).getProgramPath();
	}

	@Override
	protected void setValue(Object element, Object inputValue) {
		Node node = ((DynamicModelRow) element).getNode();
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(node);
		domain.getCommandStack().execute(new RecordingCommand(domain) {
			@Override
			protected void doExecute() {
				node.eSet(node.eClass().getEStructuralFeature("programPath"), inputValue.toString());

			}
		});
		getViewer().refresh();
	}
}
