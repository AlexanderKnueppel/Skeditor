package de.tubs.skeditor.utils;

import SkillGraph.Category;
import SkillGraph.Edge;
import SkillGraph.Node;

public class SynthesisUtil {
	
	/**
	 * Checks whether an edge can be created. 
	 * @param parent
	 * @param child
	 * @return false if child has an invalid category or there is a path from parent to child
	 */
	public static boolean canCreateEdge(Node parent, Node child) {
		boolean result =  !(containsNode(parent, child) || containsNode(child, parent));
		return result & isValidCategoryChild(parent, child);
	}
	
	public static boolean isValidCategoryChild(Node parent, Node child) {
		switch(parent.getCategory()) {
		case ACTION: 
			if(child.getCategory() == Category.ACTION || child.getCategory() == Category.ACTUATOR || child.getCategory() == Category.PERCEPTION || child.getCategory() == Category.PLANNING) {
				return true;
			}
			break;
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
	
	public static boolean containsNode(Node n1, Node n2) {
		if(n1.getName().equals(n2.getName())) {
			return true;
		}
		for(Edge e : n1.getChildEdges()) {
			if(n2.getName().equals(e.getChildNode().getName())) {
				return true;
			}
		}
		boolean result = false;
		for(Edge e : n1.getChildEdges()) {
			result |= containsNode(e.getChildNode(), n2);
		}
		return result;
	}
	
	public static String childsToString(Node parent) {
		String str = "("+parent.getName()+")->(";
		for(Edge e : parent.getChildEdges()) {
			str += childsToString(e.getChildNode());
		}
		str += ")";
		return str;
		
	}
}
