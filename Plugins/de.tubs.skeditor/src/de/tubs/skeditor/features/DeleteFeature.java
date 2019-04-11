package de.tubs.skeditor.features;

import java.util.ArrayList;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.DefaultDeleteFeature;

import SkillGraph.Category;
import SkillGraph.Edge;
import SkillGraph.Graph;
import SkillGraph.Node;
import de.tubs.skeditor.utils.GraphUtil;

public class DeleteFeature extends DefaultDeleteFeature {
	ArrayList<Node> nodesToDeleteFrom = new ArrayList<Node>();
	ArrayList<Edge> deletedEdges = new ArrayList<Edge>();
	ArrayList<Node> deletedNodes = new ArrayList<Node>();

	public DeleteFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public void preDelete(IDeleteContext context) {
		PictogramElement pe = context.getPictogramElement();
		Object[] businessObjectsForPictogramElement = getAllBusinessObjectsForPictogramElement(pe);
		for (Object object : businessObjectsForPictogramElement) {
			if (object instanceof Edge) {
				Edge edge = (Edge) object;
				deletedEdges.add(edge);
				nodesToDeleteFrom.add(edge.getParentNode());
				nodesToDeleteFrom.add(edge.getChildNode());
			}
			if (object instanceof Node) {
				Node node = (Node) object;
				deletedNodes.add(node);
				if (node.getCategory() == Category.MAIN) {
					((Graph) getBusinessObjectForPictogramElement(getDiagram())).setRootNode(null);
				}
				nodesToDeleteFrom.addAll(node.getParentNodes());
				nodesToDeleteFrom.addAll(GraphUtil.getChildNodes(node));
				deletedEdges.addAll(GraphUtil.getAllEdges(node));
			}
		}
		super.preDelete(context);
	}

	@Override
	public void postDelete(IDeleteContext context) {
		for (Node nodeToDeleteFrom : nodesToDeleteFrom) {
			if (nodeToDeleteFrom != null) {
				nodeToDeleteFrom.getChildEdges().removeAll(deletedEdges);
				nodeToDeleteFrom.getParentNodes().removeAll(deletedNodes);
			}
		}
		((Graph) getBusinessObjectForPictogramElement(getDiagram())).getNodes().removeAll(deletedNodes);
		super.postDelete(context);
	}
}
