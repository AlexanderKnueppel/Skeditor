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
import de.tubs.skeditor.utils.ParameterUtil.ParameterValue;

public class ValueEditingSupport extends EditingSupport {

	public ValueEditingSupport(ColumnViewer viewer) {
		super(viewer);
	}
	
	@Override
	protected boolean canEdit(Object element) {
		return true;
	}
	
//	public static void main(String[] args) {
//		String match = "^((\\d*)(.\\d*)?)$"; // e.g., 2.7
//		match += "|^(\\[:\\])$"; // closed interval [x:y]
//		match += "|^(\\[((\\d*)(.\\d*)?)?\\s*:\\s*((\\d*)(.\\d*)?)?\\))$"; // half-closed interval [x:y)
//		match += "|^(\\(((\\d*)(.\\d*)?)?\\s*:\\s*((\\d*)(.\\d*)?)?\\])$"; // half-closed interval (x:y]
//		match += "|^(\\(((\\d*)(.\\d*)?)?\\s*:\\s*((\\d*)(.\\d*)?)?\\))$"; // open interval (x:y)
//		
//		String value = "[1:1)";
//		if (!Pattern.matches(match, String.valueOf(value).trim())) {
//			System.out.println("Not matched");
//		}
//	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		TextCellEditor editor = new TextCellEditor((Composite) getViewer().getControl());
		editor.setValidator(new ICellEditorValidator() {
			@Override
			public String isValid(Object value) {
				//if (Pattern.matches("((\\d*)(.\\d*)?((\\s)?\\[(\\d+)(\\.\\d+)?(\\s)?:(\\s)?(\\d+)(\\.\\d+)?\\])?)?", String.valueOf(value))) {
//				String match = "^((\\d*)(.\\d*)?)$"; // e.g., 2.7
//				match += "|^(\\[((\\d*)(.\\d*)?)?\\s*:\\s*((\\d*)(.\\d*)?)?\\])$"; // closed interval [x:y]
//				match += "|^(\\[((\\d*)(.\\d*)?)?\\s*:\\s*((\\d*)(.\\d*)?)?\\))$"; // half-closed interval [x:y)
//				match += "|^(\\(((\\d*)(.\\d*)?)?\\s*:\\s*((\\d*)(.\\d*)?)?\\])$"; // half-closed interval (x:y]
//				match += "|^(\\(((\\d*)(.\\d*)?)?\\s*:\\s*((\\d*)(.\\d*)?)?\\))$"; // open interval (x:y)
				if (Pattern.matches(ParameterValue.getCompleteMatch(), String.valueOf(value).trim())) {
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
