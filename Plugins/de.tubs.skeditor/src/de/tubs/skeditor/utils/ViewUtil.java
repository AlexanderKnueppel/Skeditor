package de.tubs.skeditor.utils;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PlatformUI;

import SkillGraph.Graph;
import SkillGraph.Node;
import de.tubs.skeditor.views.DynamicModelView;
import de.tubs.skeditor.views.InfoView;
import de.tubs.skeditor.views.ParameterListView;
import de.tubs.skeditor.views.SafetyGoalsView;
import de.tubs.skeditor.views.VariableView;
import org.eclipse.core.runtime.jobs.Job;

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

	public static void updateInfoView(Node node) {
	    Runnable uiUpdater = new Runnable() {
	        public void run() {
	    		IViewReference[] ref = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getViewReferences();
	    		for (IViewReference iViewReference : ref) {
	    			if (iViewReference.getId().equals("de.tubs.skeditor.views.InfoView")) {
	    				TableViewer view = ((InfoView) iViewReference.getView(true)).getViewer();
	    				if (view.getInput() == null || !view.getInput().equals(node)) {
	    					view.setInput(node);
	    				} else {
	    					view.refresh();
	    				}

	    			}
	    		}
	        }
	    };
	    Job longRunningOperation = new Job("My command") {
	        protected IStatus run(IProgressMonitor monitor) {    
	            Display.getDefault().asyncExec(uiUpdater);
	            return Status.OK_STATUS;
	        }
	    };
	    longRunningOperation.schedule();

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
