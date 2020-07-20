/**
* The skillComposition is responible for the composition of skills. 
* 
*
* @author  Arne Windeler
* @version 1.0
* @since   2020-01-14 
*/

package de.tubs.skeditor.compositionality;

import org.eclipse.emf.common.util.EList;

import SkillGraph.Controller;
import SkillGraph.Edge;
import SkillGraph.Node;
import SkillGraph.SkillGraphFactory;

public class SkillComposition {
	public static void composingSkills(Node nodeA, Node nodeB) {
		prepairingSkills(nodeA, nodeB);

		String s = nodeA.getController().get(0).getCtrl();

		composeSkillRelations(nodeA, nodeB);

		nodeA.getController().get(0).setCtrl(
				s + "\n" + nodeB.getController().get(0).getCtrl() + "\n " + "\n" + "HP main::= {mainA ++ mainB}.");
	}

	// this methode prepares skills with dissimilar hybrid program for the
	// composition
	public static void prepairingSkills(Node nodeA, Node nodeB) {

		Controller conA = nodeA.getController().get(0);
		String sConA = conA.getCtrl();

		sConA = prepairingController(sConA, 'A');

		Controller conB = nodeB.getController().get(0);
		String sConB = conB.getCtrl();

		sConB = prepairingController(sConB, 'B');

		nodeA.getController().get(0).setCtrl(sConA);

		nodeB.getController().get(0).setCtrl(sConB);

	}

	public static boolean isInAlphabet(char c) {

		if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
			return true;
		else
			return false;

	}

	public static String getMethod(String s, int xyz) {
		int x = xyz;
		for (int i = x; !isInAlphabet(s.charAt(i)); i++) {
			x += 1;
		}

		int y = x;
		for (int i = y; isInAlphabet(s.charAt(i)); i++) {
			y += 1;
		}

		return s.substring(x, y);
	}

	public static void composeSkillRelations(Node a, Node b) {

		EList<Node> parents = b.getParentNodes();
		for (int i = 0; i < parents.size(); i++) {

			if (!parentExisting(a, parents.get(i))) {
				a.getParentNodes().add(parents.get(i));

				for (int x = 0; x < parents.get(i).getChildEdges().size(); x++) {

					if (b.getName() == parents.get(i).getChildEdges().get(x).getChildNode().getName()) {

						parents.get(i).getChildEdges().get(x).setChildNode(a);

					}

				}
			}
		}

		EList<Edge> childEdgesB = b.getChildEdges();
		EList<Edge> childEdgesA = a.getChildEdges();

		for (int i = 0; i < childEdgesB.size(); i++) {
			Edge e = childEdgesB.get(i);
			if (!edgeExisting(e, childEdgesA)) {
				Edge edge = SkillGraphFactory.eINSTANCE.createEdge();
				edge.setParentNode(a);
				edge.setChildNode(e.getChildNode());
				edge.getChildNode().getParentNodes().add(a);
				a.getChildEdges().add(edge);
			}

		}

		for (int i = 0; i < b.getEquations().size(); i++) {
			boolean isIn = false;
			for (int x = 0; x < a.getEquations().size(); x++) {
				if (a.getEquations().get(x).getEquation().equals(b.getEquations().get(i).getEquation())) {
					isIn = true;
				}

			}
			if (!isIn) {
				a.getEquations().add(b.getEquations().get(i));
			}
		}

		for (int i = 0; i < b.getRequirements().size(); i++) {
			boolean isIn = false;
			for (int x = 0; x < a.getRequirements().size(); x++) {
				if (a.getRequirements().get(x).getTerm().equals(b.getRequirements().get(i).getTerm())) {
					isIn = true;
				}

			}
			if (!isIn) {
				a.getRequirements().add(b.getRequirements().get(i));
			}
		}

	}

	public static String prepairingController(String string, char c) {
		String s = string;
		String[] ident = { "R", "HP" };

		for (int z = 0; z < ident.length; z++) {

			int x = s.indexOf(ident[z], 0);

			while (x != -1) {
				int y = x;

				String method = getMethod(s, y + ident[z].length());

				s = s.replaceAll(method, method + c);
				x = s.indexOf(ident[z], x + (ident[z].length() + 1));
			}
		}

		return s;
	}

	public static boolean edgeExisting(Edge e, EList<Edge> edges) {
		for (Edge x : edges) {
			if (x.getChildNode().getName().equals(e.getChildNode().getName())
					&& x.getParentNode().getName().equals(e.getParentNode().getName())) {
				return true;
			}
		}
		return false;
	}

	public static boolean parentExisting(Node c, Node p) {
		for (Node n : c.getParentNodes()) {
			if (n.getName() != null && n.getName().equals(p.getName())) {
				return true;
			}
		}
		return false;
	}

	public static boolean composableControllers(Node a, Node b) {
		if (a.getController().isEmpty() || b.getController().isEmpty()) {
			return false;
		}

		String contrA = a.getController().get(0).getCtrl();
		contrA = prepairingController(contrA, 'A');
		String[] contrArrayA = contrA.split("\\P{Alpha}+");

		String contrB = b.getController().get(0).getCtrl();
		contrB = SkillComposition.prepairingController(contrB, 'B');
		String[] contrArrayB = contrB.split("\\P{Alpha}+");
		for (String x : contrArrayA) {
			for (String y : contrArrayB) {
				if (!x.isEmpty() && !y.isEmpty()) {
					if (y.equals(x) && Character.isLowerCase(y.charAt(0)) && isControlled(x, contrA)
							&& isControlled(y, contrB)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static boolean isControlled(String var, String cont) {

		if (var.equals("t")) {
			return false;
		}
		int x = cont.indexOf(var, 0);

		while (x != -1) {
			int y = x + var.length();
			boolean checking = true;
			while (checking == true && cont.length() > y) {
				if (Character.isWhitespace(cont.charAt(y))) {
					y++;
				} else if (cont.charAt(y) == ':') {
					y++;
					for (int i = y; Character.isWhitespace(cont.charAt(i)); i = y) {
						y++;
					}
					if (cont.charAt(y) == '=') {
						return true;
					}
				} else {
					checking = false;
				}

			}
			x = cont.indexOf(var, y);

		}

		return false;
	}

	public static boolean composableRequirements(Node a, Node b) {

		// toDo

//    	String req = null;
//    	for(int i = 0; i < a.getRequirements().size(); i++) {
//    		req = a.getRequirements().get(i).getTerm();
//    		 if(!ContractPropagator.validClause(req, b.getRequirements())) {
//    			 return false;
//    		 }
//    	}
		return true;
	}

}
