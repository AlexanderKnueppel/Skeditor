package de.tubs.skeditor.utils;

import java.util.ArrayList;
import java.util.List;

import SkillGraph.Category;
import SkillGraph.Controller;
import SkillGraph.Edge;
import SkillGraph.Equation;
import SkillGraph.Node;
import SkillGraph.SkillGraphFactory;
import de.tubs.skeditor.contracting.grammar.GrammarUtil;
import de.tubs.skeditor.synthesis.prover.TermProver;

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
	
	/**
	 * returns a string representation of the given node and all of its children and subchildren and so on
	 * 
	 * @param parent, the node we want to represent the children from
	 * @return the string representation
	 */
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
	
	/**
	 * calculate depth of skillgraph
	 * 
	 * @param root, the root node of the graph
	 * @return the depth of the graph
	 */
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
	
	/**
	 * creates a new edge from parent to child
	 * 
	 * @param parent, the parent node
	 * @param child, the child node
	 * @return the newly created edge
	 */
	public static Edge createEdge(Node parent, Node child) {
		Edge edge = SkillGraphFactory.eINSTANCE.createEdge();
		edge.setParentNode(parent);
		edge.setChildNode(child);
		parent.getChildEdges().add(edge);
		child.getParentNodes().add(parent);
		return edge;
	}
	
	/**
	 * creates a new node with the given name and category
	 * 
	 * @param name, the name for the new node
	 * @param category, the category for the new node
	 * @return the newly created node
	 */
	public static Node createNode(String name, Category category) {
		Node node = SkillGraphFactory.eINSTANCE.createNode();
		node.setName(name);
		node.setCategory(category);
		return node;
	}
	
	/**
	 * removes the given edge
	 * 
	 * @param edge, the edge to remove
	 */
	public static void removeEdge(Edge edge) {
		edge.getParentNode().getChildEdges().remove(edge);
		edge.getChildNode().getParentNodes().remove(edge.getParentNode());
	}
	
	/**
	 * creates exact copy of node but without reference to parents or children
	 * 
	 * @param node, the node to copy
	 * @return, the copy of the node
	 */
	public static Node copyNode(Node node) {
		Node copy = createNode(node.getName(), node.getCategory());
		_copyEquations(node, copy);
		_copyVariables(node, copy);
		_copyController(node, copy);
		_copyRequirements(node, copy);
		copy.setProgramPath(node.getProgramPath());
		return copy;
	}
	
	/**
	 * copies node and all of its children and all of their children and so on
	 * 
	 * @param node, the node to copy
	 * @return, the copy of the node
	 */
	public static Node copyNodeWithChildren(Node node) {
		Node copy = createNode(node.getName(), node.getCategory());
		_copyEquations(node, copy);
		_copyVariables(node, copy);
		_copyController(node, copy);
		_copyRequirements(node, copy);
		copy.setProgramPath(node.getProgramPath());
		if(node.getChildEdges().size() > 0) {
			for(int i = 0; i < node.getChildEdges().size(); i++) {
				_copyNode(node.getChildEdges().get(i).getChildNode(), copy);
			}
		}
		return copy;
	}
	
	/**
	 * checks if the given string is a valid requirement
	 * 
	 * @param requirement
	 * @return true if the given string is a valid requirement, false otherwise
	 */
	public static boolean isValidRequirement(String requirement) {
		
		TermProver prover = new TermProver();
		return GrammarUtil.tryToParse(requirement).isEmpty() & prover.tryParse(requirement);
	}
	
	/**
	 * returns the node with the given name in the graph if included
	 * 
	 * @param name, the name of the skill
	 * @param node, the root node of the graph 
	 * @return the node matching the name or null
	 */
	public static Node getChildByName(String name, Node node) {
		if(node.getName().equals(name)) {
			return node;
		}
		if(!node.getChildEdges().isEmpty()) {
			for (Edge e : node.getChildEdges() ) {
				Node inGraph = getChildByName(name, e.getChildNode());
				if(inGraph != null) {
					return inGraph;
				}
			}
		}
		return null;
	}
	
	/**
	 * returns a List of all propagated variables 
	 * 
	 * @param node, the node we want the propagated variables from
	 * @return a list of all propagated variables without duplicates
	 */
	public static List<String> providedVariablesOf(Node node) {
		List<String> providedVars = new ArrayList<String>();
		if(node.getProvidedVariables() != null) {
			for(String defined : node.getProvidedVariables()) {
				if(!providedVars.contains(defined)) {
					providedVars.add(defined);
				}
			}
		}
		
		if(node.getChildEdges().size() > 0) {
			for (Edge e : node.getChildEdges()){
				for(String defined : providedVariablesOf(e.getChildNode())) {
					if(!providedVars.contains(defined)) {
						providedVars.add(defined);
					}
				}
			}
		}
		
		return providedVars;
	}
	
	/**
	 * checks if the given string is numeric
	 * 
	 * @param str, the string to check
	 * @return true if the given string is a number or false otherwise
	 */
	public static boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
	}
	
	/**PRIVATE FUNCTIONS**/
	private static void _copyNode(Node node, Node parent) {
		Node copy = SynthesisUtil.createNode(node.getName(), node.getCategory());
		_copyEquations(node, copy);
		_copyVariables(node, copy);
		_copyController(node, copy);
		_copyRequirements(node, copy);
		copy.setProgramPath(node.getProgramPath());
		SynthesisUtil.createEdge(parent, copy);
		if(node.getChildEdges().size() > 0) {
			for(int i = 0; i < node.getChildEdges().size(); i++) {
				_copyNode(node.getChildEdges().get(i).getChildNode(), copy);
			}
		}
	}
	
	private static void _copyEquations(Node src, Node dest) {
		for(Equation equation : src.getEquations()) {
			Equation newEquation = SkillGraphFactory.eINSTANCE.createEquation();
			newEquation.setEquation(equation.getEquation());
			newEquation.setNode(dest);
			dest.getEquations().add(newEquation);
		}
	}
	
	private static void _copyVariables(Node src, Node dest) {
		for(String reqVar : src.getRequiredVariables()) {
			dest.getRequiredVariables().add(reqVar);
		}
		for(String defVar : src.getProvidedVariables()) {
			dest.getProvidedVariables().add(defVar);
		}
	}
	
	private static void _copyController(Node src, Node dest) {
		for(Controller ctrl : src.getController()) {
			Controller newController = SkillGraphFactory.eINSTANCE.createController();
			newController.setCtrl(ctrl.getCtrl());
			newController.setNode(dest);
			dest.getController().add(newController);
		}
	}
	
	private static void _copyRequirements(Node src, Node dest) {
		for(SkillGraph.Requirement requirement : src.getRequirements()) {
			SkillGraph.Requirement newRequirement = SkillGraphFactory.eINSTANCE.createRequirement();
			newRequirement.setComment(requirement.getComment());
			newRequirement.setTerm(requirement.getTerm());
			newRequirement.setType(requirement.getType());
			newRequirement.setNode(dest);
			dest.getRequirements().add(newRequirement);
		}
	}
	
	
}
