package de.tubs.skeditor.features;

import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.features.IDirectEditingInfo;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.features.impl.AbstractDirectEditingFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.MultiText;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

import SkillGraph.Node;

public class DirectEditingNodeFeature extends AbstractDirectEditingFeature {

	public DirectEditingNodeFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public int getEditingType() {
		return TYPE_MULTILINETEXT;
	}

	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		PictogramElement pictogramElement = context.getPictogramElement();
		Object businessObject = getBusinessObjectForPictogramElement(pictogramElement);
		GraphicsAlgorithm graphicsAlgorithm = context.getGraphicsAlgorithm();
		if (businessObject instanceof Node && graphicsAlgorithm instanceof MultiText) {
			return true;
		}

		return false;
	}

	@Override
	public String getInitialValue(IDirectEditingContext context) {
		PictogramElement pe = context.getPictogramElement();
		Node node = (Node) getBusinessObjectForPictogramElement(pe);
		return node.getName();
	}

	@Override
	public String checkValueValid(String value, IDirectEditingContext context) {
		return null;
	}

	public void setValue(String value, IDirectEditingContext context) {
		PictogramElement pictogramElement = context.getPictogramElement();
		Node node = (Node) getBusinessObjectForPictogramElement(pictogramElement);
		node.setName(value);
		updatePictogramElement(((Shape) pictogramElement).getContainer());
	}

	@Override
	public void execute(IContext context) { // TODO make prettier, is not really a good solution, too improvised
		getFeatureProvider().getDirectEditingInfo().setActive(true);
		IPictogramElementContext PictoEditContext = (IPictogramElementContext) context;
		if (PictoEditContext.getPictogramElement() instanceof ContainerShape) {
			ContainerShape containerShape = (ContainerShape) PictoEditContext.getPictogramElement();
			EList<Shape> containerChildren = containerShape.getChildren();
			if (containerChildren.size() > 0 && containerChildren.get(0).getGraphicsAlgorithm() instanceof MultiText) {
				IDirectEditingInfo directEditingInfo = getFeatureProvider().getDirectEditingInfo();
				directEditingInfo.setMainPictogramElement(containerShape);
				directEditingInfo.setPictogramElement(containerChildren.get(0));
				directEditingInfo.setGraphicsAlgorithm(containerChildren.get(0).getGraphicsAlgorithm());
				getDiagramBehavior().refresh();
			}
		}

		super.execute(context);
	}

	@Override
	public boolean canExecute(IContext context) {
		return true;
	}

	@Override
	public boolean stretchFieldToFitText() {
		return true;
	}
	
}
