package de.tubs.skeditor.views.parameterlistview;

import java.util.regex.Pattern;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

import SkillGraph.Parameter;

public class ValueEditingSupport extends EditingSupport {

	public ValueEditingSupport(ColumnViewer viewer) {
		super(viewer);
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		TextCellEditor editor = new TextCellEditor((Composite) getViewer().getControl());
		editor.setValidator(new ICellEditorValidator() {
			@Override
			public String isValid(Object value) {
				if (Pattern.matches("((\\d*)(.\\d*)?((\\s)?\\[(\\d+)(\\.\\d+)?(\\s)?:(\\s)?(\\d+)(\\.\\d+)?\\])?)?", String.valueOf(value))) {
					return null; //checks if Pattern has form: "5" or "5 [3 : 5]" or "[3 : 5]"
				}
				return "INVALID";
			}
		});
		return editor;
	}

	@Override
	protected Object getValue(Object element) {
		return ((Parameter) element).getDefaultValue();
	}

	@Override
	protected void setValue(Object element, Object inputValue) {
		Parameter parameter = ((Parameter) element);
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(parameter);
		domain.getCommandStack().execute(new RecordingCommand(domain) {
			@Override
			protected void doExecute() {

				parameter.eSet(parameter.eClass().getEStructuralFeature("defaultValue"), String.valueOf(inputValue));

			}
		});
		getViewer().update(element, null);
	}

}
