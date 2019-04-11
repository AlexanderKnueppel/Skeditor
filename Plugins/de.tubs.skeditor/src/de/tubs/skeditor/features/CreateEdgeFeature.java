package de.tubs.skeditor.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.impl.AbstractCreateConnectionFeature;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;

import SkillGraph.Category;
import SkillGraph.Edge;
import SkillGraph.Node;
import SkillGraph.SkillGraphFactory;
import de.tubs.skeditor.ImageProvider;
import de.tubs.skeditor.utils.ConstraintUtil;
import de.tubs.skeditor.utils.GraphUtil;

public class CreateEdgeFeature extends AbstractCreateConnectionFeature {

	public CreateEdgeFeature(IFeatureProvider fp) {
		super(fp, "Edge", "Create Edge");
	}

	/**
	 * Returns the EClass belonging to the anchor, or null if not available.
	 */
	private Node getNode(Anchor anchor) {
		if (anchor != null) {
			Object businessObject = getBusinessObjectForPictogramElement(anchor.getParent());
			if (businessObject instanceof Node) {
				return (Node) businessObject;
			}
		}
		return null;
	}

	@Override
	public boolean canCreate(ICreateConnectionContext context) {
		Node source = getNode(context.getSourceAnchor());
		Node target = getNode(context.getTargetAnchor());
		boolean validSourceTarget = source != null && target != null && source != target;
		return validSourceTarget && !wouldCauseLoop(source, target) && !hasSimilarEdge(source, target) && ConstraintUtil.canAddEdge(source, target);
	}

	private boolean hasSimilarEdge(Node parent, Node child) {
		for (Edge edge : parent.getChildEdges()) {
			if (edge.getParentNode().equals(parent) && edge.getChildNode().equals(child)) {
				System.out.println("HAS SIMILAR");
				return true;
			}
		}
		return false;
	}

	private boolean wouldCauseLoop(Node source, Node target) {
		for (Node node : GraphUtil.getChildNodes(target)) {
			if (source.equals(node)) {
				return true;
			}
			if (wouldCauseLoop(source, node)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canStartConnection(ICreateConnectionContext context) {
		Node source = getNode(context.getSourceAnchor());
		return source != null && (source.getCategory() != Category.ACTUATOR && source.getCategory() != Category.SENSOR);
	}

	/**
	 * Creates an Edge with an arrow on the target
	 * 
	 * @param source
	 *            is the "parent" of the target
	 * @param target
	 *            is the "child" of the source
	 * @return the created Edge
	 */
	private Edge createEdge(Node parent, Node child) {
		Edge edge = SkillGraphFactory.eINSTANCE.createEdge();
		edge.setParentNode(parent);
		edge.setChildNode(child);

		child.getParentNodes().add(parent);
		parent.getChildEdges().add(edge);

		return edge;
	}

	@Override
	public String getCreateImageId() {
		return ImageProvider.IMG_ARROW_HEAD;
	}

	@Override
	public Connection create(ICreateConnectionContext context) {
		Connection newConnection = null;

		Node parent = getNode(context.getSourceAnchor());
		Node child = getNode(context.getTargetAnchor());
		if (parent != null && child != null) {
			Edge edge = createEdge(parent, child);
			AddConnectionContext addContext = new AddConnectionContext(context.getSourceAnchor(), context.getTargetAnchor());
			addContext.setNewObject(edge);
			newConnection = (Connection) getFeatureProvider().addIfPossible(addContext);
		}

		return newConnection;
	}

}
