/**
* The createFileOperationCompose Creates a new skill graph file 
* and composes two skill graph afterwards. 
* 
*
* @author  Arne Windeler
* @version 1.0
* @since   2020-01-14 
*/

package de.tubs.skeditor.compositionality;

import java.io.IOException;
import java.util.HashMap;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.graph.CompoundDirectedGraph;
import org.eclipse.draw2d.graph.CompoundDirectedGraphLayout;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.ILinkService;
import org.eclipse.graphiti.ui.services.GraphitiUi;

import SkillGraph.Category;
import SkillGraph.Edge;
import SkillGraph.Graph;
import SkillGraph.Node;
import SkillGraph.SkillGraphFactory;

public class CreateFileOperationCompose extends RecordingCommand {
	private TransactionalEditingDomain editingDomain;
	private String diagramName;
	// private URI path;
	private String containerName;
	private String GraphAName;
	private String GraphBName;
	private Node identicalNode;
	private int i = 0;
	private Node otherRootNode;

	private static final int PADDING = 30; // min. distance between components

	public CreateFileOperationCompose(TransactionalEditingDomain domain, String containerName, String name,
			String GraphA, String GraphB) {
		super(domain);
		this.editingDomain = domain;
		this.diagramName = name;
		// this.path = path;
		this.containerName = containerName;
		this.GraphAName = GraphA;
		this.GraphBName = GraphB;
	}

	@Override
	protected void doExecute() {

		String diagramTypeId = "SkillGraph";

		ResourceSet rSet = editingDomain.getResourceSet();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		if (!resource.exists() || !(resource instanceof IContainer)) {

		}
		IContainer container = (IContainer) resource;
		final IFile file = container.getFile(new Path(diagramName));

		URI uri = URI.createFileURI(file.getFullPath().toString());
		Resource resource2 = rSet.createResource(uri);

		Diagram diagram = Graphiti.getPeCreateService().createDiagram(diagramTypeId, this.diagramName, true);
		resource2.getContents().add(diagram);

		// Get the dtp
		IDiagramTypeProvider dtp = GraphitiUi.getExtensionManager().createDiagramTypeProvider(diagram,
				"de.tubs.skeditor.diagram.SkillGraphDiagramTypeProvider"); //$NON-NLS-1$
		IFeatureProvider featureProvider = dtp.getFeatureProvider();

		// The dtp already creates a new graph in its init method; so get it here
		Graph g = (Graph) featureProvider.getBusinessObjectForPictogramElement(diagram);

		Diagram dA = getDiagram(GraphAName, root, resource, rSet);
		Diagram dB = getDiagram(GraphBName, root, resource, rSet);

		// get graph and node A
		IDiagramTypeProvider dtpA = GraphitiUi.getExtensionManager().createDiagramTypeProvider(dA,
				"de.tubs.skeditor.diagram.SkillGraphDiagramTypeProvider"); //$NON-NLS-1$
		IFeatureProvider featureProviderA = dtpA.getFeatureProvider();
		Graph gA = (Graph) featureProviderA.getBusinessObjectForPictogramElement(dA);
		g.getParameterList().addAll(gA.getParameterList()); // add parameters from graph A
		Node nodeA = gA.getRootNode();

		// get graph and node B
		IDiagramTypeProvider dtpB = GraphitiUi.getExtensionManager().createDiagramTypeProvider(dB,
				"de.tubs.skeditor.diagram.SkillGraphDiagramTypeProvider"); //$NON-NLS-1$
		IFeatureProvider featureProviderB = dtpB.getFeatureProvider();
		Graph gB = (Graph) featureProviderB.getBusinessObjectForPictogramElement(dB);
		Node nodeB = gB.getRootNode();

		// checks if root node of one graph appears in the other graph.

		if (nodeInGraph(nodeA, gB) != null) {
			nodeA.setCategory(nodeInGraph(nodeA, gB).getCategory());
			g.setRootNode(gB.getRootNode());
			otherRootNode = nodeA;
		} else if (nodeInGraph(nodeB, gA) != null) {
			nodeB.setCategory(nodeInGraph(nodeB, gA).getCategory());
			g.setRootNode(gA.getRootNode());
			otherRootNode = nodeB;
		} else {
			// Create a new main node and the new edges
			int name = file.getName().lastIndexOf(".");
			Node node = SkillGraphFactory.eINSTANCE.createNode();
			node.setName(file.getName().substring(0, name));

			node.setCategory(Category.MAIN);
			g.setRootNode(node);

			nodeA.setCategory(Category.OBSERVABLE_EXTERNAL_BEHAVIOR);

			Edge edgeA = SkillGraphFactory.eINSTANCE.createEdge();
			edgeA.setChildNode(nodeA);
			edgeA.setParentNode(node);
			nodeA.getParentNodes().add(node);
			node.getChildEdges().add(edgeA);

			nodeB.setCategory(Category.OBSERVABLE_EXTERNAL_BEHAVIOR);

			Edge edgeB = SkillGraphFactory.eINSTANCE.createEdge();
			edgeB.setChildNode(nodeB);
			edgeB.setParentNode(node);
			nodeB.getParentNodes().add(node);
			node.getChildEdges().add(edgeB);
		}

		// add all parameters from graph B that are not in graph A
		for (int i = 0; i < gB.getParameterList().size(); i++) {
			boolean isIn = false;
			for (int x = 0; x < g.getParameterList().size(); x++) {
				if (g.getParameterList().get(x).getName().equals(gB.getParameterList().get(i).getName())) {
					isIn = true;
				}
			}
			if (isIn == false) {
				g.getParameterList().add(gB.getParameterList().get(i));
			}
		}

		// Add graphical representation of the newly created node

		AddContext[] addNodesArray = new AddContext[0];

		if (otherRootNode != null) {

			addNodesArray = graphComposition(g.getRootNode(), diagram, featureProviderA, addNodesArray);
			addNodesArray = graphComposition(otherRootNode, diagram, featureProviderB, addNodesArray);

		} else {
			AddContext addcontext = new AddContext();

			addcontext.setNewObject(g.getRootNode());
			addcontext.setTargetContainer(diagram);

			addcontext.setX(100);
			addcontext.setY(100);

			addNodesArray = new AddContext[1];
			addNodesArray[0] = addcontext;
			addNodesArray = graphComposition(nodeA, diagram, featureProviderA, addNodesArray);
			addNodesArray = graphComposition(nodeB, diagram, featureProviderB, addNodesArray);

		}

		IAddFeature add = featureProvider.getAddFeature(addNodesArray[0]);
		for (int i = 0; i < addNodesArray.length; i++) {
			if (add.canAdd(addNodesArray[i])) {
				add.add(addNodesArray[i]);
			}
		}

		// add Edges
		AddConnectionContext[] addEdgesArray = new AddConnectionContext[0];
		for (Edge e : g.getRootNode().getChildEdges()) {
			addEdgesArray = addEdges(e, diagram, featureProvider, addEdgesArray);
		}

		IAddFeature addE = featureProvider.getAddFeature(addEdgesArray[0]);

		for (int i = 0; i < addEdgesArray.length; i++) {

			if (addE.canAdd(addEdgesArray[i])) {
				addE.add(addEdgesArray[i]);
			}
		}

		// Save resource to disk
		try {
			resource2.save(new HashMap());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Sorting the graph
		CompoundDirectedGraph cGraph = SortDiagramFeature.mapSkillGraphToGraph(diagram);
		cGraph.setDefaultPadding(new Insets(PADDING));
		cGraph.setDirection(PositionConstants.EAST);
		new CompoundDirectedGraphLayout().visit(cGraph);
		SortDiagramFeature.mapGraphCoordinatesToSkillGraph(cGraph);

		// Save resource to disk
		try {
			resource2.save(new HashMap());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public static Diagram getDiagramFromFile(URI uri, ResourceSet resourceSet) {
		// Get the URI of the model file.
		final URI resourceURI = uri;
		// Demand load the resource for this file.
		Resource resource;
		try {
			resource = resourceSet.getResource(resourceURI, true);
			if (resource != null) {
				// does resource contain a diagram as root object?
				final EObject root = resource.getContents().get(0);
				if (root instanceof Diagram) {
					return (Diagram) root;
				}
			}
		} catch (final WrappedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Diagram getDiagram(String g, IWorkspaceRoot ro, IResource re, ResourceSet resourceSet) {
		IWorkspaceRoot root = ro;
		IResource resource = re;
		ResourceSet rSet = resourceSet;
		String graph = g;

		String[] arrA = graph.split("/");
		String containerNameA = "";
		for (int i = 1; i < arrA.length - 1; i++) {
			containerNameA += "/" + arrA[i];
		}
		String diagramNameA = arrA[arrA.length - 1];

		IResource resourceA = root.findMember(new Path(containerNameA));
		if (!resource.exists() || !(resource instanceof IContainer)) {

		}
		IContainer containerA = (IContainer) resourceA;
		final IFile fileA = containerA.getFile(new Path(diagramNameA));

		URI uriA = URI.createFileURI(fileA.getFullPath().toString());

		Diagram diagramReturn = getDiagramFromFile(uriA, rSet);
		return diagramReturn;
	}

	public AddContext[] graphComposition(Node n, Diagram diagram, IFeatureProvider featureProvider, AddContext[] aca) {

		Node node = n;

		AddContext context = new AddContext();

		context.setNewObject(node);

		context.setTargetContainer(diagram);

		AddContext[] addContextArray = aca;
		IDiagramTypeProvider dtp = GraphitiUi.getExtensionManager().createDiagramTypeProvider(diagram,
				"de.tubs.skeditor.diagram.SkillGraphDiagramTypeProvider"); //$NON-NLS-1$
		IFeatureProvider fP = dtp.getFeatureProvider();
		Graph graph = (Graph) fP.getBusinessObjectForPictogramElement(diagram);

		if (isAdded(aca, node)) {
			// Node with indentical name is already added

			if (!identicalNode.getController().isEmpty() && !node.getController().isEmpty()
					&& SkillComposition.composableControllers(node, identicalNode)
					&& SkillComposition.composableRequirements(node, identicalNode)) {
				// composable Controllers
				SkillComposition.composingSkills(identicalNode, node);

			} else if (!identicalNode.getController().isEmpty() && !node.getController().isEmpty()
					&& identicalNode.getController().get(0).getCtrl().equals(node.getController().get(0).getCtrl())
					&& SkillComposition.composableRequirements(node, identicalNode)) {
				// identical Controllers
				SkillComposition.composeSkillRelations(identicalNode, node);

			} else if (identicalNode.getController().isEmpty()
					|| node.getController().isEmpty() && SkillComposition.composableRequirements(node, identicalNode)) {
				// min one empty controller
				SkillComposition.composeSkillRelations(identicalNode, node);

				if (identicalNode.getController().isEmpty()) {
					identicalNode.getController().addAll(node.getController());
				}

			} else {
				// no composable controllers
				node.setName(node.getName() + "_2");
				if (!graph.getRootNode().equals(node)) {
					graph.getNodes().add(node);
				}

				addContextArray = new AddContext[aca.length + 1];
				for (int index = 0; index < aca.length; index++) {
					addContextArray[index] = aca[index];
				}
				addContextArray[addContextArray.length - 1] = context;

			}

		} else if (!isAdded(aca, node)) {
			// no identical node is added
			if (!graph.getRootNode().equals(node)) {
				graph.getNodes().add(node);
			}

			addContextArray = new AddContext[aca.length + 1];
			for (int index = 0; index < aca.length; index++) {
				addContextArray[index] = aca[index];
			}
			addContextArray[addContextArray.length - 1] = context;

		}

		if (!node.getChildEdges().isEmpty()) {
			for (int i = 0; i < node.getChildEdges().size(); i++) {
				AddContext[] a = graphComposition(node.getChildEdges().get(i).getChildNode(), diagram, featureProvider,
						addContextArray);

				addContextArray = a;
			}
		}

		return addContextArray;
	}

	public boolean isAdded(AddContext[] ac, Node n) {

		for (int index = 0; index < ac.length; index++) {

			if (((Node) ac[index].getNewObject()).getName().equals(n.getName())) {
				identicalNode = (Node) ac[index].getNewObject();
				return true;
			}
		}

		return false;
	}

	public AddConnectionContext[] addEdges(Edge e, Diagram diagram, IFeatureProvider featureProvider,
			AddConnectionContext[] aec) {

		AddConnectionContext[] addConnectionContextArray = aec;
		// Get linkservice to ask for relations between DOs and PEs

		ILinkService linkserv = Graphiti.getLinkService();

		if (linkserv.getPictogramElements(diagram, e).isEmpty()) {
			// Call add feature to add the edge

			// We need the source anchor (from the first connected node)
			Anchor sourceAnchor = null;

			for (PictogramElement aPe : linkserv.getPictogramElements(diagram, e.getParentNode())) {
				if (aPe instanceof ContainerShape) {
					sourceAnchor = ((ContainerShape) aPe).getAnchors().get(0);
				}
			}

			// And the target anchor (from the second connected node)
			Anchor targetAnchor = null;
			for (PictogramElement aPe : linkserv.getPictogramElements(diagram, e.getChildNode())) {
				if (aPe instanceof ContainerShape) {
					targetAnchor = ((ContainerShape) aPe).getAnchors().get(0);
				}
			}
			// Now we can create the add connection context with both anchors
			AddConnectionContext acc = new AddConnectionContext(sourceAnchor, targetAnchor);

			// ... and the new edge
			acc.setNewObject(e);

			// ... to be created into the diagram
			acc.setTargetContainer(diagram);

			addConnectionContextArray = new AddConnectionContext[aec.length + 1];
			for (int index = 0; index < aec.length; index++) {
				addConnectionContextArray[index] = aec[index];
			}
			addConnectionContextArray[addConnectionContextArray.length - 1] = acc;

			if (!e.getChildNode().getChildEdges().isEmpty()) {
				for (int i = 0; i < e.getChildNode().getChildEdges().size(); i++) {
					AddConnectionContext[] a = addEdges(e.getChildNode().getChildEdges().get(i), diagram,
							featureProvider, addConnectionContextArray);

					addConnectionContextArray = a;
				}
			}

		}
		return addConnectionContextArray;
	}

	public static Node nodeInGraph(Node node, Graph g) {
		for (Node n : g.getNodes()) {
			if (n.getName().equals(node.getName())) {
				if (composableSkills(n, node)) {
					return n;
				} else {
					n.setName(node.getName() + "2");
					return null;
				}

			}
		}
		if (node.getName().equals(g.getRootNode().getName())) {
			if (composableSkills(g.getRootNode(), node)) {
				return g.getRootNode();
			} else {
				g.getRootNode().setName(node.getName() + "2");
				return null;
			}

		}
		return null;
	}

	public static boolean composableSkills(Node a, Node b) {

		if (!a.getController().isEmpty() && !b.getController().isEmpty()
				&& SkillComposition.composableControllers(a, b)) {

			return true;

		} else if (a.getController().isEmpty() || b.getController().isEmpty()) {
			return true;
		}

		return false;
	}

}
