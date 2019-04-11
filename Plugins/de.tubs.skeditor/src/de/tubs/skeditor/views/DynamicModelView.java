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

import SkillGraph.Equation;
import SkillGraph.Node;
import SkillGraph.SkillGraphFactory;
import de.tubs.skeditor.views.dynamicmodelview.ContentProvider;
import de.tubs.skeditor.views.dynamicmodelview.DynamicModelRow;
import de.tubs.skeditor.views.dynamicmodelview.EquationEditingSupport;
import de.tubs.skeditor.views.dynamicmodelview.LabelProvider;
import de.tubs.skeditor.views.dynamicmodelview.PathEditingSupport;

public class DynamicModelView extends ViewPart {

	private TableViewer viewer;

	public TableViewer getViewer() {
		return viewer;
	}

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.FULL_SELECTION);
		viewer.getTable().setLinesVisible(true);
		viewer.getTable().setHeaderVisible(true);

		TableViewerColumn columnEquation = new TableViewerColumn(viewer, SWT.NONE);
		columnEquation.getColumn().setWidth(600);
		columnEquation.getColumn().setText("Equations");
		columnEquation.setEditingSupport(new EquationEditingSupport(viewer));

		TableViewerColumn columnPreConditions = new TableViewerColumn(viewer, SWT.NONE);
		columnPreConditions.getColumn().setWidth(200);
		columnPreConditions.getColumn().setText("PreConditions");

		TableViewerColumn columnPostConditions = new TableViewerColumn(viewer, SWT.NONE);
		columnPostConditions.getColumn().setWidth(200);
		columnPostConditions.getColumn().setText("PostConditions");

		TableViewerColumn columnFileLink = new TableViewerColumn(viewer, SWT.NONE);
		columnFileLink.getColumn().setWidth(300);
		columnFileLink.getColumn().setText("Path for File (manual for now)");
		columnFileLink.setEditingSupport(new PathEditingSupport(viewer));

		viewer.setContentProvider(new ContentProvider());
		viewer.setLabelProvider(new LabelProvider());

		Button button = new Button(parent, SWT.NONE);
		button.setText("Add new Equation");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Node node = (Node) viewer.getInput();

				Equation equation = SkillGraphFactory.eINSTANCE.createEquation();
				equation.setEquation("New Eq");

				TransactionalEditingDomain nodeDomain = TransactionUtil.getEditingDomain(node);
				nodeDomain.getCommandStack().execute(new RecordingCommand(nodeDomain) {
					@Override
					protected void doExecute() {
						node.getEquations().add(equation);
					}
				});
				TransactionalEditingDomain eqDomain = TransactionUtil.getEditingDomain(equation);
				eqDomain.getCommandStack().execute(new RecordingCommand(eqDomain) {
					@Override
					protected void doExecute() {
						equation.setNode(node);
					}
				});
				viewer.refresh();
			}
		});

		MenuManager manager = new MenuManager();
		viewer.getControl().setMenu(manager.createContextMenu(viewer.getControl()));
		manager.add(new Action("Delete Equation") {
			@Override
			public void run() {
				Equation equation = ((DynamicModelRow) viewer.getStructuredSelection().getFirstElement()).getEquation();
				Node node = ((Node) viewer.getInput());

				TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(node);
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						node.getEquations().remove(equation);
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
