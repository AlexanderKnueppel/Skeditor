package de.tubs.skeditor.synthesis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
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
import org.eclipse.emf.ecore.util.EcoreUtil;
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

import SkillGraph.Edge;
import SkillGraph.Graph;
import SkillGraph.Node;
import SkillGraph.Parameter;

import de.tubs.skeditor.compositionality.SortDiagramFeature;
import de.tubs.skeditor.synthesis.search.SkillSearch;

public class SynthesisOperation extends RecordingCommand{
	private static final int PADDING = 30; // min. distance between components
	private TransactionalEditingDomain editingDomain;
	private String diagramName;
	private String containerName;
	private String repoName;
	private List<Requirement> requirements;
	private List<Requirement> unsatisfiableRequirements;

	public SynthesisOperation(TransactionalEditingDomain domain, String containerName, String name, String repo, List<Requirement> requirements) {
		super(domain);
		this.editingDomain = domain;
		this.diagramName = name;
		this.containerName = containerName;
		this.repoName = repo;
		this.requirements = requirements;
		this.unsatisfiableRequirements = new ArrayList<>();
	}

	public List<Requirement> getUnsatisfiableRequirements(){
		return unsatisfiableRequirements;
	}
	
	@Override
	protected void doExecute() {
		
		ResourceSet rSet = editingDomain.getResourceSet();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		if (!resource.exists() || !(resource instanceof IContainer)) {
			return;
		}
		//get graphs of folder
		Set<Graph> graphs = new HashSet<Graph>();
		IResource repoResource = root.findMember(new Path(repoName));
		if(! (repoResource instanceof IContainer)) {
			return;
		} 
		IContainer repoContainer = (IContainer) repoResource;
		
		IResource[] members = null;
		try {
			members = repoContainer.members();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//get graphs of repo
		if(members != null) {
			for(IResource res : members) {
				if(res instanceof IFile) {
					IFile graphFile = (IFile) res;
					if(graphFile.getFileExtension().equals("sked")) {
						Diagram diag = getDiagram(graphFile.getFullPath().toString(), root, resource, rSet);
						IDiagramTypeProvider tempDTP = GraphitiUi.getExtensionManager().createDiagramTypeProvider(diag,
								"de.tubs.skeditor.diagram.SkillGraphDiagramTypeProvider"); //$NON-NLS-1$
						IFeatureProvider tempFP = tempDTP.getFeatureProvider();
						Graph graph = (Graph) tempFP.getBusinessObjectForPictogramElement(diag);
						graphs.add(graph);
					}
					
				}
			}
		}
		
		//init repository for SkillSearch
		SkillSearch.getInstance().initializeRepository(graphs);
		
		//create new diagram for skillgraph synthesis
		String diagramTypeId = "SkillGraph";
		Diagram diagram = Graphiti.getPeCreateService().createDiagram(diagramTypeId, this.diagramName, true);
		
		IContainer container = (IContainer) resource;
		final IFile file = container.getFile(new Path(diagramName));

		URI uri = URI.createFileURI(file.getFullPath().toString());
		Resource resource2 = rSet.createResource(uri);
		resource2.getContents().add(diagram);
		
		// Get the dtp
		IDiagramTypeProvider dtp = GraphitiUi.getExtensionManager().createDiagramTypeProvider(diagram,
				"de.tubs.skeditor.diagram.SkillGraphDiagramTypeProvider"); //$NON-NLS-1$
		IFeatureProvider featureProvider = dtp.getFeatureProvider();

		// The dtp already creates a new graph in its init method; so get it here
		Graph g = (Graph) featureProvider.getBusinessObjectForPictogramElement(diagram);
		int name = file.getName().lastIndexOf(".");
		
		
		//synthesize new graph based on requirements
		Synthesis syn = new Synthesis();
		Node rootNode = syn.synthesizeGraph(requirements);
		unsatisfiableRequirements = syn.getUnsatisfiableRequirements();
		System.out.println("unsatisfiable in opera: "+unsatisfiableRequirements );
		rootNode.setName(file.getName().substring(0, name));
		g.setRootNode(rootNode);
		List<AddContext> addContextNodes = new ArrayList<>();
		List<AddConnectionContext> addContextEdges = new ArrayList<>();
		AddContext ctx = new AddContext();
		ctx.setNewObject(g.getRootNode());
		ctx.setTargetContainer(diagram);

		ctx.setX(100);
		ctx.setY(100);
		addContextNodes.add(ctx);
		for(Edge e : rootNode.getChildEdges()) {
			//addEdges(e, diagram, addContextEdges);
			addNodes(e.getChildNode(), diagram, g, addContextNodes);
		}
		IAddFeature addNodeFeature = featureProvider.getAddFeature(addContextNodes.get(0));
		for (AddContext addCtx : addContextNodes) {
			if (addNodeFeature.canAdd(addCtx)) {
				addNodeFeature.add(addCtx);
			}
		}
		for(Edge e : rootNode.getChildEdges()) {
			addEdges(e, diagram, addContextEdges);
			//addNodes(e.getChildNode(), diagram, g, addContextNodes);
		}
		IAddFeature addEdgeFeature = null;
		if(addContextEdges.size() > 0) {
			addEdgeFeature = featureProvider.getAddFeature(addContextEdges.get(0));
		}
	
		if(addEdgeFeature != null) {
			for (AddConnectionContext addCtx : addContextEdges) {
				if (addEdgeFeature.canAdd(addCtx)) {
					addEdgeFeature.add(addCtx);
				}
			}
		}
		
		//insert all parameters of all graphs from repo to new graph
		for(Graph gr : graphs) {
			for(int i = 0; i < gr.getParameterList().size(); i++) {
				boolean contained = false;
				Parameter param = gr.getParameterList().get(i);
				for(Parameter p : g.getParameterList()) {
					if(p.getAbbreviation().equals(param.getAbbreviation())) {
						contained = true;
					}
				}
				if(!contained) {
					g.getParameterList().add(EcoreUtil.copy(param));
				}
			}
		}
		/*try {
			resource2.save(new HashMap());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		CompoundDirectedGraph cGraph = SortDiagramFeature.mapSkillGraphToGraph(diagram);
		cGraph.setDefaultPadding(new Insets(PADDING));
		cGraph.setDirection(PositionConstants.EAST);
		new CompoundDirectedGraphLayout().visit(cGraph);
		SortDiagramFeature.mapGraphCoordinatesToSkillGraph(cGraph);
		
		try {
			resource2.save(new HashMap());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void addEdges(Edge e, Diagram diagram, List<AddConnectionContext> ectx) {

		// Get linkservice to ask for relations between DOs and PEs

		ILinkService linkserv = Graphiti.getLinkService();

		if (linkserv.getPictogramElements(diagram, e).isEmpty()) {

			// We need the source anchor (from the first connected node)
			Anchor sourceAnchor = null;
			for (PictogramElement aPe : linkserv.getPictogramElements(diagram, e.getParentNode())) {
				System.out.println("pictogram source:"+aPe);
				if (aPe instanceof ContainerShape) {
					sourceAnchor = ((ContainerShape) aPe).getAnchors().get(0);
				}
			}

			// And the target anchor (from the second connected node)
			Anchor targetAnchor = null;
			System.out.println("Child node:"+e.getChildNode());
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

			ectx.add(acc);

			if (!e.getChildNode().getChildEdges().isEmpty()) {
				for (Edge edge : e.getChildNode().getChildEdges()) {
					addEdges(edge, diagram, ectx);
				}
			}

		}
	}
	
	private void addNodes(Node node, Diagram diagram, Graph graph, List<AddContext> nctx) {

			AddContext ctx = new AddContext();
			ctx.setNewObject(node);
			ctx.setTargetContainer(diagram);
			boolean exists = false;
			for ( Node n : graph.getNodes()) {
				if(n.getName().equals(node.getName())) {
					exists = true;
				}
			}
			if(!exists) {
				graph.getNodes().add(node);
				nctx.add(ctx);
			}
			
			

			if (!node.getChildEdges().isEmpty()) {
				for (Edge e : node.getChildEdges()) {
					addNodes(e.getChildNode(), diagram, graph, nctx);
				}
			}

		}
	
	private Diagram getDiagramFromFile(URI uri, ResourceSet resourceSet) {
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

	private Diagram getDiagram(String g, IWorkspaceRoot ro, IResource re, ResourceSet resourceSet) {
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
}
