package de.tubs.skeditor.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;

import SkillGraph.Category;
import SkillGraph.Node;
import SkillGraph.Graph;
import de.tubs.skeditor.ImageProvider;
import de.tubs.skeditor.utils.ColorUtil;

public class ChangeCategoryFeature extends AbstractCustomFeature {
	private final Category CATEGORY;

	public ChangeCategoryFeature(IFeatureProvider fp, Category category) {
		super(fp);
		CATEGORY = category;
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}

	@Override
	public String getName() {
		return "Change Category";
	}

	@Override
	public String getDescription() {
		return "Change category of a node";
	}

	@Override
	public String getImageId() {
		return ImageProvider.getImage(CATEGORY.getName());
	}

	@Override
	public boolean isAvailable(IContext context) {
		return true;
	}

	@Override
	public void execute(ICustomContext context) {
		Node node = ((Node) context.getPictogramElements()[0].getLink().getBusinessObjects().get(0));
		RoundedRectangle rect = (RoundedRectangle) ((ContainerShape) context.getPictogramElements()[0].getLink().getPictogramElement()).getGraphicsAlgorithm();
		node.setCategory(CATEGORY);
		rect.setBackground(manageColor(ColorUtil.getCategoryColor(node.getCategory().getLiteral())));
	}

	public Category getCATEGORY() {
		return CATEGORY;
	}

}
