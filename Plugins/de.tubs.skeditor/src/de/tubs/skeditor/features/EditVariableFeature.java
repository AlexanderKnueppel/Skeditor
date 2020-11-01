package de.tubs.skeditor.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import SkillGraph.Node;
import de.tubs.skeditor.synthesis.dialog.VariableContentProvider;
import de.tubs.skeditor.synthesis.dialog.EditVariableListDialog;
import de.tubs.skeditor.synthesis.dialog.VariableRow;


public class EditVariableFeature extends AbstractCustomFeature{
	public EditVariableFeature(IFeatureProvider fp) {
		super(fp);
		// TODO Auto-generated constructor stub
	}

	/**
	 * boolean that indicate if something changes
	 */
	private boolean hasDoneChanges = false;

	@Override
	public String getName() {
		return "Edit Variables";
	}

	@Override
	public String getDescription() {
		return "Edit required and defined variables of a Node";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof Node) {
				ret = true;
			}
		}
		return ret;
	}

	@Override
	public void execute(ICustomContext context) {
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof Node) {
				Node node = (Node) bo;
				EditVariableListDialog ldialog = new EditVariableListDialog(getShell(), node);
				ldialog.setTitle("Edit required and defined variables");
				ldialog.setContentProvider(new VariableContentProvider());
				ldialog.setLabelProvider(new ColumnLabelProvider() {
					@Override
					public String getText(Object element) {
						VariableRow row = (VariableRow) element;
						return row.toString();
					}
					
					@Override
		            public Image getImage(Object element) {
		               return null;
		            }
				});
				ldialog.setInput(node);
				ldialog.open();
				this.hasDoneChanges = ldialog.hasChanged();
				updatePictogramElement(((Shape) pes[0]).getContainer());
				getFeatureProvider().getDiagramTypeProvider().getDiagramBehavior().refresh();
			}

		}
	}

	@Override
	public boolean hasDoneChanges() {
		return this.hasDoneChanges;
	}

	/**
	 * Returns the currently active Shell.
	 * 
	 * @return The currently active Shell.
	 */
	private static Shell getShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}

}
