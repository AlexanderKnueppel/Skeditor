package de.tubs.skeditor.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddFeature;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.util.IColorConstant;

import SkillGraph.Edge;
import de.tubs.skeditor.utils.ColorUtil;

public class AddEdgeFeature extends AbstractAddFeature {

	private static final IColorConstant EDGE_COLOR = ColorUtil.getEdgeColor();

	public AddEdgeFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canAdd(IAddContext context) {
		if (context instanceof IAddConnectionContext && context.getNewObject() instanceof Edge) {
			return true;
		}
		return false;
	}

	@Override
	public PictogramElement add(IAddContext context) {

		IAddConnectionContext addConnectionContext = (IAddConnectionContext) context;
		Edge addedEdge = (Edge) context.getNewObject();
		IPeCreateService peCreateService = Graphiti.getPeCreateService();
		
		Connection connection = peCreateService.createFreeFormConnection(getDiagram());
		connection.setStart(addConnectionContext.getSourceAnchor());
		connection.setEnd(addConnectionContext.getTargetAnchor());

		IGaService gaService = Graphiti.getGaService();
		Polyline polyline = gaService.createPolyline(connection);
		polyline.setLineWidth(2);
		polyline.setForeground(manageColor(EDGE_COLOR));

		// create link and wire it
		link(connection, addedEdge);

		ConnectionDecorator connectionDecorator;
		connectionDecorator = peCreateService.createConnectionDecorator(connection, false, 1.0, true);
		createArrowHeadDecorator(connectionDecorator);
		return connection;
	}

	public Polyline createArrowHeadDecorator(GraphicsAlgorithmContainer container) {
		IGaService gaService = Graphiti.getGaService();
		Polyline arrowHead = gaService.createPolyline(container, new int[] { -15, 10, 0, 0, -15, -10 });
		arrowHead.setForeground(manageColor(EDGE_COLOR));
		arrowHead.setLineWidth(2);
		return arrowHead;
	}

}
