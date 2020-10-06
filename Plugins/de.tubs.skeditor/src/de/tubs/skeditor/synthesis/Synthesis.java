package de.tubs.skeditor.synthesis;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.util.EcoreUtil;

import SkillGraph.Category;
import SkillGraph.Edge;
import SkillGraph.Graph;
import SkillGraph.Node;
import SkillGraph.SkillGraphFactory;
import de.tubs.skeditor.utils.SynthesisUtil;

/**
 * Synthesize a new graph 
 * 
 * @author Christopher
 *
 */
public class Synthesis {

	private Deque<SkillInsert> insertStack; //remember insert decisions in stack
	//private int requirementIndex; //remember index of current requirement
	
	public Synthesis() {
		
		
	}
	
	private boolean checkValidity() {
		return true;
	}
	/**
	 * Synthesizes a new graph based on specified requirements
	 * 
	 * @param requirements, list of specified requirements
	 */
	public Node synthesizeGraph(List<Requirement> requirements) {
		//define root of new graph
		Node rootNode = SkillGraphFactory.eINSTANCE.createNode(); 
		rootNode.setCategory(Category.MAIN);
		rootNode.setName("ROOT");
		//define stack to remember insertions
		insertStack = new ArrayDeque<>();
		int requirementIndex = 0;
		List<Requirement> unsatisfiableRequirements = new ArrayList<>();
		
		while(requirements.size() > requirementIndex) {
			Requirement currentRequirement = requirements.get(requirementIndex);
			SkillProvider requirementProvider = new RequirementSkillProvider(currentRequirement);
			System.out.println(currentRequirement.getFormula());
			Node candidate = requirementProvider.getNext();
			System.out.println("Candidate: "+SynthesisUtil.childsToString(candidate));
			SkillInsert insertEvent = new RequirementInsert(currentRequirement, requirementProvider);
			while(candidate != null) {
				System.out.println("!Candidate: "+SynthesisUtil.childsToString(candidate));
				if(SynthesisUtil.isValidCategoryChild(rootNode, candidate)) { //node can be a child of root
					//insert nodes of node that are not contained yet
					System.out.println("Ist valides kind");
					insertNode(candidate, rootNode, rootNode, insertEvent);
					
					insertStack.push(insertEvent); //insert skillinsert event for requirement
					System.out.println("Inserted "+insertEvent);
					System.out.println("Stack: size "+insertStack.size());
					//resolve dependencies of inserted nodes
					boolean canInsert = true;
					SkillProvider variableProvider = null;
					for(int i = 0; i < insertEvent.getInsertedSkills().length; i++) {
						System.out.println("Dependencies "+insertEvent.getInsertedSkills()[i].getName());
						canInsert = resolveDependencies(insertEvent.getInsertedSkills()[i], rootNode, variableProvider);
						if(!canInsert) {
							SkillInsert lastInsert = insertStack.pop();
							//found requirement insert, must be insert from this loop
							if(lastInsert instanceof RequirementInsert) {
								for(Edge e : lastInsert.getInsertedEdges()) {
									e.getChildNode().getParentNodes().remove(e.getParentNode());
									e.getParentNode().getChildEdges().remove(e);
								}
								break;
							}
							if(lastInsert instanceof DependencyInsert) {
								//found insert from that loop
								
								if(((DependencyInsert) lastInsert).getNode().getName().equals(insertEvent.getInsertedSkills()[i-1].getName())) {
									for(Edge e : lastInsert.getInsertedEdges()) {
										e.getChildNode().getParentNodes().remove(e.getParentNode());
										e.getParentNode().getChildEdges().remove(e);
									}
									i -= 2;
									variableProvider = lastInsert.getSkillProvider(); //take skillProvider from that insert 
								} else { //try to revert last step and take next candidate for that event
									do {
										if(((DependencyInsert) lastInsert).getNode().getName().equals(insertEvent.getInsertedSkills()[i-1].getName())) {
											for(Edge e : lastInsert.getInsertedEdges()) {
												e.getChildNode().getParentNodes().remove(e.getParentNode());
												e.getParentNode().getChildEdges().remove(e);
											}
											i -= 2;
											variableProvider = lastInsert.getSkillProvider(); //take skillProvider from that insert 
											break;
										} else {
											for(Edge e : lastInsert.getInsertedEdges()) {
												e.getChildNode().getParentNodes().remove(e.getParentNode());
												e.getParentNode().getChildEdges().remove(e);
											}
											canInsert = resolveDependencies(((DependencyInsert) lastInsert).getNode(), rootNode, lastInsert.getSkillProvider());
											if(!canInsert) {
												lastInsert = insertStack.pop();
											}
										}
										
									} while(!canInsert && !(lastInsert instanceof RequirementInsert)); //go on until insert successful or lastInsert is requirement insert
									if(lastInsert instanceof RequirementInsert) {
										for(Edge e : lastInsert.getInsertedEdges()) {
											e.getChildNode().getParentNodes().remove(e.getParentNode());
											e.getParentNode().getChildEdges().remove(e);
										}
										break;
									}
									
									
								}
								
							}
							
							
						} else {
							variableProvider = null;
						}
					}
					
					if(!canInsert) { //resolving dependencies failed
						System.out.println("Insert failed");
						candidate = requirementProvider.getNext();
					} else { //dependencies successfully resolved
						System.out.println("Insert success");
						break;
					}
				} else {
					System.out.println("kein valides kind");
					candidate = requirementProvider.getNext();
				}
			}
			if(candidate == null) { //requirement camnot be satisfied
				unsatisfiableRequirements.add(currentRequirement);
			}
			requirementIndex++;
		}
		
		/*OPEN DIALOG HERE AND TELL USER WHICH REQUIREMENTS ARE UNSATISFIABLE*/
		return rootNode;
	}
	
	/*
	 * resolves recursively all dependencies for Node 
	 */
	private boolean resolveDependencies(Node node, Node root, SkillProvider provider) {
		List<String> unsatisfiedVariables = new ArrayList<>();
		for(String requiredVar : node.getRequiredVariables()) {
			unsatisfiedVariables.add(requiredVar);
		}
		
		for (int i = 0; i < unsatisfiedVariables.size(); i++) {
			String reqVar = unsatisfiedVariables.get(i);
			if(unsatisfiedVariables.contains(reqVar)) {
				// at first check Graph, maybe it already contains a skill that satisfies the dependency 
				String[] requiredVariable = {reqVar};
				String[] forbiddenVars = (String[])node.getProvidedVariables().toArray();
				SkillProvider variableProvider;
				if(provider != null) {
					variableProvider = provider;
				}else {
					variableProvider = new VariableSkillProvider(requiredVariable, forbiddenVars); 
				}
				 
				DependencyInsert insertEvent = new DependencyInsert(node, reqVar, null, null, null, variableProvider);
				Node candidate = variableProvider.getNext();
				System.out.println("Candidate r: "+SynthesisUtil.childsToString(candidate));
				while(candidate != null) {
					System.out.println("!Candidate r: "+SynthesisUtil.childsToString(candidate));
					if(SynthesisUtil.isValidCategoryChild(node, candidate) && !nodeAlreadyContained(node, candidate)) { //node can be a child of root
						//insert nodes of node that are not contained yet
						insertNode(candidate, root, node, insertEvent);
						List<String> satisfiedVars = new ArrayList<>();
						System.out.println("provided:");
						for(String provided : providedVariablesOf(node)) {
							System.out.println(provided);
							if(unsatisfiedVariables.contains(provided)) {
								satisfiedVars.add(provided);
								unsatisfiedVariables.remove(provided);
							}
						}
						System.out.println("satisfied:");
						String[] satisfied = new String[satisfiedVars.size()];
						for(int j = 0; j < satisfiedVars.size(); j++) {
							satisfied[j] = satisfiedVars.get(j);
							System.out.println(satisfied[j]);
						}
						insertEvent.setSatisfiedVariables(satisfied);
						if(satisfied.length>0&(insertEvent.getInsertedEdges().length > 0 || insertEvent.getInsertedSkills().length > 0)) {
							insertStack.push(insertEvent);
							System.out.println("Inserted "+insertEvent);
							System.out.println("Stack: size "+insertStack.size());
						} else {
							if(insertEvent.getInsertedEdges().length > 0 || insertEvent.getInsertedSkills().length > 0) {
								for(Edge e : insertEvent.getInsertedEdges()) {
									SynthesisUtil.removeEdge(e);
								}
							}
						}
						
						
						//resolve dependencies of inserted nodes
						SkillProvider lastVariableProvider = null;
						boolean canInsert = true;
						if(insertEvent.getInsertedSkills().length > 0) {
							System.out.println("inserted: "+insertEvent.getInsertedSkills()[0]);
						}
						
						int j = 0;
						while(j < insertEvent.getInsertedSkills().length) {
							canInsert = resolveDependencies(insertEvent.getInsertedSkills()[j], root, lastVariableProvider);
							if(!canInsert) {
								SkillInsert lastInsert = insertStack.pop(); //can only get dependency inserts here
								if(j == 0) {
									for(Edge e : lastInsert.getInsertedEdges()) {
										SynthesisUtil.removeEdge(e);
									}
									for(String var : ((DependencyInsert) lastInsert).getSatisfiedVariables()) {
										unsatisfiedVariables.add(var);
									}
									break;
								} else {
									if(((DependencyInsert) lastInsert).getNode().getName().equals(insertEvent.getInsertedSkills()[j-1].getName())) {
										for(Edge e : lastInsert.getInsertedEdges()) {
											SynthesisUtil.removeEdge(e);
										}
										for(String var : ((DependencyInsert) lastInsert).getSatisfiedVariables()) {
											unsatisfiedVariables.add(var);
										}
										j--;
										lastVariableProvider = lastInsert.getSkillProvider(); //take skillProvider from that insert
										
									}
								}
								
							} else {
								lastVariableProvider = null;
								j++;
							}
						}
						for(String required : node.getRequiredVariables()) {
							if(!unsatisfiedVariables.contains(required)) {
								unsatisfiedVariables.add(required);
							}
						}
						System.out.println("provided:");
						for(String provided : providedVariablesOf(node)) {
							System.out.println(provided);
							if(unsatisfiedVariables.contains(provided)) {
								unsatisfiedVariables.remove(provided);
							}
						}
						
								
							
						
						
						if(!canInsert) { //resolving dependencies failed
							candidate = variableProvider.getNext();
						} else { //dependencies successfully resolved
							break;
						}
					} else {
						candidate = variableProvider.getNext();
					}
				}
				if(candidate == null) {
					return false;
				} 
			}
			
		}
		return true;
	}
	
	private boolean nodeAlreadyContained(Node node, Node toInsert) {
		if(node.getName().equals(toInsert.getName())) {
			return true;
		}
		
			if(!node.getParentNodes().isEmpty()) {
				for(Node parent : node.getParentNodes()) {
					if(nodeAlreadyContained(parent, toInsert)) {
						return true;
					}
				}
			}
			
		
		
		return false;
	}
	/*
	 * inserts toInsert and all children
	 */
	private void insertNode(Node toInsert, Node root, Node parent, SkillInsert insert) {
		SkillInsert insertObject = insert;
		List<Node> insertedNodes = new ArrayList<>();
		List<Edge> insertedEdges = new ArrayList<>();
		int depth = SynthesisUtil.depth(toInsert);
		
		Node temp = toInsert;
		for(int i = 0; i < depth; i++) {
			Node inGraph = getChildByName(temp.getName(), root);
			boolean edgeExists = false;
			if( inGraph != null) { //node is in graph
				if(temp.getChildEdges().size() > 0) { // temp has child
					for(Edge e : inGraph.getChildEdges()) { //check if edge already exists
						if(e.getChildNode().getName().equals(temp.getChildEdges().get(0).getChildNode().getName())) {
							edgeExists = true;
						}
					}
					if(!edgeExists) {
						Node childInGraph = getChildByName(temp.getChildEdges().get(0).getChildNode().getName(), root);
						if(childInGraph != null) { //add new edge between existing nodes
							Edge newEdge = SynthesisUtil.createEdge(inGraph, childInGraph);
							insertedEdges.add(newEdge);
						} else { //add child node and edge between inGraph and child
							String name = temp.getChildEdges().get(0).getChildNode().getName();
							Category category = temp.getChildEdges().get(0).getChildNode().getCategory();
							Node child = SynthesisUtil.createNode(name, category);
							for(String definedVar : temp.getChildEdges().get(0).getChildNode().getProvidedVariables()) {
								child.getProvidedVariables().add(definedVar);
							}
							for(String requiredVar : temp.getChildEdges().get(0).getChildNode().getRequiredVariables()) {
								child.getRequiredVariables().add(requiredVar);
							}
							Edge newEdge = SynthesisUtil.createEdge(inGraph, child);
							insertedNodes.add(child);
							insertedEdges.add(newEdge);
						}
					}
				} 
				
			} else { //node is not in graph
				
				//copy temp and add it to graph
				Node newNode = SynthesisUtil.createNode(temp.getName(), temp.getCategory());
				for(String definedVar : temp.getProvidedVariables()) {
					newNode.getProvidedVariables().add(definedVar);
				}
				for(String requiredVar : temp.getRequiredVariables()) {
					newNode.getRequiredVariables().add(requiredVar);
				}
				insertedNodes.add(newNode);
				
				//set new edge between newNode and parent
				Edge parentEdge = SynthesisUtil.createEdge(parent, newNode);
				insertedEdges.add(parentEdge);
				if(temp.getChildEdges().size() > 0) { // temp has child
					String name = temp.getChildEdges().get(0).getChildNode().getName();
					Node childInGraph = getChildByName(name, root);
					if(childInGraph != null) { //add new edge between newNode and existing childInGraph
						Edge newEdge = SynthesisUtil.createEdge(newNode, childInGraph);
						insertedEdges.add(newEdge);
					} else { //add child node and edge between temp and child
						Category category = temp.getChildEdges().get(0).getChildNode().getCategory();
						Node child = SynthesisUtil.createNode(name, category);
						for(String definedVar : temp.getChildEdges().get(0).getChildNode().getProvidedVariables()) {
							child.getProvidedVariables().add(definedVar);
						}
						for(String requiredVar : temp.getChildEdges().get(0).getChildNode().getRequiredVariables()) {
							child.getRequiredVariables().add(requiredVar);
						}
						Edge newEdge = SynthesisUtil.createEdge(newNode, child);
						insertedNodes.add(child);
						insertedEdges.add(newEdge);
					}
				} 
			}
			if(temp.getChildEdges().size() > 0) {
				temp = temp.getChildEdges().get(0).getChildNode();
			} else {
				temp = null;
			}
			
		}
		Node[] insertedNodesArray = new Node[insertedNodes.size()];
		Edge[] insertedEdgesArray = new Edge[insertedEdges.size()];
		for (int i = 0; i < insertedNodes.size(); i++) {
			insertedNodesArray[i] = insertedNodes.get(i);
		}
		for (int i = 0; i < insertedEdges.size(); i++) {
			insertedEdgesArray[i] = insertedEdges.get(i);
		}
		insertObject.setInsertedSkills(insertedNodesArray);
		insertObject.setInsertedEdges(insertedEdgesArray);
	}
	
	private Node getChildByName(String name, Node node) {
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
	/*
	 * returns a List of all propagated variables 
	 */
	private Set<String> providedVariablesOf(Node node) {
		Set<String> providedVars = new HashSet<String>();
		if(node.getProvidedVariables() != null) {
			if (node.getProvidedVariables().size()>0) {
				providedVars.addAll(node.getProvidedVariables());
			}
		}
		
		if(node.getChildEdges().size() > 0) {
			for (Edge e : node.getChildEdges()) {
				providedVars.addAll(providedVariablesOf(e.getChildNode()));
			}
		}
		
		return providedVars;
	}
}
