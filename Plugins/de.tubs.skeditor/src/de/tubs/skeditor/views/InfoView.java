package de.tubs.skeditor.views;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import de.tubs.skeditor.views.infoview.ContentProvider;
import de.tubs.skeditor.views.infoview.LabelProvider;


public class InfoView extends ViewPart {

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
		columnEquation.getColumn().setWidth(200);
		columnEquation.getColumn().setText("Category");

		TableViewerColumn columnPreConditions = new TableViewerColumn(viewer, SWT.NONE);
		columnPreConditions.getColumn().setWidth(600);
		columnPreConditions.getColumn().setText("Content");

		viewer.setContentProvider(new ContentProvider());
		viewer.setLabelProvider(new LabelProvider());

		GridLayoutFactory.fillDefaults().generateLayout(parent);
	}

	@Override
	public void setFocus() {
	}

}
