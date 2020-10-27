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
import de.tubs.skeditor.views.variableview.ContentProvider;
import de.tubs.skeditor.views.variableview.LabelProvider;

public class VariableView extends ViewPart {

	private TableViewer viewer;

	public TableViewer getViewer() {
		return viewer;
	}
	
	@Override
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.FULL_SELECTION);
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);

		TableViewerColumn columnNode = new TableViewerColumn(viewer, SWT.NONE);
		columnNode.getColumn().setWidth(200);
		columnNode.getColumn().setText("Skill");

		TableViewerColumn columnDefined = new TableViewerColumn(viewer, SWT.NONE);
		columnDefined.getColumn().setWidth(200);
		columnDefined.getColumn().setText("Defined");

		TableViewerColumn columnRequired = new TableViewerColumn(viewer, SWT.NONE);
		columnRequired.getColumn().setWidth(200);
		columnRequired.getColumn().setText("Required");

		viewer.setContentProvider(new ContentProvider());
		viewer.setLabelProvider(new LabelProvider());

		GridLayoutFactory.fillDefaults().generateLayout(parent);
	}

	@Override
	public void setFocus() {
	}

}
