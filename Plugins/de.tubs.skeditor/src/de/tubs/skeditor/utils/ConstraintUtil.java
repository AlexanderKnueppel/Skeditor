package de.tubs.skeditor.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import SkillGraph.Category;
import SkillGraph.Node;

public class ConstraintUtil {

	private static Category[] mainParent = {Category.MAIN}, mainChild = { Category.OBSERVABLE_EXTERNAL_BEHAVIOR, Category.ACTION, Category.PLANNING , Category.PERCEPTION, Category.ACTUATOR  };
	private static Category[] obsParent = { Category.MAIN }, obsChild = { Category.ACTION, Category.PLANNING };
	private static Category[] actionParent = { Category.MAIN, Category.OBSERVABLE_EXTERNAL_BEHAVIOR, Category.ACTION },
			actionChild = { Category.ACTION, Category.PERCEPTION, Category.PLANNING, Category.SENSOR, Category.ACTUATOR };
	private static Category[] actuatorParent = { Category.MAIN, Category.ACTION }, actuatorChild = {};
	private static Category[] planningParent = { Category.MAIN, Category.OBSERVABLE_EXTERNAL_BEHAVIOR, Category.ACTION }, planningChild = { Category.PERCEPTION };
	private static Category[] perceptionParent = { Category.MAIN, Category.PLANNING, Category.ACTION, Category.PERCEPTION }, perceptionChild = { Category.SENSOR, Category.PERCEPTION };
	private static Category[] sensorParent = { Category.MAIN, Category.PERCEPTION, Category.PLANNING, Category.ACTION }, sensorChild = {};

	/**
	 * Checks if a node can be changed into a different category
	 * 
	 * @param node
	 *            The node
	 * @param category
	 *            The new category
	 * @return
	 */
	public static boolean areConstraintsValid(Node node, Category category) {
		ArrayList<Node> parents = GraphUtil.getParentNodes(node);
		ArrayList<Node> children = GraphUtil.getChildNodes(node);

		switch (category) {
		case MAIN:
			return check(parents, Arrays.asList(mainParent), children, Arrays.asList(mainChild), category);

		case OBSERVABLE_EXTERNAL_BEHAVIOR:
			return check(parents, Arrays.asList(obsParent), children, Arrays.asList(obsChild), category);
		case ACTION:
			return check(parents, Arrays.asList(actionParent), children, Arrays.asList(actionChild), category);
		case ACTUATOR:
			return check(parents, Arrays.asList(actuatorParent), children, Arrays.asList(actuatorChild), category);
		case PLANNING:
			return check(parents, Arrays.asList(planningParent), children, Arrays.asList(planningChild), category);
		case PERCEPTION:
			return check(parents, Arrays.asList(perceptionParent), children, Arrays.asList(perceptionChild), category);
		case SENSOR:
			return check(parents, Arrays.asList(sensorParent), children, Arrays.asList(sensorChild), category);
		default:
			return false;
		}
	}

	public static boolean canAddEdge(Node parent, Node child) {
		Category parentCat = parent.getCategory();
		Category childCat = child.getCategory();
		return getValidChildCategories(parentCat).contains(childCat) && getValidParentCategories(childCat).contains(parentCat);
	}

	private static List<Category> getValidChildCategories(Category category) {
		switch (category) {
		case MAIN:
			return Arrays.asList(mainChild);
		case OBSERVABLE_EXTERNAL_BEHAVIOR:
			return Arrays.asList(obsChild);
		case ACTION:
			return Arrays.asList(actionChild);
		case ACTUATOR:
			return Arrays.asList(actuatorChild);
		case PLANNING:
			return Arrays.asList(planningChild);
		case PERCEPTION:
			return Arrays.asList(perceptionChild);
		case SENSOR:
			return Arrays.asList(sensorChild);
		default:
			return null;
		}
	}

	private static List<Category> getValidParentCategories(Category category) {
		switch (category) {
		case MAIN:
			return Arrays.asList(mainParent);
		case OBSERVABLE_EXTERNAL_BEHAVIOR:
			return Arrays.asList(obsParent);
		case ACTION:
			return Arrays.asList(actionParent);
		case ACTUATOR:
			return Arrays.asList(actuatorParent);
		case PLANNING:
			return Arrays.asList(planningParent);
		case PERCEPTION:
			return Arrays.asList(perceptionParent);
		case SENSOR:
			return Arrays.asList(sensorParent);
		default:
			return null;
		}
	}

	private static boolean check(ArrayList<Node> parents, List<Category> validParents, ArrayList<Node> children, List<Category> validChildren, Category category) {
		for (Node parent : parents) {
			if (!validParents.contains(parent.getCategory())) {
				return false;
			}
		}
		for (Node child : children) {
			if (!validChildren.contains(child.getCategory())) {
				return false;
			}
		}
		return true;
	}

}
