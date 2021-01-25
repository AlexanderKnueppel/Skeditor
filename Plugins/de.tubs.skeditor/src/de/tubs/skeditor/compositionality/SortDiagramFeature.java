package de.tubs.skeditor.compositionality;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.draw2d.graph.CompoundDirectedGraph;
import org.eclipse.draw2d.graph.Edge;
import org.eclipse.draw2d.graph.EdgeList;
import org.eclipse.draw2d.graph.Node;
import org.eclipse.draw2d.graph.NodeList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.Shape;


public class SortDiagramFeature extends AbstractCustomFeature{

	public SortDiagramFeature(IFeatureProvider fp) {
		super(fp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ICustomContext context) {
		// TODO Auto-generated method stub
		
	}
	
	public static CompoundDirectedGraph mapSkillGraphToGraph(ContainerShape c) {
        Map<AnchorContainer, Node> shapeToNode = new HashMap<AnchorContainer, Node>();
        CompoundDirectedGraph dg = new CompoundDirectedGraph();
        EdgeList edgeList = new EdgeList();
        NodeList nodeList = new NodeList();
        for (Shape shape : c.getChildren()) {
            if (shape instanceof ContainerShape && (shape.getLink().getBusinessObjects().get(0) instanceof SkillGraph.Node)) {
                Node node = new Node();
                GraphicsAlgorithm ga = shape.getGraphicsAlgorithm();
                node.x = ga.getX();
                node.y = ga.getY();
                node.width = ga.getWidth();
                node.height = ga.getHeight();
                node.data = shape;
                shapeToNode.put(shape, node);
                nodeList.add(node);
            }
        }
        
        Diagram d = (Diagram)c;
        EList<Connection> connections = d.getConnections();
        
        for (Connection connection : connections.stream()
                .filter(con -> con.getStart().getParent().getLink().getBusinessObjects().get(0) 
                			instanceof SkillGraph.Node).collect(Collectors.toList())) {
            AnchorContainer source = connection.getStart().getParent();
            AnchorContainer target = connection.getEnd().getParent();

            Node s = null, t = null;
            if (shapeToNode.containsKey(source)) {
                s = shapeToNode.get(source);
            } else {
                s = shapeToNode.get(source.eContainer());
            }
            if (shapeToNode.containsKey(target)) {
                t = shapeToNode.get(target);
            } else {
                t = shapeToNode.get(target.eContainer());
            }

            Edge edge = new Edge(s, t);
            edge.data = connection;
            edgeList.add(edge);
        }
        dg.nodes = nodeList;
        dg.edges = edgeList;
        return dg;
    }
	
    public static void mapGraphCoordinatesToSkillGraph(CompoundDirectedGraph graph) {
        NodeList myNodes = new NodeList();
        myNodes.addAll(graph.nodes);
        myNodes.addAll(graph.subgraphs);
        for (Object object : myNodes) {
            Node node = (Node) object;
            Shape shape = (Shape) node.data;

            shape.getGraphicsAlgorithm().setX(node.x);
            shape.getGraphicsAlgorithm().setY(node.y);
            // shape.getGraphicsAlgorithm().setWidth(node.width);
            // shape.getGraphicsAlgorithm().setHeight(node.height);
            shape.getGraphicsAlgorithm().setWidth(shape.getGraphicsAlgorithm().getWidth());
            shape.getGraphicsAlgorithm().setHeight(shape.getGraphicsAlgorithm().getHeight());
        }
    }



}
