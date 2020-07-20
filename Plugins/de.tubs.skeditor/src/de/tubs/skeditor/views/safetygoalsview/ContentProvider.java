package de.tubs.skeditor.views.safetygoalsview;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ITreeContentProvider;

import SkillGraph.Graph;
import SkillGraph.Node;
import SkillGraph.Requirement;

public class ContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getChildren(Object object) {
		Node node = (Node) object;
		return node.getRequirements().toArray();
	}

	@Override
	public Object[] getElements(Object object) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		Node root = ((Graph) object).getRootNode();
		if (root.getRequirements().size() > 0) {
			nodes.add(root);
		}
		for (Node node : ((Graph) object).getNodes()) {
			if (node.getRequirements().size() > 0) {
				nodes.add(node);
			}
		}
		return nodes.toArray();
	}

	@Override
	public Object getParent(Object object) {
		if (object instanceof Requirement) {
			Requirement req = (Requirement) object;
			return req.getNode();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object object) {
		if (object instanceof Node) {
			return ((Node) object).getRequirements().size() > 0;
		}
		return false;
	}
}
