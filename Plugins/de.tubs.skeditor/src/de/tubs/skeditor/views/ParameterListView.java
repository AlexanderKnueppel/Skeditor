package de.tubs.skeditor.views;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import SkillGraph.Graph;
import SkillGraph.Parameter;
import SkillGraph.SkillGraphFactory;
import de.tubs.skeditor.views.parameterlistview.AbbreviationEditingSupport;
import de.tubs.skeditor.views.parameterlistview.ContentProvider;
import de.tubs.skeditor.views.parameterlistview.IsVariableEditingSupport;
import de.tubs.skeditor.views.parameterlistview.LabelProvider;
import de.tubs.skeditor.views.parameterlistview.NameEditingSupport;
import de.tubs.skeditor.views.parameterlistview.UnitEditingSupport;
import de.tubs.skeditor.views.parameterlistview.ValueEditingSupport;

public class ParameterListView extends ViewPart {

	private TableViewer viewer;

	public TableViewer getViewer() {
		return viewer;
	}

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.FULL_SELECTION);
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);

		TableViewerColumn columnName = new TableViewerColumn(viewer, SWT.NONE);
		columnName.getColumn().setWidth(200);
		columnName.getColumn().setText("Name");
		columnName.setEditingSupport(new NameEditingSupport(viewer));

		TableViewerColumn columnAbbreviation = new TableViewerColumn(viewer, SWT.NONE);
		columnAbbreviation.getColumn().setWidth(100);
		columnAbbreviation.getColumn().setText("Abbreviation");
		columnAbbreviation.setEditingSupport(new AbbreviationEditingSupport(viewer));

		TableViewerColumn columnUnit = new TableViewerColumn(viewer, SWT.NONE);
		columnUnit.getColumn().setWidth(50);
		columnUnit.getColumn().setText("Unit");
		columnUnit.setEditingSupport(new UnitEditingSupport(viewer));

		TableViewerColumn columnDefaultValue = new TableViewerColumn(viewer, SWT.NONE);
		columnDefaultValue.getColumn().setWidth(100);
		columnDefaultValue.getColumn().setText("Value [range]");
		columnDefaultValue.setEditingSupport(new ValueEditingSupport(viewer));

		TableViewerColumn columnIsVariable = new TableViewerColumn(viewer, SWT.NONE);
		columnIsVariable.getColumn().setWidth(50);
		columnIsVariable.getColumn().setText("var");
		columnIsVariable.setEditingSupport(new IsVariableEditingSupport(viewer));

		viewer.setContentProvider(new ContentProvider());
		viewer.setLabelProvider(new LabelProvider());

		Button button = new Button(parent, SWT.NONE);
		button.setText("Add new parameter");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Parameter parameter = SkillGraphFactory.eINSTANCE.createParameter();
				parameter.setName("Acceleration");
				parameter.setAbbreviation("a");
				parameter.setUnit("m/s²");
				parameter.setDefaultValue("");
				Graph graph = (Graph) viewer.getInput();
				TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(graph);
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						graph.getParameterList().add(parameter);
					}
				});
				viewer.refresh();
			}
		});

		MenuManager manager = new MenuManager();
		viewer.getControl().setMenu(manager.createContextMenu(viewer.getControl()));
		manager.add(new Action("Delete Parameter") {
			@Override
			public void run() {
				Parameter deletParameter = (Parameter) viewer.getStructuredSelection().getFirstElement();
				Graph graph = ((Graph) viewer.getInput());

				TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(graph);
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						graph.getParameterList().remove(deletParameter);
					}
				});
				viewer.refresh();
			}
		});

		GridLayoutFactory.fillDefaults().generateLayout(parent);
	}

	@Override
	public void setFocus() {
	}

}
