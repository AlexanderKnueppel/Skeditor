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
import de.tubs.skeditor.contracting.grammar.folParser.Has_conditionContext;
import de.tubs.skeditor.contracting.grammar.folParser.Has_skillContext;
import de.tubs.skeditor.contracting.grammar.folParser.ScientificContext;
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
	private Requirement currentRequirement;
	private RequirementInsert currentRequirementInsert;
	private Node insertedRootNodeforCurrentRequirement;
	private TermProver prover;
	
	//private int requirementIndex; //remember index of current requirement
	
	public Synthesis() {
		unsatisfiableRequirements = new ArrayList<>();
		satisfiedRequirements = new ArrayList<>();
		prover = new TermProver();
	}
	
	public List<Requirement> getUnsatisfiableRequirements() {
		return unsatisfiableRequirements;
	}
	
	private void printStack() {
		System.out.println("AKTUELLER STACK:");
		for(SkillInsert insert : insertStack) {
			System.out.println(insert);
		}
	}
	
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
	
	private static List<Requirement> convertToDNF(Requirement req) {
		class DNFVisitor extends folBaseVisitor<List<Requirement>> {
			@Override 
			public List<Requirement> visitCondition(folParser.ConditionContext ctx) {
				System.out.println(ctx.getText());
				if(ctx.formula() != null) {
					return visitFormula(ctx.formula());
				}
				if(ctx.has_condition() != null) {
					return visitHas_condition(ctx.has_condition());
				}
				return null;
			}

			@Override
			public List<Requirement> visitFormula(FormulaContext ctx) {
				System.out.println("formula:"+ctx.getText());
				List<Requirement> requirementList = new ArrayList<>();
				if(ctx.formula().size() > 1) { //more than one formula in this context
					List<Requirement> firstFormula = visitFormula(ctx.formula(0));
					List<Requirement> secondFormula = visitFormula(ctx.formula(1)); 
					switch(ctx.bin_connective().getText()) {
					case "&": //connect both requirements
						for(Requirement req1 : firstFormula) {
							for(Requirement req2 : secondFormula) {
								requirementList.add(new Requirement(req1.getFormula()+"&"+req2.getFormula()));
							}
						}
						break;
					case "|": //add both requirements
						for(Requirement req1 : firstFormula) {
							requirementList.add(new Requirement(req1.getFormula()));
						}
						for(Requirement req2 : secondFormula) {
							requirementList.add(new Requirement(req2.getFormula()));
						}
						break;
					case "->":
						for(Requirement req1 : convertToNot(firstFormula)) {
							requirementList.add(new Requirement(req1.getFormula()));
						}
						for(Requirement req2 : secondFormula) {
							requirementList.add(new Requirement(req2.getFormula()));
						}
						break;
					case "<->":
						for(Requirement req1 : convertToNot(firstFormula)) {
							for(Requirement req2 : convertToNot(secondFormula)) {
								requirementList.add(new Requirement(req1.getFormula()+"&"+req2.getFormula()));
							}
						}
						for(Requirement req1 : firstFormula) {
							for(Requirement req2 : secondFormula) {
								requirementList.add(new Requirement(req1.getFormula()+"&"+req2.getFormula()));
							}
						}
						break;
					default:
						return null;
					}
					return requirementList;
				} else if (ctx.formula().size() == 1) { //one formula expression
					if(ctx.NOT() != null) {
						return convertToNot(visitFormula(ctx.formula(0)));
					} else if(ctx.FORALL() != null) {
						requirementList.add(new Requirement("Forall("+ctx.variable().getText()+")"+ctx.formula(0).getText()));
						return requirementList;
					} else if(ctx.EXISTS() != null) {
						requirementList.add(new Requirement("Exists("+ctx.variable().getText()+")"+ctx.formula(0).getText()));
						return requirementList;
					} else {
						return visitFormula(ctx.formula(0));
					}
				} else { //no formula, so only terms
					if(ctx.term().size() > 1) {
						requirementList.add(new Requirement(ctx.term(0).getText()+ctx.binop().getText()+ctx.term(1).getText()));
						return requirementList;
					} else if (ctx.term().size() == 1) {
						requirementList.add(new Requirement(ctx.term(0).getText()));
						return requirementList;
					} else {
						requirementList.add(new Requirement(ctx.BOOL_LITERAL().getText()));
						return requirementList;
					}
				}
			}
			
			@Override
			public List<Requirement> visitHas_condition(Has_conditionContext ctx) {
				System.out.println("has_cond: "+ctx.getText());
				List<Requirement> requirementList = new ArrayList<>();
				if(ctx.has_condition().size() > 1) { //binary connected conditions
					List<Requirement> has1 = visitHas_condition(ctx.has_condition(0));
					List<Requirement> has2 = visitHas_condition(ctx.has_condition(1));
					switch(ctx.bin_connective().getText()) {
					case "&":
						for(Requirement req1 : has1) {
							for(Requirement req2 : has2) {
								requirementList.add(new Requirement(req1.getFormula()+"&"+req2.getFormula()));
							}
						}
						break;
					case "|":
						for(Requirement req1 : has1) {
							requirementList.add(new Requirement(req1.getFormula()));
						}
						for(Requirement req2 : has2) {
							requirementList.add(new Requirement(req2.getFormula()));
						}
						break;
					case "->":
						for(Requirement req1 : convertToNot(has1)) {
							requirementList.add(new Requirement(req1.getFormula()));
						}
						for(Requirement req2 : has2) {
							requirementList.add(new Requirement(req2.getFormula()));
						}
						break;
					case "<->":
						for(Requirement req1 : convertToNot(has1)) {
							for(Requirement req2 : convertToNot(has2)) {
								requirementList.add(new Requirement(req1.getFormula()+"&"+req2.getFormula()));
							}
						}
						for(Requirement req1 : has1) {
							for(Requirement req2 : has2) {
								requirementList.add(new Requirement(req1.getFormula()+"&"+req2.getFormula()));
							}
						}
						break;
					default:
						return null;
					}
					return requirementList;
				} else if (ctx.has_condition().size() == 1 ) {
					if(ctx.NOT() != null) {
						return convertToNot(visitHas_condition(ctx.has_condition(0)));
					}
					return visitHas_condition(ctx.has_condition(0));
				} else {
					requirementList.add(new Requirement("has(\""+ctx.has_skill().skill_name().getText()+"\")"));
					return requirementList;
				}
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
		List<Requirement> list1 = convertToDNF(new Requirement("x>4<->z<0"));
		//List<Requirement> list2 = convertToDNF(new Requirement("f=0|r=3"));
		//List<Requirement> list3 = convertToDNF(new Requirement("a>4&(b<0|c>3)"));
		System.out.println(list1);
		//System.out.println(list2);
		//System.out.println(list3);
		List<List<Requirement>> DNFs = new ArrayList<>();
		DNFs.add(list1);
		//DNFs.add(list2);
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
	 * sort requirements, requirements with has will come to the end
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
		String s = "";
		for(Requirement r : requirements) {
			if(r.getFormula().contains("has")) { 
				if(s.length() == 0) {
					s += r.getFormula();
				} else {
					s += "&"+r.getFormula();
				}
			}
			
			
		}
		if(s.length() > 0) {
			Requirement hasReq = new Requirement(s);
			sorted.add(hasReq);
		}
		
		return sorted;
	}
	
	/*
	 * synthesizes a new skill graph based on given requirements
	 * 
	 */
	public Node synthesizeGraph(List<Requirement> requirements) {
		List<Requirement> unsatisfiables= new ArrayList<>();
		List<Requirement> satisfiables= new ArrayList<>();
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
					newGraph = synthesizeGraph_(sortRequirements(splitRequirements(reqs)), unsatisfiables, satisfiedRequirements);
					if(unsatisfiables.isEmpty()) { //every requirement satisfied, return graph
						requirements.removeAll(requirementSet);
						unsatisfiableRequirements = requirements;
						return newGraph;
					} else {
						satisfiables.clear();
						unsatisfiables.clear();
					}
				}
			}
		}
		return newGraph;
		
	}
	
	public Node synthesizeGraph_(List<Requirement> requirements, List<Requirement> unsatisfiableReqs, List<Requirement> satisfiedReqs) {
		insertStack = new ArrayDeque<>();
		rootNode = SynthesisUtil.createNode("ROOT", Category.MAIN);
		int requirementIndex = 0;
		int myDepth = 0;
		boolean resolved = false;
		boolean insertSuccess = true;
		boolean hasConditionStarted = false;
		
		Map<Requirement, RequirementInsert> insertEventforReq = new HashMap<>();
		Map<Requirement, List<Integer>> indiciesOfinsertedNodes = new HashMap<>();
		List<Integer> insertedRequirementIndicies = new ArrayList<>();
		RequirementSkillProvider lastProvider = null;
		System.out.println("requirements: "+requirements+requirements.size());
		while(requirements.size() > requirementIndex) {
			resolved = false;
			System.out.println("Requirement: "+requirements.get(requirementIndex).getFormula());
			currentRequirement = requirements.get(requirementIndex);
			
			//if the graph is still valid, the current requirement is already satisfied
			if(checkValidity()) { 
				requirementIndex++;
				satisfiedReqs.add(currentRequirement);
				continue;
			}
			
			if(!currentRequirement.getFormula().contains("has")) { //requirement is formula
				RequirementSkillProvider requirementProvider = new RequirementSkillProvider(currentRequirement);
				VariableSkillProvider lastVariableProvider = null;
				//check every candidate returned by provider
				Node candidate = requirementProvider.getNext();
				
				while(candidate != null) {
					List<Node> insertedNodes = new ArrayList<>();
					List<Edge> insertedEdges = new ArrayList<>();
					System.out.println("Candidate: "+SynthesisUtil.childsToString(candidate));
					insertSuccess = insertCandidate(candidate, rootNode, insertedNodes, insertedEdges);
					if(insertSuccess) {
						
					}
					RequirementInsert requirementInsert = null;
					
					if(insertedEdges.size() > 0) {//wenn ein knoten eingefügt wurde, danna auch safe ne kante, wenn keine kanten, dann auch keine knoten
						
						requirementInsert = new RequirementInsert(0, null, insertedNodes, insertedEdges, currentRequirement, requirementProvider);
						//remember current requirement insert and the root node of candidate inserted for that requirement
						currentRequirementInsert = requirementInsert;
						insertedRootNodeforCurrentRequirement = candidate;
					} 
					if(requirementInsert != null) { //remember insertion in stack if at least one edge was inserted
						List<Integer> indicies = new ArrayList<>();
						insertStack.push(requirementInsert);
						printStack(); //////////////////
						boolean returnValue = true;
						
						//resolve dependencies of nodes inserted for this requirement
						System.out.println("Anzahl hinzugefügter knoten: "+insertedNodes.size());
						for(int i = 0; i < insertedNodes.size(); i++) {
							int previousSize = insertStack.size();
							if(lastVariableProvider != null) {
								returnValue = resolveDependenciesOf(myDepth+1, insertedNodes.get(i), requirementInsert, lastVariableProvider, 1);
								lastVariableProvider = null;
							} else {
								returnValue = resolveDependenciesOf(myDepth+1, insertedNodes.get(i), requirementInsert, null, 1);
							}
							if(insertStack.size() - previousSize > 0 && returnValue == true) { //we inserted new nodes
								System.out.println("Stack hat sich vergößert und alles gut");
								indicies.add(i);
							}
							if(returnValue == true && i == insertedNodes.size()-1) {
								System.out.println("wir sin fertig!!");
								resolved = true;
							}
							if(returnValue == false) {
								if(indicies.size() == 0) { //dependencies of first inserted node could not be resolved, so try next candidate
									resolved = false;
									break;
								} else { //try revert last insert
									int lastIndex = indicies.get(indicies.size());
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
											while(((DependencyInsert) parent).getNode() != insertedNodes.get(lastIndex) && returnValue == true) {
												returnValue = resolveDependenciesOf(parent.getDepth(), ((DependencyInsert) parent).getNode(), parent.getParent(), null, parent.getNumber()+1);
												parent = parent.getParent();
											}
											if(returnValue == true){
												returnValue = resolveDependenciesOf(parent.getDepth(), ((DependencyInsert) parent).getNode(), parent.getParent(), null, parent.getNumber()+1);
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
										i = i - 1;
									}
								}
								
							}
						}
						if(resolved == true) { //all dependencies of requirement insert could be resolved
							System.out.println("hab gebreakt");
							break; //break while and take next requirement
							
						} 
					} else {
						resolved = true;
					}
					if(insertSuccess == false) { //failed to insert candidate
						candidate = requirementProvider.getNext();
					}
					if(resolved == false) { //could not resolve dependencies
						candidate = requirementProvider.getNext();
					} else {
						insertedRequirementIndicies.add(requirementIndex);
						break;
					}
					
				}
				System.out.println("bin jetzt nach while candidate");
				//if there is no more candidate left, current requirement not satisfiable
				if(candidate == null) {
					if(!hasConditionStarted) {
						System.out.println("nicht satisfiable");
						unsatisfiableReqs.add(currentRequirement);
						return null;
					} else if(requirementIndex == 0){ //no candidate left for the first requirement -> not satisfiable
						unsatisfiableReqs.add(currentRequirement);
						return null;
					} else {
						//TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT
					}
				} else {
					System.out.println("candidate ist nicht null");
					satisfiedReqs.add(currentRequirement);
				}
			} else { //we are at the HAS requirement
				hasConditionStarted = true;
				boolean hasChecked = checkHasCondition(currentRequirement);
				if(!hasChecked) {
					//TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT
				}
			}
			
			requirementIndex++;
			
		}
		return rootNode;
	}
	
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
		return prover.prove(hasReq.getFormula(), guarantee);
	}
	
	private String convertSkillsToCondition(Node node) {
		String s = "";
		if(node.getChildEdges().isEmpty()) {
			return "has(\""+node.getName()+"\")";
		}
		for(Edge e : node.getChildEdges()) {
			if(s.length() == 0) {
				s += convertSkillsToCondition(e.getChildNode());
			} else {
				s += "&"+convertSkillsToCondition(e.getChildNode());
			}
		}
		return s;
	}
	
	public boolean resolveDependenciesOf(int depth, Node node, SkillInsert parentInsert, VariableSkillProvider lastProvider, int lastNumber) {
		int number = lastNumber;
		Map<String, DependencyInsert> insertEventforVar = new HashMap<>();
		Map<String, List<Integer>> indiciesOfinsertedNodes = new HashMap<>();
		List<Integer> insertedVariableIndicies = new ArrayList<>();
		VariableSkillProvider variableProvider = lastProvider;
		VariableSkillProvider lastVariableProvider = null;
		boolean insertSuccess = true;
		
		List<String> requiredVariables = node.getRequiredVariables();
		System.out.println("resolve dependencies of "+node.getName());
		for(int i = 0; i < requiredVariables.size(); i++) {
				
			String requiredVar = requiredVariables.get(i);
			System.out.println("Benötigte variable: "+requiredVar+", Knoten: "+node.getName());
			List<String> providedVariables = providedVariablesOf(node);
				
			if(!providedVariables.contains(requiredVar)) { //variable muss noch bedient werden
				System.out.println("Benötigte variable: "+requiredVar+", Knoten: "+node.getName()+ " wird noch gebraucht!");
				if(lastVariableProvider != null && !lastVariableProvider.getRequiredVariable().equals(requiredVar)) {
					System.out.println("EIN RIESEN PROBLEM GIBT ES!!!!!!!");
					return false;
				}
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
						List<Integer> indicies = new ArrayList<>();
						SkillInsert lastInsert = null;
						System.out.println("Wir haben "+insertedNodes.size()+" knoten eingefügt");
						for(int j = 0; j < insertedNodes.size(); j++) {
						
							int previousSize = insertStack.size();
							returnValue = resolveDependenciesOf(depth+1, insertedNodes.get(j), variableInsert, lastVariableProvider, 1);	
							System.out.println(insertedNodes.get(j).getName()+" resolved: "+returnValue);
							if(insertStack.size()-previousSize > 0 && returnValue == true) { //we inserted new nodes
								indicies.add(j);
								System.out.println("inserted candidate!");
								indiciesOfinsertedNodes.put(requiredVar, indicies);
							} else if(returnValue == false) {
								if(indicies.size() == 0) { //we could not resolve dependencies of inserted nodes
									break;
								} else {
									
									int lastIndex = indicies.get(indicies.size()-1)-1; //set j to last index in the next run
									
										
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
											if(returnValue == true){
												returnValue = resolveDependenciesOf(parent.getDepth(), ((DependencyInsert) parent).getNode(), parent.getParent(), null, parent.getNumber()+1);
											} 
										}
										if(returnValue == false) {
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
										j = j - 1;
										lastVariableProvider = null;
									}
								}
							}
						}
					}
					if(returnValue == true && insertSuccess == true) {
						insertedVariableIndicies.add(i); //remember index of satisfied required variable
						break;
					} else if(insertSuccess == false) {
						candidate = variableProvider.getNext();
						System.out.println("Nächster kandidat: "+candidate);
					} else {
						for(Edge e : variableInsert.getInsertedEdges()) {
							SynthesisUtil.removeEdge(e);
						}
						candidate = variableProvider.getNext();
						System.out.println("Nächster kandidat: "+candidate);
					}
				}
				if (candidate == null) {
					System.out.println("candidate for "+requiredVar+" ist null");
					variableProvider = null;
					insertEventforVar.remove(node.getRequiredVariables().get(i));
					indiciesOfinsertedNodes.remove(node.getRequiredVariables().get(i));
						
					if(insertedVariableIndicies.size() == 0) { //we have no satisfied variable, so return false
						return false;
					} else { //revert to previous satisfied variable
						int lastSatisfiedVariableIndex = insertedVariableIndicies.get(insertedVariableIndicies.size() - 1);
						List<Integer> nodeIndicies = indiciesOfinsertedNodes.get(node.getRequiredVariables().get(lastSatisfiedVariableIndex));
						DependencyInsert lastVariableInsert = insertEventforVar.get(node.getRequiredVariables().get(lastSatisfiedVariableIndex));
						DependencyInsert lastInsert = null;
						boolean returnValue = false;
						int lastIndex = nodeIndicies.get(nodeIndicies.size() - 1);
						lastInsert = (DependencyInsert)insertStack.pop();
						while(!( lastInsert.getNode() == lastVariableInsert.getInsertedSkills().get(lastIndex) && lastInsert.getNumber() == 1)) {
							for(Edge e : lastInsert.getInsertedEdges()) {
								SynthesisUtil.removeEdge(e);
							}
							returnValue = resolveDependenciesOf(lastInsert.getDepth(), ((DependencyInsert) lastInsert).getNode(), lastInsert.getParent(), (VariableSkillProvider)lastInsert.getSkillProvider(), lastInsert.getNumber());
							if(returnValue == true) {
								SkillInsert parent = lastInsert.getParent();
								while(((DependencyInsert) parent).getNode() != lastVariableInsert.getInsertedSkills().get(lastIndex) && returnValue == true) {
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
									//
								} 
							}
							if(returnValue == false) {
								lastInsert = (DependencyInsert)insertStack.pop();
							} else {
								break;
							}
						}
						if(returnValue == false) {
							i = lastIndex - 1;
							lastVariableProvider = (VariableSkillProvider) lastVariableInsert.getSkillProvider();
							insertedVariableIndicies.remove(insertedVariableIndicies.size() - 1);
							insertEventforVar.remove(node.getRequiredVariables().get(lastIndex));
							indiciesOfinsertedNodes.remove(node.getRequiredVariables().get(lastIndex));
						} else {
							i = i - 1;
							lastVariableProvider = null;
						}
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
	
	public boolean insertCandidate(Node candidate, Node parentNode, List<Node> insertedNodesList, List<Edge> insertedEdgeList) {
		List<Node> insertedNodes = insertedNodesList;
		List<Edge> insertedEdges = insertedEdgeList;
		int depth = SynthesisUtil.depth(candidate);
		Node lastCheckedNode = parentNode;
		Node temp = candidate;
		System.out.println("tiefe von kandidat: "+depth);
		System.out.println("kandidat: "+candidate);
		System.out.println("root: "+rootNode);
		if(!SynthesisUtil.canCreateEdge(parentNode, candidate)) { // we cannot create edge from parent to candidate, so ignore candidate
			System.out.println("keine kante möglich!");
			return false;
		}
		for(int i = 0; i < depth; i++) {
			
			//check if temp is already in graph
			Node inGraph = getChildByName(temp.getName(), rootNode);
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
		
		//optimize edges of graph
		optimize_graph(rootNode, insertedNodes, insertedEdges);
		
		
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
		System.out.println("garantie: "+guarantees);
		for(Requirement r : satisfiedRequirements) {
			if(toCheck.length() == 0) {
				toCheck += r.getFormula();
			} else {
				toCheck += "&"+r.getFormula();
			}
		}
		if(toCheck.length() == 0) {
			toCheck += currentRequirement.getFormula();
		} else {
			toCheck += "&"+currentRequirement.getFormula();
		}
		if(guarantees.length() == 0) {
			return false;
		}
		return prover.prove(toCheck, guarantees);
	}
	
	/*
	 * remove redundant edges 
	 */
	private void optimize_graph(Node node, List<Node> insertedNodes, List<Edge> insertedEdges) {
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
			Node child = toRemove.get(i).getChildNode();
			Node parent = toRemove.get(i).getParentNode();
			SynthesisUtil.removeEdge(toRemove.get(i));
			if(!checkValidity()) {
				Edge newEdge = SynthesisUtil.createEdge(parent, child);
				if(insertedEdges.contains(toRemove.get(i))) {
					insertedEdges.remove(toRemove.get(i));
					insertedEdges.add(newEdge);
				}
			} else {
				if(insertedEdges.contains(toRemove.get(i))) { //we inserted the edge before this call
					insertedEdges.remove(toRemove.get(i));
				}
			}
		}
		for(Edge e : node.getChildEdges()) {
			optimize_graph(e.getChildNode(), insertedNodes, insertedEdges);
		}
	}
	/*
	
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
		
		
		return rootNode;
	}
	
	
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
	*/
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
	private List<String> providedVariablesOf(Node node) {
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
	
	private void _providedVariablesOf(Node node, List<String> variables) {
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
	}
}
