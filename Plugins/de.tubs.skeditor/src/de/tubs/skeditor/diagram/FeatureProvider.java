package de.tubs.skeditor.diagram;

import java.util.ArrayList;

import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IReconnectionFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.IReconnectionContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.DefaultFeatureProvider;

import SkillGraph.Category;
import SkillGraph.Edge;
import SkillGraph.Node;
import de.tubs.skeditor.features.AddEdgeFeature;
import de.tubs.skeditor.features.AddNodeFeature;
import de.tubs.skeditor.features.AddSafetyRequirementsFeature;
import de.tubs.skeditor.features.ChangeCategoryFeature;
import de.tubs.skeditor.features.CreateEdgeFeature;
import de.tubs.skeditor.features.CreateNodeFeature;
import de.tubs.skeditor.features.DeleteFeature;
import de.tubs.skeditor.features.DirectEditingNodeFeature;
import de.tubs.skeditor.features.ExportFeature;
import de.tubs.skeditor.features.LayoutNodeFeature;
import de.tubs.skeditor.features.ReconnectionFeature;
import de.tubs.skeditor.features.RunKeymaeraCheckFeature;
import de.tubs.skeditor.features.UpdateFeature;

public class FeatureProvider extends DefaultFeatureProvider {

	public FeatureProvider(IDiagramTypeProvider dtp) {
		super(dtp);
	}

	@Override
	public ICreateFeature[] getCreateFeatures() {
		ArrayList<ICreateFeature> createFeatures = new ArrayList<ICreateFeature>();
		for (Category category : Category.values()) {
			createFeatures.add(new CreateNodeFeature(this, category));
		}
		return createFeatures.toArray(new ICreateFeature[createFeatures.size()]);
	}

	@Override
	public ICreateConnectionFeature[] getCreateConnectionFeatures() {
		return new ICreateConnectionFeature[] { new CreateEdgeFeature(this) };
	}

	@Override
	public IReconnectionFeature getReconnectionFeature(IReconnectionContext context) {
		// TODO Auto-generated method stub
		return new ReconnectionFeature(this);
	}

	@Override
	public IAddFeature getAddFeature(IAddContext context) {
		if (context.getNewObject() instanceof Node) {
			return new AddNodeFeature(this);
		} else if (context.getNewObject() instanceof Edge) {
			return new AddEdgeFeature(this);
		}
		return super.getAddFeature(context);
	}

	@Override
	public IDirectEditingFeature getDirectEditingFeature(IDirectEditingContext context) {
		PictogramElement pictogramElement = context.getPictogramElement();
		Object businessObject = getBusinessObjectForPictogramElement(pictogramElement);
		if (businessObject instanceof Node) {
			return new DirectEditingNodeFeature(this);
		}
		return super.getDirectEditingFeature(context);
	}

	@Override
	public IUpdateFeature getUpdateFeature(IUpdateContext context) {
		PictogramElement pictogramElement = context.getPictogramElement();
		if (pictogramElement instanceof ContainerShape) {
			Object bo = getBusinessObjectForPictogramElement(pictogramElement);
			if (bo instanceof Node) {
				return new UpdateFeature(this);
			}
		}
		return super.getUpdateFeature(context);
	}

	@Override
	public ILayoutFeature getLayoutFeature(ILayoutContext context) {
		PictogramElement pictogramElement = context.getPictogramElement();
		Object bo = getBusinessObjectForPictogramElement(pictogramElement);
		if (bo instanceof Node) {
			return new LayoutNodeFeature(this);
		}
		return super.getLayoutFeature(context);
	}

	@Override
	public ICustomFeature[] getCustomFeatures(ICustomContext context) {
		ArrayList<ICustomFeature> customFeatures = new ArrayList<ICustomFeature>();
		for (Category category : Category.values()) {
			customFeatures.add(new ChangeCategoryFeature(this, category));
		}
		customFeatures.add(new ExportFeature(this));
		customFeatures.add(new AddSafetyRequirementsFeature(this));
		customFeatures.add(new RunKeymaeraCheckFeature(this));
		return customFeatures.toArray(new ICustomFeature[customFeatures.size()]);
	}

	@Override
	public IDeleteFeature getDeleteFeature(IDeleteContext context) {
		return new DeleteFeature(this);
	}

}
