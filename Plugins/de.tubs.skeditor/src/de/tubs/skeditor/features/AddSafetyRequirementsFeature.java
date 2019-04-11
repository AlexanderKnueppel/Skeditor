package de.tubs.skeditor.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;

import SkillGraph.Node;
import SkillGraph.Requirement;
import SkillGraph.SkillGraphFactory;
import SkillGraph.Type;
import de.tubs.skeditor.ImageProvider;

public class AddSafetyRequirementsFeature extends AbstractCustomFeature {

	public AddSafetyRequirementsFeature(IFeatureProvider fp) {
		super(fp);

	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}

	@Override
	public String getName() {
		return "Manage Safety Requirement";
	}

	@Override
	public String getDescription() {
		return "Add, edit or remove safety requirement from a node";
	}

	@Override
	public String getImageId() {
		return ImageProvider.IMG_SAFETY;
	}

	@Override
	public boolean isAvailable(IContext context) {
		return true;
	}

	@Override
	public void execute(ICustomContext context) {
		Node node = ((Node) context.getPictogramElements()[0].getLink().getBusinessObjects().get(0));
		Requirement req = SkillGraphFactory.eINSTANCE.createRequirement();
		req.setComment("New Requirement Comment");
		req.setTerm("a < b");
		req.setNode(node);
		req.setType(Type.FUNCTIONAL_SAFETY_REQUIREMENT);
		node.getRequirements().add(req);
	}

}
