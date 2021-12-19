package de.tubs.skeditor.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;

import SkillGraph.Assumption;
import SkillGraph.Node;
import SkillGraph.SkillGraphFactory;
import de.tubs.skeditor.ImageProvider;

public class AddAssumptionFeature extends AbstractCustomFeature {

	public AddAssumptionFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}

	@Override
	public String getName() {
		return "Manage Assumptions";
	}

	@Override
	public String getDescription() {
		return "Add, edit, or remove assumptions from a node";
	}

	@Override
	public String getImageId() {
		return ImageProvider.IMG_ASSUMPTION;
	}

	@Override
	public boolean isAvailable(IContext context) {
		return true;
	}

	@Override
	public void execute(ICustomContext context) {
		Node node = ((Node) context.getPictogramElements()[0].getLink().getBusinessObjects().get(0));
		Assumption as = SkillGraphFactory.eINSTANCE.createAssumption();
		as.setComment("New Assumption Comment");
		as.setTerm("\\true");
		as.setNode(node);
		node.getAssumptions().add(as);
	}
}
