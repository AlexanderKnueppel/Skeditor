package de.tubs.skeditor.synthesis.search;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import SkillGraph.Category;
import SkillGraph.Graph;
import SkillGraph.Node;

/**
 * class for skill search. It reads all graphs from a given folder and allows to search for skills from them.
 * 
 *  @author Christopher Göbel
 * 
 */
public class SkillSearch {

	private static final SkillSearch INSTANCE = new SkillSearch(); //instance for this singleton class
	
	private static final String NAME_STRING = "name=";
	private static final String CATEGORY_STRING = "category=";
	private static final String PROVIDED_STRING = "provided=";
	private static final String DEFINED_STRING = "defined=";
	private static final String REQUIRED_STRING = "required=";
	private Set<Node> nodeRepo;
	/**
	 * Used to create the singleton instance. 
	 */
	private SkillSearch() {
		nodeRepo = new HashSet<>();
	}
	
	/**
	 * Returns the instance of this class
	 * @return instance of this class
	 */
	public static SkillSearch getInstance() {
		return INSTANCE;
	}
	
	public Set<Node> getAllSkills() {
		return nodeRepo;
	}
	/**
	 * 
	 * @param filter, the search filter
	 * @return Set of skills matching filter
	 * @throws FilterFormatException 
	 */
	public Set<Node> searchSkills(String filter) throws FilterFormatException {
		Set<Node> skills = new HashSet<>();
		Deque<Character> stack = new ArrayDeque<>(); //for finding matching parenthesis in filter
		String restFilter = "";
		if(filter.startsWith("(")) { //if filter starts with parenthesis, find matching closing parenthesis
			int i = 0; 
			do {
				if(filter.charAt(i) == '(') {
					stack.push('(');
				} else if(filter.charAt(i) == ')') {
					stack.pop();
				}
				i++;
			} while(!stack.isEmpty());
			skills = searchSkills(filter.substring(1, i-1));
		} else if(filter.startsWith(NAME_STRING)){ //name argument
			String[] filterArray = filter.split("\"", 3);
			String name = filterArray[1]; // name argument should be in double quotes
			restFilter = filterArray[2];
			for(Node node : nodeRepo) {
				if (node.getName().equals(name)) {
					skills.add(node);
				}
			}
		} else if(filter.startsWith(CATEGORY_STRING)){ //category argument
			String[] filterArray = filter.split("\"", 3);
			String category = filterArray[1]; // name argument should be in double quotes
			restFilter = filterArray[2];
			for(Node node : nodeRepo) {
				if(isValidCategory(category)) {
					if(node.getCategory().getName().equalsIgnoreCase(category)) {
						skills.add(node);
					}
				} else {
					throw new FilterFormatException("unknown category \""+category+"\"");
				}
			}
		} else if(filter.startsWith(PROVIDED_STRING)){ //provided variable argument
			return Collections.emptySet();
		} else if(filter.startsWith(DEFINED_STRING)){ //defined variable argument
			String[] filterArray = filter.split("\"", 3);
			String providedVariables = filterArray[1]; // name argument should be in double quotes
			restFilter = filterArray[2];
			String[] varArray = providedVariables.split(",");
			for(Node node : nodeRepo) {
				for (String var : varArray) {
					if(node.getProvidedVariables().contains(var)) {
						skills.add(node);
						break;
					}
				}
			}
		} else if(filter.startsWith(REQUIRED_STRING)){ //required variable argument
			String[] filterArray = filter.split("\"", 3);
			String requiredVariables = filterArray[1]; // name argument should be in double quotes
			restFilter = filterArray[2];
			String[] varArray = requiredVariables.split(",");
			for(Node node : nodeRepo) {
				for (String var : varArray) {
					if(node.getRequiredVariables().contains(var)) {
						skills.add(node);
						break;
					}
				}
			}
		} else {
			throw new FilterFormatException("unknown filter argument \""+filter+"\"");
		}
		if(!restFilter.equals("")) {
			switch(restFilter.charAt(0)) {
			case '&': //AND operator
				for (Node node : skills) {
					if(!(searchSkills(restFilter.substring(1))).contains(node)) {
						skills.remove(node);
					}
				}
			case '|': // OR operator
				for (Node node : searchSkills(restFilter.substring(1))) {
					skills.add(node);
				}
			default:
				throw new FilterFormatException("unknown operation \""+restFilter.charAt(0)+"\"");
			}
		}
		//TODO read every skill and filter them, repo must be specified
		return skills;
	}
	
	/**
	 * initializes skill repository from all graphs in folder repoName
	 * 
	 * @param repoName, the name of the skillgraph repository (folder) 
	 */
	public void initializeRepository(Set<Graph> repo) {
		for(Graph g : repo) {
			for(Node node : g.getNodes()) {
				nodeRepo.add(node);
			}
		}
	}
	
	private boolean isValidCategory(String category) {
		switch(category.toLowerCase()) {
		case "main": 
			break;
		case "observable_external_behavior":
			break;
		case "perception":
			break;
		case "planning":
			break;
		case "action":
			break;
		case "sensor":
			break;
		case "actuator":
			break;
		default:
			return false;
		}
		return true;
	}
}
