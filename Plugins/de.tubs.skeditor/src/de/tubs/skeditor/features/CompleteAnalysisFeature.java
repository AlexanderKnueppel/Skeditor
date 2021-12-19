package de.tubs.skeditor.features;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import SkillGraph.Graph;
import SkillGraph.Node;
import de.tubs.skeditor.utils.ColorUtil;
import de.tubs.skeditor.utils.GraphUtil;

public class CompleteAnalysisFeature extends AbstractCustomFeature {
	ArrayList<Node> visited = new ArrayList<Node>();

	StringBuilder stringBuilder = new StringBuilder();

	public CompleteAnalysisFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Analyze skill graph";
	}

	@Override
	public String getDescription() {
		return "Performs a complete analysis of the skill graph";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}

	@Override
	public void execute(ICustomContext context) {
		Node root = ((Graph) getBusinessObjectForPictogramElement(getDiagram())).getRootNode();
		
	}
}
