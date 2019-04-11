package de.tubs.skeditor.features;

import org.eclipse.graphiti.features.IDirectEditingInfo;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddFeature;
import org.eclipse.graphiti.mm.algorithms.MultiText;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.util.IColorConstant;

import SkillGraph.Node;
import de.tubs.skeditor.utils.ColorUtil;

public class AddNodeFeature extends AbstractAddFeature {

	private static final IColorConstant NODE_TEXT_BORDER = ColorUtil.getEdgeColor();

	public AddNodeFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public PictogramElement add(IAddContext context) {
		Node addedNode = (Node) context.getNewObject();
		Diagram diagram = (Diagram) context.getTargetContainer();

		IPeCreateService createService = Graphiti.getPeCreateService();
		ContainerShape containerShape = createService.createContainerShape(diagram, true);

		int width = 120;
		int height = 50;

		IGaService gaService = Graphiti.getGaService();
		RoundedRectangle rectangle;

		rectangle = gaService.createRoundedRectangle(containerShape, 5, 5);
		rectangle.setForeground(manageColor(NODE_TEXT_BORDER));
		rectangle.setBackground(manageColor(ColorUtil.getCategoryColor(addedNode.getCategory().getLiteral())));
		rectangle.setLineWidth(2);

		gaService.setLocationAndSize(rectangle, context.getX(), context.getY(), width, height);

		if (addedNode.eResource() == null) {
			getDiagram().eResource().getContents().add(addedNode);
		}
		link(containerShape, addedNode);

		Shape textShape = createService.createShape(containerShape, false);
		MultiText text = gaService.createMultiText(textShape, addedNode.getName());

		text.setForeground(manageColor(NODE_TEXT_BORDER));
		text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		text.setFont(gaService.manageDefaultFont(getDiagram(), false, true));
		gaService.setLocation(text, 0, 0);
		link(textShape, addedNode);

		IDirectEditingInfo directEditingInfo = getFeatureProvider().getDirectEditingInfo();
		directEditingInfo.setMainPictogramElement(containerShape);
		directEditingInfo.setPictogramElement(textShape);
		directEditingInfo.setGraphicsAlgorithm(text);

		createService.createChopboxAnchor(containerShape);

		layoutPictogramElement(containerShape);

		return containerShape;
	}
	
	@Override
	public boolean canAdd(IAddContext context) {
		if (context.getNewObject() instanceof Node) {
			if (context.getTargetContainer() instanceof Diagram) {
				return true;
			}
		}
		return false;
	}

}
