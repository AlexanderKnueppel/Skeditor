package de.tubs.skeditor.utils;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PlatformUI;

import SkillGraph.Graph;
import SkillGraph.Node;
import de.tubs.skeditor.views.DynamicModelView;
import de.tubs.skeditor.views.ParameterListView;
import de.tubs.skeditor.views.SafetyGoalsView;
import de.tubs.skeditor.views.VariableView;

public class ViewUtil {
	public static void updateSafetyGoalsView() {
		IViewReference[] ref = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getViewReferences();
		for (IViewReference iViewReference : ref) {
			if (iViewReference.getId().equals("de.tubs.skeditor.views.SafetyGoalsView")) {// TODO rework, bit too hacky
				((SafetyGoalsView) iViewReference.getView(true)).getViewer().refresh();
			}
		}
	}

	public static void updateDiffView(Node node) {
		IViewReference[] ref = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getViewReferences();
		for (IViewReference iViewReference : ref) {
			if (iViewReference.getId().equals("de.tubs.skeditor.views.DifferentialEquationView")) {
				TableViewer view = ((DynamicModelView) iViewReference.getView(true)).getViewer();
				if (view.getInput() == null || !view.getInput().equals(node)) {
					view.setInput(node);
				} else {
					view.refresh();
				}

			}
		}
	}

	public static void updateViews(Graph graph) {
		IViewReference[] ref = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getViewReferences();
		for (IViewReference iViewReference : ref) {
			if (iViewReference.getId().equals("de.tubs.skeditor.views.SafetyGoalsView")) {// TODO rework, bit too hacky
				TreeViewer view = ((SafetyGoalsView) iViewReference.getView(true)).getViewer();
				if (view.getInput() == null || !view.getInput().equals(graph)) {
					view.setInput(graph);
				}
				view.refresh();
			} else if (iViewReference.getId().equals("de.tubs.skeditor.views.ParameterListView")) {
				TableViewer view = ((ParameterListView) iViewReference.getView(true)).getViewer();
				if (view.getInput() == null || !view.getInput().equals(graph)) {
					view.setInput(graph);
				}
				view.refresh();
			} else if (iViewReference.getId().equals("de.tubs.skeditor.views.VariableView")) {
				TableViewer view = ((VariableView) iViewReference.getView(true)).getViewer();
				if (view.getInput() == null || !view.getInput().equals(graph)) {
					view.setInput(graph);
				}
				view.refresh();
			}
		}
	}
}
