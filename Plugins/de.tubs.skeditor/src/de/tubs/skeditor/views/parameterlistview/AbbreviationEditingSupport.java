package de.tubs.skeditor.views.parameterlistview;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Composite;

import SkillGraph.Parameter;
import de.tubs.skeditor.utils.ParameterUtil;

public class AbbreviationEditingSupport extends EditingSupport {
	
	public static String INVALID_NAME = "#INVALID#";

	public AbbreviationEditingSupport(ColumnViewer viewer) {
		super(viewer);
	}

	@Override
	protected boolean canEdit(Object element) {
		Parameter parameter = (Parameter) element;
		return !parameter.isVariable() && !ParameterUtil.isGlobalParameter(parameter.getAbbreviation());
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return new TextCellEditor((Composite) getViewer().getControl());
	}

	@Override
	protected Object getValue(Object element) {
		return ((Parameter) element).getAbbreviation();
	}

	@Override
	protected void setValue(Object element, Object inputValue) {
		Parameter parameter = ((Parameter) element);
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(parameter);
		domain.getCommandStack().execute(new RecordingCommand(domain) {
			@Override
			protected void doExecute() {
				String name = inputValue.toString();
				for (Parameter other : parameter.getGraph().getParameterList()) {
					if (other != parameter && parameter.getName().equals(name)) {
						name = INVALID_NAME;
					}
				}
				parameter.eSet(parameter.eClass().getEStructuralFeature("abbreviation"), name);

			}
		});
		getViewer().update(element, null);
	}

}
