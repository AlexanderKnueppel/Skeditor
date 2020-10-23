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

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import SkillGraph.Category;
import SkillGraph.Edge;
import SkillGraph.Graph;
import SkillGraph.Node;
import SkillGraph.SkillGraphFactory;
import de.tubs.skeditor.contracting.Contract;
import de.tubs.skeditor.contracting.ContractPropagator;
import de.tubs.skeditor.contracting.grammar.folBaseVisitor;
import de.tubs.skeditor.contracting.grammar.folLexer;
import de.tubs.skeditor.contracting.grammar.folParser;
import de.tubs.skeditor.contracting.grammar.folParser.FormulaContext;
import de.tubs.skeditor.contracting.grammar.folParser.TermContext;
import de.tubs.skeditor.contracting.grammar.folParser.VariableContext;
import de.tubs.skeditor.synthesis.prover.TermProver;
//import de.tubs.skeditor.synthesis.prover.TermProver.OperationVisitor;
import de.tubs.skeditor.utils.SynthesisUtil;

/**
 * Synthesize a new graph 
 * 
 * @author Christopher
 *
 */
public class Synthesis {

	private Deque<SkillInsert> insertStack; //remember insert decisions in stack
	private Node rootNode;
	private List<Requirement> unsatisfiableRequirements;
	private List<Requirement> satisfiedRequirements;
	private List<String> forbiddenSkills; //names of forbidden skills
	private Requirement currentRequirement;
	private List<Requirement> currentRequirementList;
	private TermProver prover;
	
	public Synthesis() {
		unsatisfiableRequirements = new ArrayList<>();
		satisfiedRequirements = new ArrayList<>();
		forbiddenSkills = new ArrayList<>();
		prover = new TermProver();
	}
	
	public List<Requirement> getUnsatisfiableRequirements() {
		return unsatisfiableRequirements;
	}
	
	
	
	/**
	 * synthesizes a new skill graph based on given requirements
	 * 
	 * @param requirements, the given list of requirements
	 * @return the root node of the new graph
	 */
	public Node synthesizeGraph(List<Requirement> requirements) {
		List<Requirement> unsatisfiables= new ArrayList<>();
		Node newGraph = null;
		//Map<Requirement, List<Requirement>> requirementMap = new HashMap<>(); //remember for each requirement every possible solution
		
		for(int i = requirements.size(); i > 0; i--) {
			List<List<Requirement>> subsets = new ArrayList<>();
			getSubSets(requirements, 0, new ArrayList<Requirement>(), subsets, i); //retrieve a list of all subsets of requirements of size i
			for(List<Requirement> requirementSet : subsets) {
				List<List<Requirement>> DNFLists = new ArrayList<>();
				for(Requirement r : requirementSet) {
					DNFLists.add(convertToDNF(r));
				}
				List<List<Requirement>> allCombinations = getAllPossibleCombinations(DNFLists);
				for(List<Requirement> reqs : allCombinations) {
					//synthesize graph
					System.out.println("Liste: "+reqs.toString());
					currentRequirementList = sortRequirements(splitRequirements(reqs));
					System.out.println("current list"+currentRequirementList);
					newGraph = synthesizeGraph_(currentRequirementList, unsatisfiables, satisfiedRequirements);
					if(!unsatisfiables.isEmpty() || newGraph == null) { //every requirement satisfied, return graph
						satisfiedRequirements.clear();
						unsatisfiables.clear();
						forbiddenSkills.clear();
					} else {
						optimize_graph(rootNode); //optimize edges of graph
						requirements.removeAll(requirementSet);
						unsatisfiableRequirements = requirements;
						return newGraph;
					}
				}
			}
			
		}
		if(newGraph == null) {
			newGraph = SynthesisUtil.createNode("ROOT", Category.MAIN);
		}
		return newGraph;
	}
	
	/**PRIVATE FUNCTIONS**/
	
	/*
	 * tries to synthesize a graph satisfying the given requirements
	 */
	private Node synthesizeGraph_(List<Requirement> requirements, List<Requirement> unsatisfiableReqs, List<Requirement> satisfiedReqs) {
		insertStack = new ArrayDeque<>();
		rootNode = SynthesisUtil.createNode("ROOT", Category.MAIN);
		int requirementIndex = 0;
		int myDepth = 0;
		boolean resolved = true;
		boolean insertSuccess = true;
		
		Map<Requirement, RequirementInsert> insertEventforReq = new HashMap<>();
		Map<Requirement, List<Integer>> indiciesOfinsertedNodes = new HashMap<>();
		List<Integer> insertedRequirementIndicies = new ArrayList<>();
		RequirementSkillProvider lastProvider = null;
		System.out.println("requirements: "+requirements+requirements.size());
		if(requirements.size() == 0) {
			return null;
		}
		while(requirements.size() > requirementIndex) {
			resolved = true;
			System.out.println("Requirement: "+requirements.get(requirementIndex).getFormula());
			currentRequirement = requirements.get(requirementIndex);
			
			
				//if the graph is still valid, the current requirement is already satisfied
				if (!currentRequirement.getFormula().startsWith("has")) {
					if(checkValidity()) { 
						requirementIndex++;
						satisfiedReqs.add(currentRequirement);
						continue;
					}
				} else {
					if(checkHasCondition(currentRequirement)) { 
						requirementIndex++;
						satisfiedReqs.add(currentRequirement);
						continue;
					}
				}
				
				RequirementSkillProvider requirementProvider;
				
				if(lastProvider != null) { //use lastProvider if it is not null
					requirementProvider = lastProvider;
					lastProvider = null;
				} else {
					if(!currentRequirement.getFormula().startsWith("has")) {
						requirementProvider= new RequirementSkillProvider(currentRequirement);
					} else {
						requirementProvider= new HasRequirementSkillProvider(currentRequirement);
					}
					
				}
				
				//check every candidate returned by provider
				Node candidate = requirementProvider.getNext();
				
				while(candidate != null) {
					List<Node> insertedNodes = new ArrayList<>();
					List<Edge> insertedEdges = new ArrayList<>();
					System.out.println("Candidate: "+SynthesisUtil.childsToString(candidate));
					insertSuccess = insertCandidate(candidate, rootNode, insertedNodes, insertedEdges);

					RequirementInsert requirementInsert = null;
					
					if(insertedEdges.size() > 0) {//wenn ein knoten eingefügt wurde, danna auch safe ne kante, wenn keine kanten, dann auch keine knoten
						
						requirementInsert = new RequirementInsert(0, null, insertedNodes, insertedEdges, currentRequirement, requirementProvider);
						//remember current requirement insert and the root node of candidate inserted for that requirement
					} 
					if(requirementInsert != null) { //remember insertion in stack if at least one edge was inserted
						List<Integer> indicies = new ArrayList<>();
						int lastNum = 1;
						//#########
						insertStack.push(requirementInsert);
						//#########
						insertEventforReq.put(currentRequirement, requirementInsert);
						VariableSkillProvider lastVariableProvider = null;
						//printStack(); //////////////////
						boolean returnValue = true;
						//resolve dependencies of nodes inserted for this requirement
						System.out.println("Anzahl hinzugefügter knoten: "+insertedNodes.size());
						int i;
						for(i = 0; i < insertedNodes.size(); i++) {
							int previousSize = insertStack.size();
							returnValue = resolveDependenciesOf(myDepth+1, insertedNodes.get(i), requirementInsert, lastVariableProvider, lastNum);
							lastVariableProvider = null;
							lastNum = 1;
							if(insertStack.size() - previousSize > 0 && returnValue == true) { //we inserted new nodes
								System.out.println("Stack hat sich vergößert und alles gut");
								if(lastNum == 1) {
									indicies.add(i);
								}
								
							}
							if(returnValue == true && i == insertedNodes.size()-1) {
								System.out.println("wir sin fertig!!");
								resolved = true;
							}
							if(returnValue == false) {
								SkillInsert insert = insertStack.pop();
								while(!(insert instanceof RequirementInsert)) {
									for(Edge e : insert.getInsertedEdges()) {
										SynthesisUtil.removeEdge(e);
									}
									insert = insertStack.pop();
								}
								break;
								/*if(indicies.size() == 0) { //dependencies of first inserted node could not be resolved, so try next candidate
									insertStack.pop(); //remove last inserted requirement insert
									for(Edge e : requirementInsert.getInsertedEdges()){
										SynthesisUtil.removeEdge(e);
									}
									resolved = false;
									break;
								} else { //try revert last insert
									int lastIndex = indicies.get(indicies.size()-1);
									SkillInsert lastInsert = insertStack.pop();
									printStack(); //////////////////
									while(!(((DependencyInsert) lastInsert).getNode() == insertedNodes.get(lastIndex) && lastInsert.getNumber() == 1)) {
										for(Edge inserted : lastInsert.getInsertedEdges()) { //remove edges inserted by lastInsert
											SynthesisUtil.removeEdge(inserted);
										}
										
										//DependencyInsert dependencyInsert = (DependencyInsert) lastInsert;
										returnValue = resolveDependenciesOf(lastInsert.getDepth(), ((DependencyInsert) lastInsert).getNode(), lastInsert.getParent(), (VariableSkillProvider)lastInsert.getSkillProvider(), lastInsert.getNumber());
										if(returnValue == true) {
											SkillInsert parent = lastInsert.getParent();
											if(!(parent instanceof RequirementInsert)) {
												while(((DependencyInsert) parent).getNode() != insertedNodes.get(lastIndex) && returnValue == true) {
													returnValue = resolveDependenciesOf(parent.getDepth(), ((DependencyInsert) parent).getNode(), parent.getParent(), null, parent.getNumber()+1);
													parent = parent.getParent();
												}
												if(returnValue == true){
													lastNum = parent.getNumber()+1;
												} 
											}
											
										}
										if(returnValue == false) {
											lastInsert = insertStack.pop();
											printStack(); //////////////////
										} else {
											break;
										}
									}
									if(returnValue == false) {
										i = lastIndex - 1;
										indicies.remove(indicies.size() - 1);
										lastVariableProvider = (VariableSkillProvider) lastInsert.getSkillProvider();
										for(Edge inserted : lastInsert.getInsertedEdges()) { //remove edges inserted by lastInsert
											SynthesisUtil.removeEdge(inserted);
										}
									} else {
										i = lastIndex - 1;
										lastVariableProvider = null;
									}
								}*/
								
							}
						}
						
						if(resolved == true) { //all dependencies of requirement insert could be resolved
							//System.out.println("hab gebreakt");
							indiciesOfinsertedNodes.put(currentRequirement, indicies);
							//satisfiedRequirements.add(currentRequirement);
							break; //break while and take next requirement
							
						} 
					} else {
						resolved = true;
					}
					if(insertSuccess == false) { //failed to insert candidate
						candidate = requirementProvider.getNext();
						continue;
					}
					if(resolved == false) { //could not resolve dependencies
						for(Edge e : requirementInsert.getInsertedEdges()) {
							SynthesisUtil.removeEdge(e);
						}
						
						candidate = requirementProvider.getNext();
						continue;
					} else {
						break;
					}
				}
				//System.out.println("bin jetzt nach while candidate");
				//if there is no more candidate left, current requirement not satisfiable
				if(candidate == null) {
					//System.out.println("nicht satisfiable");
					unsatisfiableReqs.add(currentRequirement);
					return null;
				} else {
					satisfiedRequirements.add(currentRequirement);
				}
				insertedRequirementIndicies.add(requirementIndex);
				requirementIndex++;
			} 
		return rootNode;
		
	}
	
	/*
	 * resolves dependencies of given node
	 */
	private boolean resolveDependenciesOf(int depth, Node node, SkillInsert parentInsert, VariableSkillProvider lastProvider, int lastNumber) {
		int number = lastNumber;
		Map<String, DependencyInsert> insertEventforVar = new HashMap<>();
		Map<String, List<Integer>> indiciesOfinsertedNodes = new HashMap<>();
		List<Integer> insertedVariableIndicies = new ArrayList<>();
		VariableSkillProvider variableProvider = lastProvider;
		boolean insertSuccess = true;
		
		List<String> requiredVariables = node.getRequiredVariables();
		System.out.println("resolve dependencies of "+node.getName()+" lastprovider:"+lastProvider);
		for(int i = 0; i < requiredVariables.size(); i++) {
				
			String requiredVar = requiredVariables.get(i);
			System.out.println("Benötigte variable: "+requiredVar+", Knoten: "+node.getName());
			List<String> providedVariables = SynthesisUtil.providedVariablesOf(node);
				
			if(!providedVariables.contains(requiredVar)) { //variable muss noch bedient werden
				System.out.println("Benötigte variable: "+requiredVar+", Knoten: "+node.getName()+ " wird noch gebraucht!");
				/*if(lastVariableProvider != null && !lastVariableProvider.getRequiredVariable().equals(requiredVar)) {
					System.out.println("EIN RIESEN PROBLEM GIBT ES!!!!!!!");
					return false;
				}*/
				if(variableProvider == null) {
					variableProvider = new VariableSkillProvider(requiredVar, node.getProvidedVariables());
				}
					
					
				//check every candidate returned by provider
				Node candidate = variableProvider.getNext();
				while(candidate != null) {
					List<Node> insertedNodes = new ArrayList<>();
					List<Edge> insertedEdges = new ArrayList<>();
					insertSuccess = insertCandidate(candidate, node, insertedNodes, insertedEdges);
					DependencyInsert variableInsert = null;
					boolean returnValue = true;
					if(insertedEdges.size() > 0) {//wenn ein knoten eingefügt wurde, danna auch safe ne kante, wenn keine kanten, dann auch keine knoten
						variableInsert = new DependencyInsert(depth, parentInsert, node, insertedNodes, insertedEdges, variableProvider); 
						variableInsert.setNumber(number);
						insertEventforVar.put(requiredVar, variableInsert);
						number++;
					}
					if(variableInsert != null) {
							
						insertStack.push(variableInsert);
						//printStack();
						List<Integer> indicies = new ArrayList<>();
						VariableSkillProvider lastVariableProvider = null;
						int lastNum = 1;
						//System.out.println("Wir haben "+insertedNodes.size()+" knoten eingefügt");
						for(int j = 0; j < insertedNodes.size(); j++) {
						
							int previousSize = insertStack.size();
							returnValue = resolveDependenciesOf(depth+1, insertedNodes.get(j), variableInsert, lastVariableProvider, lastNum);	
							System.out.println(insertedNodes.get(j).getName()+" resolved: "+returnValue);
							if(insertStack.size()-previousSize > 0 && returnValue == true) { //we inserted new nodes
								indicies.add(j);
								//System.out.println("inserted candidate!");
								//reset temp variables for last variableprovider and last number
								lastVariableProvider = null;
								lastNum = 1;
								//indiciesOfinsertedNodes.put(requiredVar, indicies);
							} else if(returnValue == false) {
								SkillInsert insert = insertStack.pop();
								while(insert != parentInsert) {
									for(Edge e : insert.getInsertedEdges()) {
										SynthesisUtil.removeEdge(e);
									}
								}
								break;
							}
								/*if(indicies.size() == 0) { //we could not resolve dependencies of inserted nodes
									break;
								} else {
									
									int lastIndex = indicies.get(indicies.size()-1); //set j to last index in the next run
									
										
									lastInsert = insertStack.pop();
									while(!(((DependencyInsert) lastInsert).getNode() == insertedNodes.get(lastIndex) && lastInsert.getNumber() == 1)) {
										for(Edge e : lastInsert.getInsertedEdges()) {
											SynthesisUtil.removeEdge(e);
										}
										returnValue = resolveDependenciesOf(lastInsert.getDepth(), ((DependencyInsert) lastInsert).getNode(), lastInsert.getParent(), (VariableSkillProvider)lastInsert.getSkillProvider(), lastInsert.getNumber());
										if(returnValue == true) {
											SkillInsert parent = lastInsert.getParent();
											while(((DependencyInsert) parent).getNode() != insertedNodes.get(lastIndex) && returnValue == true) {
												returnValue = resolveDependenciesOf(parent.getDepth(), ((DependencyInsert) parent).getNode(), parent.getParent(), null, parent.getNumber()+1);
												parent = parent.getParent();
											}
											
											if(returnValue == true) { // in next run resolve dependencies of last inserted node first
												lastNum = parent.getNumber()+1; 
											}
											
											
										} else if(returnValue == false) {
											lastInsert = insertStack.pop();
										} else {
											break;
										}
									}
									if(returnValue == false) {
										indicies.remove(indicies.size()-1);
										j = lastIndex - 1;
										lastVariableProvider = (VariableSkillProvider) lastInsert.getSkillProvider();
									} else {
										j = lastIndex - 1;
										lastVariableProvider = null;
									}
								}
							}*/
						}
						indiciesOfinsertedNodes.put(requiredVar, indicies);
					}
					if(returnValue == true && insertSuccess == true) {
						insertedVariableIndicies.add(i); //remember index of satisfied required variable
						break;
					} else if(insertSuccess == false) {
						candidate = variableProvider.getNext();
						System.out.println("Nächster kandidat, da einfügen nicht ging: "+candidate);
					} else {
						for(Edge e : variableInsert.getInsertedEdges()) {
							SynthesisUtil.removeEdge(e);
						}
						System.out.println("kandidat insert wieder rausnehmen");
						//printStack();
						//insertStack.pop();
						indiciesOfinsertedNodes.remove(requiredVar);
						candidate = variableProvider.getNext();
						System.out.println("Nächster kandidat: "+candidate);
					}
				}
				if (candidate == null) {
					System.out.println("candidate for "+requiredVar+" ist null");
					variableProvider = null;
					insertEventforVar.remove(node.getRequiredVariables().get(i));
					//indiciesOfinsertedNodes.remove(node.getRequiredVariables().get(i));
						
					if(insertedVariableIndicies.size() == 0) { //we have no satisfied variable, so return false
						return false;
					} else { //revert to previous satisfied variable
						SkillInsert insert = insertStack.pop();
						while(insert != parentInsert) {
							for(Edge e : insert.getInsertedEdges()) {
								SynthesisUtil.removeEdge(e);
							}
						}
						return false;
						/*int lastNum = 1;
						int lastSatisfiedVariableIndex = insertedVariableIndicies.get(insertedVariableIndicies.size() - 1);
						List<Integer> nodeIndicies = indiciesOfinsertedNodes.get(node.getRequiredVariables().get(lastSatisfiedVariableIndex));
						DependencyInsert lastVariableInsert = insertEventforVar.get(node.getRequiredVariables().get(lastSatisfiedVariableIndex));
						DependencyInsert lastInsert = null;
						boolean returnValue = false;
						int lastIndex = -1;
						if(nodeIndicies != null && !nodeIndicies.isEmpty()) {
							lastIndex = nodeIndicies.get(nodeIndicies.size() - 1);
						} else { //no skills inserted for the skills inserted for the last variable, so go on with last variable
							for(Edge e : lastVariableInsert.getInsertedEdges()) {
								SynthesisUtil.removeEdge(e);
							}
							i = lastSatisfiedVariableIndex - 1;
							variableProvider = (VariableSkillProvider) lastVariableInsert.getSkillProvider();
							insertedVariableIndicies.remove(insertedVariableIndicies.size()-1);
							insertStack.pop();
							insertEventforVar.remove(node.getRequiredVariables().get(lastSatisfiedVariableIndex));
							continue;
						}
						lastInsert = (DependencyInsert)insertStack.pop();
						System.out.println("lastInsert: "+lastInsert.toString());
						//while(!( lastInsert.getNode() == lastVariableInsert.getInsertedSkills().get(nodeIndicies.get(0)) && lastInsert.getNumber() == 1)) { //solange wir nicht den ersten insert des ersten knotens haben für die letzte eingefügte variable
						while(lastInsert != lastVariableInsert) {
							System.out.println("lastinsert ist nicht letzter variablen insert");
							for(Edge e : lastInsert.getInsertedEdges()) {
								SynthesisUtil.removeEdge(e);
							}
							returnValue = resolveDependenciesOf(lastInsert.getDepth(), ((DependencyInsert) lastInsert).getNode(), lastInsert.getParent(), (VariableSkillProvider)lastInsert.getSkillProvider(), lastInsert.getNumber());
							if(returnValue == true) {
								SkillInsert parent = lastInsert.getParent();
								while(((DependencyInsert) parent).getNode() != lastVariableInsert.getInsertedSkills().get(lastIndex) && returnValue == true) {
									System.out.println("in while parent");
									returnValue = resolveDependenciesOf(parent.getDepth(), ((DependencyInsert) parent).getNode(), parent.getParent(), null, parent.getNumber()+1);
									parent = parent.getParent();
								}
								if(returnValue == true){
									returnValue = resolveDependenciesOf(parent.getDepth(), lastVariableInsert.getInsertedSkills().get(lastIndex), parent.getParent(), null, parent.getNumber()+1);
									
									for(int k = lastIndex+1; k < lastVariableInsert.getInsertedSkills().size(); k++) {
										int previousStackSize = insertStack.size();
										returnValue = resolveDependenciesOf(parent.getDepth(), lastVariableInsert.getInsertedSkills().get(k), parent.getParent(), null, 1);
										if(insertStack.size() - previousStackSize > 0) {
											nodeIndicies.add(k);
										}
									}
								} 
								break;
							} else {
								System.out.println("return false 1");
								lastInsert = (DependencyInsert)insertStack.pop();
							} 
						}
						if(returnValue == false) {
							System.out.println("return false 2");
							for(Edge e : lastVariableInsert.getInsertedEdges()) {
								SynthesisUtil.removeEdge(e);
							}
							i = lastSatisfiedVariableIndex - 1;
							variableProvider = (VariableSkillProvider) lastVariableInsert.getSkillProvider();
							insertedVariableIndicies.remove(insertedVariableIndicies.size() - 1);
							insertEventforVar.remove(node.getRequiredVariables().get(lastSatisfiedVariableIndex));
							indiciesOfinsertedNodes.remove(node.getRequiredVariables().get(lastSatisfiedVariableIndex));
						} else {
							System.out.println("return true 2");
							i = i - 1;
							variableProvider = null;
						}*/
					}
				} else {
					System.out.println("candidate ist nicht null!");
					variableProvider = null;
				}
			}
				
		}
		System.out.println("after for!");
		return true;
	}
	
	/*
	 * inserts the given candidate 
	 */
	private boolean insertCandidate(Node candidate, Node parentNode, List<Node> insertedNodesList, List<Edge> insertedEdgeList) {
		List<Node> insertedNodes = insertedNodesList;
		List<Edge> insertedEdges = insertedEdgeList;
		int depth = SynthesisUtil.depth(candidate);
		Node lastCheckedNode = parentNode;
		Node temp = candidate;
		System.out.println("tiefe von kandidat: "+depth);
		System.out.println("kandidat: "+SynthesisUtil.childsToString(candidate));
		//System.out.println("root: "+rootNode);
		if(!SynthesisUtil.canCreateEdge(parentNode, candidate)) { // we cannot create edge from parent to candidate, so ignore candidate
			System.out.println("keine kante möglich!");
			return false;
		}
		//check if candidate contains a forbidden skill
		for(int i = 0; i < depth; i++) {
			if(forbiddenSkills.contains(temp.getName().replace(" ", "_"))) { //skill is forbidden
				System.out.println("skill ist leider verboten");
				return false;
			}
			if(i < depth - 1) {
				temp = temp.getChildEdges().get(0).getChildNode(); //temp has only one child
			}
		}
		
		temp = candidate;
		for(int i = 0; i < depth; i++) {
			
			//check if temp is already in graph
			Node inGraph = SynthesisUtil.getChildByName(temp.getName(), rootNode);
			if(inGraph != null) {
				System.out.println("Knoten ist im graphen drin");
				if(lastCheckedNode != null) {
					//check if edge from lastCheckedNode to inGraph already exists
					boolean isParent = false;
					for(Node parent : inGraph.getParentNodes()) {
						if(parent == lastCheckedNode) { //lastCheckedNode is parent of inGraph
							isParent = true;
						}
					}
					if(!isParent) { //lastCheckedNode is not parent of ingraph yet
						Edge newEdge = SynthesisUtil.createEdge(lastCheckedNode, inGraph);
						insertedEdges.add(newEdge);
						lastCheckedNode = inGraph; //remember last checked node
					}
				}
			} else { //node is not in graph yet
				System.out.println("Knoten ist nicht im graphen drin");
				Node newNode = SynthesisUtil.copyNode(temp);
				Edge newEdge = SynthesisUtil.createEdge(lastCheckedNode, newNode);
				insertedEdges.add(newEdge);
				insertedNodes.add(newNode);
				lastCheckedNode = newNode; //remember last checked node
			
			}
			if(i < depth - 1) {
				temp = temp.getChildEdges().get(0).getChildNode(); //temp has only one child
			}
			
		}
		
		
		//if the graph is not valid anymore, remove all inserted edges
		if(!checkValidity()) {
			System.out.println("Graph ist nicht mehr valide!");
			for(Edge e : insertedEdges) {
				SynthesisUtil.removeEdge(e);
			}
			insertedEdges.clear();
			insertedNodes.clear();
			return false;
		}
		System.out.println(SynthesisUtil.childsToString(rootNode));
		return true;
	}
	
	/*
	 * check validity of graph
	 */
	private boolean checkValidity() {
		String guarantees = "";
		String toCheck = "";
		for(Edge e : rootNode.getChildEdges()) {
			Contract contract = ContractPropagator.computeContract(e.getChildNode());
			if(guarantees.length() == 0) {
				guarantees += contract.getGuarantee();
			} else {
				guarantees += "&"+contract.getGuarantee();
			}
			
		}
		
		guarantees = guarantees.replace(" ", ""); //remove whitespaces
		System.out.println("garantie: "+guarantees);
		System.out.println("bereists erfüllte: "+satisfiedRequirements);
		for(Requirement r : satisfiedRequirements) {
			if(!r.getFormula().contains("has")) {
				if(toCheck.length() == 0) {
					toCheck += r.getFormula();
				} else {
					toCheck += "&"+r.getFormula();
				}
			}
		}
		
		if(!currentRequirement.getFormula().contains("has")) {
			if(toCheck.length() == 0) {
				toCheck += currentRequirement.getFormula();
			} else {
				toCheck += "&"+currentRequirement.getFormula();
			}
		}
		System.out.println("toCheck: "+toCheck);
		if(guarantees.length() == 0) {
			return false;
		}
		
		String[] currentRequirementStrings = new String[currentRequirementList.size()+1];
		int i;
		for(i = 0; i < currentRequirementList.size(); i++) {
			currentRequirementStrings[i] = currentRequirementList.get(i).getFormula();
		}
		currentRequirementStrings[i] = guarantees;
		return prover.prove(toCheck, guarantees)&prover.check(currentRequirementStrings);
	}
	
	/*
	 * checks the "has" condition 
	 */
	private boolean checkHasCondition(Requirement hasReq) {
		String guarantee = "";
		for(Edge e : rootNode.getChildEdges()) {
			if(guarantee.length() == 0) {
				guarantee += convertSkillsToCondition(e.getChildNode());
			} else {
				guarantee += "&"+convertSkillsToCondition(e.getChildNode());
			}
			
		}
		System.out.println("has check guarantee: "+guarantee);
		//no skill in graph yet
		if(guarantee.length() == 0) {
			return false;
		}
		return prover.prove(hasReq.getFormula(), guarantee);
	}
	
	/*
	 * converts all skills in the graph to a string representing has conditions
	 */
	private String convertSkillsToCondition(Node node) {
		String s = "";
		if(node.getChildEdges().isEmpty()) {
			return "has(\""+node.getName().replace(" ", "_")+"\")";
		}
		s = "has(\""+node.getName().replace(" ", "_")+"\")";
		for(Edge e : node.getChildEdges()) {
			s += "&"+convertSkillsToCondition(e.getChildNode());
		}
		return s;
	}
	
	
		
		/*
		 * negates the given List of requirements representing a DNF
		 */
		private static List<Requirement> convertToNot(List<Requirement> reqs){
			List<Requirement> requirements = new ArrayList<>(reqs);
			List<Requirement> requirementList = new ArrayList<>();
			List<Requirement> toReturn = new ArrayList<>();
			if(requirements.isEmpty()) {
				return requirementList;
			} 
			Requirement req = requirements.remove(0);
			for(String s : req.getFormula().split("&")) {
				if(s.startsWith("!")) {
					requirementList.add(new Requirement(s.substring(1)));
				} else {
					requirementList.add(new Requirement("!"+s));
				}
			}
			if(requirements.isEmpty()) {
				return requirementList;
			}
			List<Requirement> otherList = convertToNot(requirements);
			for(Requirement r : requirementList) {
				for(Requirement r2 : otherList) {
					toReturn.add(new Requirement(r.getFormula()+"&"+r2.getFormula()));
				}
			}
			return toReturn;
		}
		
		/*
		 * converts the given requirement to a list of requirements representing a DNF
		 */
		private static List<Requirement> convertToDNF(Requirement req) {
			class DNFVisitor extends folBaseVisitor<List<Requirement>> {
				@Override 
				public List<Requirement> visitCondition(folParser.ConditionContext ctx) {
					//System.out.println(ctx.getText());
					if(ctx.formula() != null) {
						return visitFormula(ctx.formula());
					}
					return null;
				}

				@Override
				public List<Requirement> visitFormula(FormulaContext ctx) {
					//System.out.println("formula:"+ctx.getText());
					if(ctx.formula() != null) {
						return visitFormula(ctx.formula());
					} else if(ctx.quantifier() != null) {
						return visitQuantifier(ctx.quantifier());
					} else if(ctx.connectiveformula() != null) {
						return visitConnectiveformula(ctx.connectiveformula());
					} else {
						return null;
					}
				}
				
				@Override
				public List<Requirement> visitConnectiveformula(folParser.ConnectiveformulaContext ctx) {
					//System.out.println("has_cond: "+ctx.getText());
					List<Requirement> requirementList;
					
					if(ctx.boolexpression() != null) {
						requirementList = new ArrayList<>();
						requirementList.add(new Requirement(ctx.boolexpression().getText()));
						//System.out.println("Bool: "+ctx.boolexpression().getText()+" List: "+requirementList);
						for(int i = 0; i < ctx.connectiveformula().size(); i++) {
							List<Requirement> secondList = visitConnectiveformula(ctx.connectiveformula(i));
							List<Requirement> newList = new ArrayList<>();
							switch(ctx.connectoperator(i).getText()) {
							case "&":
							case "&&":
							case "and":
								for(Requirement req1 : requirementList) {
									for(Requirement req2 : secondList) {
										newList.add(new Requirement(req1.getFormula()+"&"+req2.getFormula()));
									}
								}
								break;
							case "|":
							case "||":
							case "or":
								for(Requirement req1 : requirementList) {
									newList.add(new Requirement(req1.getFormula()));
								}
								for(Requirement req2 : secondList) {
									newList.add(new Requirement(req2.getFormula()));
								}
								
								break;
							case "=>":
								for(Requirement req1 : convertToNot(requirementList)) {
									newList.add(new Requirement(req1.getFormula()));
								}
								for(Requirement req2 : secondList) {
									newList.add(new Requirement(req2.getFormula()));
								}
								break;
							case "<>":
								for(Requirement req1 : convertToNot(requirementList)) {
									for(Requirement req2 : convertToNot(secondList)) {
										newList.add(new Requirement(req1.getFormula()+"&"+req2.getFormula()));
									}
								}
								for(Requirement req1 : requirementList) {
									for(Requirement req2 : secondList) {
										newList.add(new Requirement(req1.getFormula()+"&"+req2.getFormula()));
									}
								}
								break;
							default:
								return null;
							}
							requirementList = newList;
						}
					} else {
						requirementList = visitConnectiveformula(ctx.connectiveformula(0));
						if(ctx.NOT() != null) {
							requirementList = convertToNot(requirementList);
						}
						for(int i = 1; i < ctx.connectiveformula().size(); i++) {
							List<Requirement> secondList = visitConnectiveformula(ctx.connectiveformula(i));
							List<Requirement> newList = new ArrayList<>();
							switch(ctx.connectoperator(i-1).getText()) {
							case "&":
							case "&&":
							case "and":
								for(Requirement req1 : requirementList) {
									for(Requirement req2 : secondList) {
										newList.add(new Requirement(req1.getFormula()+"&"+req2.getFormula()));
									}
								}
								break;
							case "|":
							case "||":
							case "or":
								for(Requirement req1 : requirementList) {
									newList.add(new Requirement(req1.getFormula()));
								}
								for(Requirement req2 : secondList) {
									newList.add(new Requirement(req2.getFormula()));
								}
								
								break;
							case "=>":
								for(Requirement req1 : convertToNot(requirementList)) {
									newList.add(new Requirement(req1.getFormula()));
								}
								for(Requirement req2 : secondList) {
									newList.add(new Requirement(req2.getFormula()));
								}
								break;
							case "<>":
								for(Requirement req1 : convertToNot(requirementList)) {
									for(Requirement req2 : convertToNot(secondList)) {
										newList.add(new Requirement(req1.getFormula()+"&"+req2.getFormula()));
									}
								}
								for(Requirement req1 : requirementList) {
									for(Requirement req2 : secondList) {
										newList.add(new Requirement(req1.getFormula()+"&"+req2.getFormula()));
									}
								}
								break;
							default:
								return null;
							}
							requirementList = newList;
						}
					}
					return requirementList;
				}
			}
			folLexer lexer = new folLexer(CharStreams.fromString(req.getFormula()));
			folParser parser = new folParser(new CommonTokenStream(lexer));
			DNFVisitor myVisitor = new DNFVisitor();
			return myVisitor.visit(parser.condition());
		}
		
		/*
		 * get subsets of requirements of specified size
		 */
		private void getSubSets(List<Requirement> requirements, int index, List<Requirement> taken, List<List<Requirement>> result, int size) {
			if (index == requirements.size()) {
				//System.out.println("Added "+taken.toString());
				if(taken.size() == size) {
					result.add(taken);
				}
			} else {
				getSubSets(requirements, index + 1, taken, result, size); 
				List<Requirement> copy = new ArrayList<>(taken);
				copy.add(requirements.get(index));
				getSubSets(requirements, index + 1, copy, result, size); 
			}
		}
		
		public static void main(String[] args) {
			List<Requirement> list1 = convertToDNF(new Requirement("( ep > 0 || r == 0 )"));
			List<Requirement> list2 = convertToDNF(new Requirement("has(\"Decelerate\") & has(\"Accelerate\")"));
			//List<Requirement> list3 = convertToDNF(new Requirement("a>4&(b<0|(c>3&d==0))"));
			System.out.println(list1);
			//System.out.println(list2);
			//System.out.println(list3);
			List<List<Requirement>> DNFs = new ArrayList<>();
			DNFs.add(list1);
			DNFs.add(list2);
			//DNFs.add(list3);
			System.out.println("Möflichkeiten: "+getAllPossibleCombinations(DNFs).toString());
		}
		
		/*
		 * retrieve all possible combinations of requirements from the given DNFs
		 */
		private static List<List<Requirement>> getAllPossibleCombinations(List<List<Requirement>> DNFLists){
			List<List<Requirement>> toReturn = new ArrayList<>();
			if(DNFLists.isEmpty()) {
				//System.out.println("returned");
				return toReturn;
			} else if(DNFLists.size() == 1) {
				for(Requirement r : DNFLists.remove(0)) {
					List<Requirement> newList = new ArrayList<>();
					newList.add(r);
					toReturn.add(newList);
				}
				//System.out.println("returned" + toReturn);
				return toReturn;
			} 
			List<Requirement> reqs = DNFLists.remove(0);
			//System.out.println("reqs:"+reqs );
			List<List<Requirement>> otherLists = getAllPossibleCombinations(DNFLists);
			//System.out.println("otherLists:"+otherLists );
			for(List<Requirement> list : otherLists) {
				for(Requirement r : reqs) {
					List<Requirement> newList = new ArrayList<>(list);
					newList.add(r);
					toReturn.add(newList);
				}
			}
			return toReturn;
		}
		
		/*
		 * split requirement at the & symbol and remove duplicates
		 */
		private List<Requirement> splitRequirements(List<Requirement> requirements){
			List<Requirement> reqs = new ArrayList<>();
			for(Requirement r : requirements) {
				for(String s : r.getFormula().split("&")) {
					Requirement req = new Requirement(s);
					if(!reqs.contains(req)) {
						reqs.add(req);
					}
				}
			}
			return reqs;
		}
		
		/*
		 * sort requirements, requirements with "has" will come to the end
		 */
		private List<Requirement> sortRequirements(List<Requirement> requirements){
			List<Requirement> sorted = new ArrayList<>();
			//first add all requirements that are formulas
			for(Requirement r : requirements) {
				if(!r.getFormula().contains("has")) { 
					sorted.add(r);
				}
			}
			//all remaining requirements are has conditions, combine them to one requirement
			for(Requirement r : requirements) {
				if(r.getFormula().contains("!has")) { 
					forbiddenSkills.add(r.getFormula().split("\"", 3)[1]); //add name of forbidden skill
				} else if(r.getFormula().contains("has")) { 
					sorted.add(r);
				}
			}
			return sorted;
		}
		
		/*
		 * remove redundant edges 
		 */
		private void optimize_graph(Node node) {
			List<Edge> toRemove = new ArrayList<>();
			for(Edge e : node.getChildEdges()) {
				for(Edge _e : node.getChildEdges()) {
					for(Edge __e : _e.getChildNode().getChildEdges()) {
						if(e.getChildNode() == __e.getChildNode()) { //node has a child that has a child that node has too
							toRemove.add(e); //remove edge from node to that child
						}
					}
				}
			}
			for(int i = 0; i < toRemove.size(); i++) {
				SynthesisUtil.removeEdge(toRemove.get(i));
			}
			for(Edge e : node.getChildEdges()) {
				optimize_graph(e.getChildNode());
			}
		}
	
		
	/*private void _providedVariablesOf(Node node, List<String> variables) {
		List<String> providedVars = variables;
		if(node.getProvidedVariables() != null) {
			for(String defined : node.getProvidedVariables()) {
				if(!providedVars.contains(defined)) {
					providedVars.add(defined);
				}
			}
		}
		if(node.getChildEdges().size() > 0) {
			for (Edge e : node.getChildEdges()) {
				_providedVariablesOf(e.getChildNode(), providedVars);
			}
		}
	}*/
}
