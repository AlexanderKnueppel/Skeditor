package de.tubs.skeditor.views;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import SkillGraph.Assumption;
import SkillGraph.Node;
import SkillGraph.Requirement;
import de.tubs.skeditor.views.safetygoalsview.CommentEditingSupport;
import de.tubs.skeditor.views.safetygoalsview.ContentProvider;
import de.tubs.skeditor.views.safetygoalsview.LabelProvider;
import de.tubs.skeditor.views.safetygoalsview.TermEditingSupport;
import de.tubs.skeditor.views.safetygoalsview.TypeEditingSupport;

public class SafetyGoalsView extends ViewPart {

	private TreeViewer viewer;

	public TreeViewer getViewer() {
		return viewer;
	}

	@Override
	public void createPartControl(Composite parent) {

		viewer = new TreeViewer(parent, SWT.FULL_SELECTION);
		viewer.getTree().setHeaderVisible(true);
		viewer.getTree().setLinesVisible(true);

		TreeViewerColumn columnSkill = new TreeViewerColumn(viewer, SWT.NONE);
		columnSkill.getColumn().setWidth(250);
		columnSkill.getColumn().setText("Skill");

		TreeViewerColumn columnTerm = new TreeViewerColumn(viewer, SWT.NONE);
		columnTerm.getColumn().setWidth(250);
		columnTerm.getColumn().setText("Term");
		columnTerm.setEditingSupport(new TermEditingSupport(viewer));

		TreeViewerColumn columnComment = new TreeViewerColumn(viewer, SWT.NONE);
		columnComment.getColumn().setWidth(600);
		columnComment.getColumn().setText("Comment");
		columnComment.setEditingSupport(new CommentEditingSupport(viewer));

		TreeViewerColumn columnType = new TreeViewerColumn(viewer, SWT.NONE);
		columnType.getColumn().setWidth(50);
		columnType.getColumn().setText("Type");
		columnType.setEditingSupport(new TypeEditingSupport(viewer));

		viewer.setContentProvider(new ContentProvider());
		viewer.setLabelProvider(new LabelProvider());

		MenuManager manager = new MenuManager();
		viewer.getControl().setMenu(manager.createContextMenu(viewer.getControl()));
		manager.add(new Action("Delete Element") {

			@Override
			public void run() {
				if (viewer.getStructuredSelection().getFirstElement() instanceof Requirement) {
					Requirement deletReq = (Requirement) viewer.getStructuredSelection().getFirstElement();
					Node node = deletReq.getNode();
					TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(node);
					domain.getCommandStack().execute(new RecordingCommand(domain) {
						@Override
						protected void doExecute() {
							node.getRequirements().remove(deletReq);
						}
					});
				} else if(viewer.getStructuredSelection().getFirstElement() instanceof Assumption) {
					Assumption deletReq = (Assumption) viewer.getStructuredSelection().getFirstElement();
					Node node = deletReq.getNode();
					TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(node);
					domain.getCommandStack().execute(new RecordingCommand(domain) {
						@Override
						protected void doExecute() {
							node.getRequirements().remove(deletReq);
						}
					});
				}
				viewer.refresh();
			}
		});

		GridLayoutFactory.fillDefaults().generateLayout(parent);
	}

	@Override
	public void setFocus() {

	}
}
