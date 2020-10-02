package de.tubs.skeditor.synthesis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
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
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.services.GraphitiUi;

import SkillGraph.Category;
import SkillGraph.Graph;
import SkillGraph.Node;
import SkillGraph.SkillGraphFactory;
import de.tubs.skeditor.synthesis.search.FilterFormatException;
import de.tubs.skeditor.synthesis.search.SkillSearch;
import de.tubs.skeditor.utils.SynthesisUtil;

public class SynthesisOperation extends RecordingCommand{
	private TransactionalEditingDomain editingDomain;
	private String diagramName;
	private String containerName;
	private String repoName;
	private List<Requirement> requirements;

	public SynthesisOperation(TransactionalEditingDomain domain, String containerName, String name, String repo, List<Requirement> requirements) {
		super(domain);
		this.editingDomain = domain;
		this.diagramName = name;
		this.containerName = containerName;
		this.repoName = repo;
		this.requirements = requirements;
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
		if(members != null) {
			for(IResource res : members) {
				if(res instanceof IFile) {
					IFile graphFile = (IFile) res;
					if(graphFile.getFileExtension().equals("sked")) {
						System.out.println("Datei: "+graphFile.getName());
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
		System.out.println("Anzahl der Graphen: "+graphs.size());
		//init repository for SkillSearch
		SkillSearch.getInstance().initializeRepository(graphs);
		
		/*try {
			for(Node node : SkillSearch.getInstance().searchSkills("defined=\"a\"|!(category=\"observable_external_behavior\")")){
				
				System.out.println(node);
			}
		} catch (FilterFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		String[] forbidden = {};
		String[] required = {"ep"};
		SkillProvider provider = new RequirementSkillProvider(new Requirement("ep>4"));
		Node myNode = provider.getNext();
		int i = 0;
		while(myNode != null) {
			System.out.println(SynthesisUtil.childsToString(myNode));
			myNode = provider.getNext();
			i++;
		}
		//create new diagram for skillgraph synthesis
		/*String diagramTypeId = "SkillGraph";
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
		Synthesis syn = new Synthesis(g);
		syn.synthesizeGraph(requirements);*/
		
		
		/*Diagram dA = getDiagram(GraphAName, root, resource, rSet);
		Diagram dB = getDiagram(GraphBName, root, resource, rSet);
		// Create a new main node
		int name = file.getName().lastIndexOf(".");
		Node node = SkillGraphFactory.eINSTANCE.createNode();
		node.setName(file.getName().substring(0, name));
		node.setCategory(Category.MAIN);

		g.setRootNode(node);

		// Add graphical representation of the newly created node
		AddContext addcontext = new AddContext();
		addcontext.setNewObject(node);
		addcontext.setTargetContainer(diagram);

		addcontext.setX(100);
		addcontext.setY(100);

		IAddFeature add = featureProvider.getAddFeature(addcontext);

		if (add.canAdd(addcontext)) {
			add.add(addcontext);
		}*/

		// Save resource to disk
		/*try {
			resource2.save(new HashMap());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/

	}
	
	private void printNodes(Set<Node> nodes) {
		for(Node node : nodes) {
			System.out.print(node.getName()+", ");
		}
		System.out.println(".");
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
