package de.tubs.skeditor.views.safetygoalsview;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.ITreeContentProvider;

import SkillGraph.Assumption;
import SkillGraph.Graph;
import SkillGraph.Node;
import SkillGraph.Requirement;

public class ContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getChildren(Object object) {
		Node node = (Node) object;
		List<Object> elements = new ArrayList<Object>();
		elements.addAll(node.getRequirements());
		elements.addAll(node.getAssumptions());
		return elements.toArray();
	}

	@Override
	public Object[] getElements(Object object) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		Node root = ((Graph) object).getRootNode();
		if (root.getRequirements().size() > 0 || root.getAssumptions().size() > 0) {
			nodes.add(root);
		}
		for (Node node : ((Graph) object).getNodes()) {
			if (node.getRequirements().size() > 0 || node.getAssumptions().size() > 0) {
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
		} else if (object instanceof Assumption) {
			Assumption req = (Assumption) object;
			return req.getNode();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object object) {
		if (object instanceof Node) {
			return ((Node) object).getRequirements().size() > 0 || ((Node) object).getAssumptions().size() > 0;
		}
		return false;
	}
}
