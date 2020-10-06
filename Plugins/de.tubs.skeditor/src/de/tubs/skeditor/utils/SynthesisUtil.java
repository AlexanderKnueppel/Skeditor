package de.tubs.skeditor.utils;

import SkillGraph.Category;
import SkillGraph.Edge;
import SkillGraph.Node;
import SkillGraph.SkillGraphFactory;

public class SynthesisUtil {
	
	/**
	 * Checks whether an edge can be created. 
	 * @param parent
	 * @param child
	 * @return false if child has an invalid category or there is a path from parent to child
	 */
	public static boolean canCreateEdge(Node parent, Node child) {
		boolean result =  !(containsNode(parent, child)!=null || containsNode(child, parent)!=null);
		return result & isValidCategoryChild(parent, child);
	}
	
	public static boolean isValidCategoryChild(Node parent, Node child) {
		switch(parent.getCategory()) {
		case ACTION: 
			if(child.getCategory() == Category.ACTION || child.getCategory() == Category.ACTUATOR || child.getCategory() == Category.PERCEPTION || child.getCategory() == Category.PLANNING) {
				return true;
			}
			break;
		case MAIN:
		case OBSERVABLE_EXTERNAL_BEHAVIOR:
			if(child.getCategory() == Category.OBSERVABLE_EXTERNAL_BEHAVIOR || child.getCategory() == Category.ACTION || child.getCategory() == Category.PLANNING || child.getCategory() == Category.PERCEPTION) {
				return true;
			}
			break;
		case PERCEPTION:
			if(child.getCategory() == Category.PERCEPTION || child.getCategory() == Category.SENSOR) {
				return true;
			}
			break;
		case PLANNING:
			if(child.getCategory() == Category.PERCEPTION || child.getCategory() == Category.PLANNING) {
				return true;
			}
			break;
		default:
			break;
		}
		return false;
	}
	
	public static Node containsNode(Node n1, Node n2) {
		if(n1.getName().equals(n2.getName())) {
			return n1;
		}
		for(Edge e : n1.getChildEdges()) {
			Node node = containsNode(e.getChildNode(), n2);
			if(node != null) {
				return node;
			}
		}
		return null;
	}
	
	public static String childsToString(Node parent) {
		if(parent == null) {
			return "";
		}
		String str = "("+parent.getName()+")->(";
		for(Edge e : parent.getChildEdges()) {
			str += childsToString(e.getChildNode());
		}
		str += ")";
		return str;
		
	}
	
	public static int depth(Node root) {
		if(root == null) {
			return 0;
		} else if(root.getChildEdges().size() == 0) {
			return 1;
		} else {
			int maxDepth = 0;
			for(Edge e : root.getChildEdges()) {
				int childDepth = depth(e.getChildNode());
				if(maxDepth < childDepth) {
					maxDepth = childDepth;
				}
			}
			return 1+maxDepth;
		}
	}
	
	public static Edge createEdge(Node parent, Node child) {
		Edge edge = SkillGraphFactory.eINSTANCE.createEdge();
		edge.setParentNode(parent);
		edge.setChildNode(child);
		parent.getChildEdges().add(edge);
		child.getParentNodes().add(parent);
		return edge;
	}
	
	public static Node createNode(String name, Category category) {
		Node node = SkillGraphFactory.eINSTANCE.createNode();
		node.setName(name);
		node.setCategory(category);
		return node;
	}
	
	public static void removeEdge(Edge edge) {
		edge.getParentNode().getChildEdges().remove(edge);
		edge.getChildNode().getParentNodes().remove(edge.getParentNode());
	}
}
