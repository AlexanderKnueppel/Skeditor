package de.tubs.skeditor.views.variableview;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;

import SkillGraph.Graph;
import SkillGraph.Node;

public class ContentProvider implements IStructuredContentProvider{

	@Override
	public Object[] getElements(Object inputElement) {
		Graph g = (Graph) inputElement;
		List<Node> allNodes = new ArrayList<>();
		allNodes.add(g.getRootNode());
		for(Node n : g.getNodes()) {
			if(!allNodes.contains(n)) {
				allNodes.add(n);
			}
		}
		return allNodes.toArray();
	}

}
