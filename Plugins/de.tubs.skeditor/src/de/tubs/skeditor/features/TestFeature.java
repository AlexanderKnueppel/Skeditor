package de.tubs.skeditor.features;

import java.util.ArrayList;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;

import SkillGraph.Graph;
import SkillGraph.Node;
import de.tubs.skeditor.HMA.testbench.MainTest;

public class TestFeature extends AbstractCustomFeature {
	ArrayList<Node> visited = new ArrayList<Node>();

	StringBuilder stringBuilder = new StringBuilder();

	public TestFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "TestBench";
	}

	@Override
	public String getDescription() {
		return "TestBench";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return true;
	}

	@Override
	public void execute(ICustomContext context) {
		Graph g = ((Graph) getBusinessObjectForPictogramElement(getDiagram()));
		MainTest.test(g);
	}
}
