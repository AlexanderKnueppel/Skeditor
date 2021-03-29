package de.tubs.skeditor.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import SkillGraph.Edge;
import SkillGraph.Equation;
import SkillGraph.Graph;
import SkillGraph.Node;
import SkillGraph.Parameter;
import SkillGraph.Requirement;
import de.tubs.skeditor.sdl.Field;
import de.tubs.skeditor.sdl.Provides;
import de.tubs.skeditor.sdl.SDLModel;
import de.tubs.skeditor.ui.handler.SkillDescriptionLanguageHandler;

public class GraphUtil {

	public static Set<Parameter> getAccessibleVariables(Node node) {
		Set<Parameter> parameters = new HashSet<Parameter>();
		SDLModel model = null;

		if (node.getSDLModel() != null) {
			try {
				model = SkillDescriptionLanguageHandler.textToModel(node.getSDLModel());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Graph g = (Graph) node.eContainer();
			List<Parameter> l = g.getParameterList();
			if (model.getSkill().getVariables() != null && model.getSkill().getVariables().getProvides() != null) {
				for (Field f : model.getSkill().getVariables().getProvides().getVariables()) {
					parameters.addAll(
							l.stream().filter(e -> e.getAbbreviation().equals(f.getName())).collect(Collectors.toList()));
				}
			}
		}

		if (getChildNodes(node).isEmpty()) {
			return parameters;
		} else {
			for (Node child : getChildNodes(node)) {
				parameters.addAll(getAccessibleVariables(child));
			}
		}

		return parameters;
	}

	public static Set<Parameter> getCollectedParameters(Node node) {
		Set<Parameter> parameters = new HashSet<Parameter>();
		SDLModel model = null;

		if (node.getSDLModel() != null) {
			try {
				model = SkillDescriptionLanguageHandler.textToModel(node.getSDLModel());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Graph g = (Graph) node.eContainer();
			for (Parameter p : g.getParameterList()) {
				// Constants
				if (model.getSkill().getParameters() != null) {
					for (Field f : model.getSkill().getParameters().getVariables()) {
						if (p.getAbbreviation().equals(f.getName())) {
							parameters.add(p);
						}
					}
				}
				// Provided variables
				if (model.getSkill().getVariables() != null) {
					for (Field f : model.getSkill().getVariables().getProvides().getVariables()) {
						if (p.getAbbreviation().equals(f.getName())) {
							parameters.add(p);
						}
					}
				}
			}
		}

		if (getChildNodes(node).isEmpty()) {
			return parameters;
		} else {
			for (Node child : getChildNodes(node)) {
				parameters.addAll(getCollectedParameters(child));
			}
		}

		return parameters;
	}

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
	 * @param container  The main list to insert into
	 * @param insertable The list to insert
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
