package de.tubs.skeditor.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.Shape;

import SkillGraph.Category;
import SkillGraph.Graph;
import SkillGraph.Node;
import SkillGraph.SkillGraphFactory;
import de.tubs.skeditor.ImageProvider;
import de.tubs.skeditor.diagram.FeatureProvider;

public class CreateNodeFeature extends AbstractCreateFeature {

	private final Category CATEGORY;

	public CreateNodeFeature(IFeatureProvider provider, Category category) {
		super(provider, capitalize(category.getName()), "Create " + capitalize(category.getName()) + " node");
		CATEGORY = category;
	}

	private static String capitalize(String string) {
		String newString = string.replace('_', ' ');
		newString = Character.toUpperCase(newString.charAt(0)) + newString.substring(1);
		return newString;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		boolean mainFound = false;
		for (Shape shape : context.getTargetContainer().getChildren()) {
			if (((Node) shape.getLink().getBusinessObjects().get(0)).getCategory().equals(Category.MAIN)) {
				mainFound = CATEGORY.equals(Category.MAIN);
				break;
			}
		}
		return (context.getTargetContainer() instanceof Diagram) && !mainFound;
	}

	@Override
	public String getCreateImageId() {
		return ImageProvider.getImage(CATEGORY.getName());
	}

	@Override
	public Object[] create(ICreateContext context) {
		FeatureProvider fp = (FeatureProvider) getFeatureProvider();
		Node newNode = SkillGraphFactory.eINSTANCE.createNode();

		Graph graph = (Graph) getBusinessObjectForPictogramElement(getDiagram());
		String name = "Node" + graph.getNodes().size();

		for (Node node : graph.getNodes()) {
			if (node.getName().equals(name)) {
				name = name + "_new";
			}
		}
		newNode.setName(name);
		newNode.setCategory(CATEGORY);

		fp.getDirectEditingInfo().setActive(true);

		if (CATEGORY == Category.MAIN) {
			graph.setRootNode(newNode);
		} else {
			graph.getNodes().add(newNode);
		}

		addGraphicalRepresentation(context, newNode);

		return new Object[] { newNode };
	}

}
