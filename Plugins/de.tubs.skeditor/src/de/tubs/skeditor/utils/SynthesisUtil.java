package de.tubs.skeditor.utils;

import java.util.ArrayDeque;
import java.util.Deque;

import SkillGraph.Category;
import SkillGraph.Controller;
import SkillGraph.Edge;
import SkillGraph.Equation;
import SkillGraph.Node;
import SkillGraph.Requirement;
import SkillGraph.SkillGraphFactory;

//import de.tubs.skeditor.synthesis.Requirement;

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
	
	/*
	 * creates exact copy of node but without reference to parents or children
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
	
	/*
	 * copies node and all of its children 
	 */
	public static Node copyNodeRecursive(Node node) {
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
	
	/*
	 * checks if the given string is a valid requirement
	 */
	public static boolean isValidRequirement(String requirement) {
		
		String str = requirement;
		str.replace(" ", ""); //remove white spaces from string
		
		//check if requirement is "include SKILL" requirement
		if(str.startsWith("include")) {
			str = str.replace("include", "");
			if(str.startsWith("not")) {
				str = str.replace("not", "");
			}
			if(!str.matches("([A-Za-z_])([A-Za-z0-9_]*)")) { //no valid skill name given
				return false;
			} else {
				return true;
			}
		}
		
		//split requirement at comparism operator, only one comparism allowed per requirement
		int numOperators = 0;
		char c;
		String operator = "";
		for(int i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			if (c == '<' || c == '>') {
				if(str.charAt(i+1) == '=') { // <= or >=
					operator = String.format("%c=", c);
					i++;
				} else {
					operator = String.format("%c", c);
				}
				numOperators++;
			} else if (c == '=') {
				numOperators++;
				operator = "=";
			} 
		}
		if(numOperators != 1) { //only 1 operator allowed
			return false;
		}
		String[] operands = str.split(operator); 
		if(operands.length != 2) {
			return false;
		}
		/*for(String o : operands) {
			System.out.println(o);
		}*/
		de.tubs.skeditor.synthesis.Requirement req = new de.tubs.skeditor.synthesis.Requirement(requirement);
		return checkOperand(operands[0]) & checkOperand(operands[1]) & !(req.getVariables().isEmpty());
	}
	
	/*
	 * checks if the given operand of a requirement is valid.
	 * this method is a helper function for isValidRequirement()
	 */
	private static boolean checkOperand(String operand) {
		int i; 
		boolean result = true;
		String rest = "";
		Deque<Character> stack = new ArrayDeque<>(); //for finding matching parenthesis in filter
		if(operand.startsWith("(")) { //if filter starts with parenthesis, find matching closing parenthesis
			i = 0;
			do {
				if(operand.charAt(i) == '(') {
					stack.push('(');
				} else if(operand.charAt(i) == ')') {
					if(stack.isEmpty()) {
						return false;
					}
					stack.pop();
				}
				i++;
			} while(!stack.isEmpty() && i < operand.length());
			if(!stack.isEmpty()) { // unbalanced parantheses
				return false;
			}
			rest = operand.substring(i);
			result &= checkOperand(operand.substring(1, i-1));
		} else {
			String toCheck = operand;
			String part1 = "", part2 = "";
			i = 0;
			System.out.println(operand);
			while(i < operand.length()) {
				if(operand.charAt(i) == '+' || operand.charAt(i) == '-' || operand.charAt(i) == '*' || operand.charAt(i) == '/') {
					part1 = operand.substring(0, i);
					part2 = operand.substring(i);
					System.out.println(part1+ " und "+part2);
					break;
				}
				i++;
			}
			if (part1.length() == 0 && part2.length() == 0) {
				toCheck = operand;
			} else {
				if(part1.length() == 0 || part2.length() == 0) { //operand is a operation itself, so operands of operand cannot be empty
					return false;
				} else {
					toCheck = part1;
					rest = part2;
				}
			} 
			if (!toCheck.matches("([A-Za-z_])([A-Za-z0-9_]*)")) { //check if String is valid variable
				System.out.println(toCheck+ " ist keine variable");
				if(!isNumeric(toCheck)) { //check if string is number
					System.out.println(toCheck+ " ist keine zahl");
					return false;
				}
			}
		}
		if(rest.length() > 0) {
			if(!(rest.charAt(0) == '+' || rest.charAt(0) == '-' || rest.charAt(0) == '*' || rest.charAt(0) == '/')) {
				return false;
			}
			result &= checkOperand(rest.substring(1));
		}
		return result;
	}
	
	/*
	 * checks if the given string is numeric
	 */
	public static boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
	}
}
