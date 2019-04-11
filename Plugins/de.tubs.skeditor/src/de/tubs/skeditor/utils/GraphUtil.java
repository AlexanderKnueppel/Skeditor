package de.tubs.skeditor.utils;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import SkillGraph.Edge;
import SkillGraph.Equation;
import SkillGraph.Node;
import SkillGraph.Requirement;

public class GraphUtil {
	public static ArrayList<Node> getChildNodes(Node node) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		for (Edge edge : node.getChildEdges()) {
			nodes.add(edge.getChildNode());
		}
		return nodes;
	}

	public static ArrayList<Node> getParentNodes(Node node) {
		ArrayList<Node> nodes = new ArrayList<>(Arrays.asList((Node[]) node.getParentNodes().toArray()));
		return nodes;
	}

	public static ArrayList<Edge> getChildEdges(Node node) {
		ArrayList<Edge> edges = new ArrayList<>(Arrays.asList((Edge[]) node.getChildEdges().toArray()));
		return edges;
	}

	public static ArrayList<Edge> getParentEdges(Node node) {
		ArrayList<Edge> edges = new ArrayList<Edge>();
		for (Node parentNode : node.getParentNodes()) {
			for (Edge edge : parentNode.getChildEdges()) {
				if (edge.getChildNode().equals(node)) {
					edges.add(edge);
				}
			}
		}
		return edges;
	}

	public static ArrayList<Edge> getAllEdges(Node node) {
		ArrayList<Edge> edges = new ArrayList<Edge>();
		edges.addAll(getChildEdges(node));
		edges.addAll(getParentEdges(node));
		return edges;
	}

	public static boolean hasChildren(Node node) {
		return !node.getChildEdges().isEmpty();
	}

	public static boolean hasParents(Node node) {
		return !node.getParentNodes().isEmpty();
	}

	public static EList<Equation> getPropagatedEquations(Node node) {
		EList<Equation> equationList = new BasicEList<Equation>();
		for (Node childNode : getChildNodes(node)) {
			insertInto(equationList, childNode.getEquations());
			insertInto(equationList, getPropagatedEquations(childNode));
		}
		return equationList;
	}

	public static EList<Requirement> getPreConditionRequirements(Node node) {
		EList<Requirement> requirementList = new BasicEList<Requirement>();
		for (Node childNode : getChildNodes(node)) {
			insertInto(requirementList, childNode.getRequirements());
			insertInto(requirementList, getPreConditionRequirements(childNode));
		}
		return requirementList;
	}

	/**
	 * Inserts all elements from insertable into container, that are not already
	 * included there
	 * 
	 * @param <T>
	 * 
	 * @param container
	 *            The main list to insert into
	 * @param insertable
	 *            The list to insert
	 * @return The container with new elements
	 */
	private static <T> EList<T> insertInto(EList<T> container, EList<T> insertable) {
		for (T t : insertable) {
			if (!container.contains(t)) {
				container.add(t);
			}
		}
		return container;
	}

}
