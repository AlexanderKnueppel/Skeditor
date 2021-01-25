package de.tubs.skeditor.synthesis;

import java.util.ArrayDeque;
import java.util.ArrayList;

import java.util.Deque;
import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import SkillGraph.Category;
import SkillGraph.Edge;
import SkillGraph.Node;
import de.tubs.skeditor.contracting.Contract;
import de.tubs.skeditor.contracting.ContractPropagator;
import de.tubs.skeditor.contracting.grammar.folBaseVisitor;
import de.tubs.skeditor.contracting.grammar.folLexer;
import de.tubs.skeditor.contracting.grammar.folParser;
import de.tubs.skeditor.contracting.grammar.folParser.FormulaContext;
import de.tubs.skeditor.synthesis.prover.TermProver;
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
					currentRequirementList = sortRequirements(splitRequirements(reqs));
					newGraph = synthesizeGraph_(currentRequirementList, unsatisfiables, satisfiedRequirements);
					if(!unsatisfiables.isEmpty() || newGraph == null) { //every requirement satisfied, return graph
						satisfiedRequirements.clear();
						unsatisfiables.clear();
						forbiddenSkills.clear();
					} else {
						optimize_graph(rootNode); //optimize edges of graph
						//System.out.println("Requirements: "+requirements);
						requirements.removeAll(requirementSet);
						unsatisfiableRequirements = requirements;
						//System.out.println("unsatisfiable: "+unsatisfiableRequirements);
						return newGraph;
					}
				}
			}
			
		}
		if(newGraph == null) { // no requirement is satisfiable
			newGraph = SynthesisUtil.createNode("ROOT", Category.MAIN);
			unsatisfiableRequirements = requirements;
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
		boolean resolved = true;
		boolean insertSuccess = true;
		
		if(requirements.size() == 0) {
			return null;
		}
		while(requirements.size() > requirementIndex) {
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
				
			if(!currentRequirement.getFormula().startsWith("has")) {
				requirementProvider= new RequirementSkillProvider(currentRequirement);
			} else {
				requirementProvider= new HasRequirementSkillProvider(currentRequirement);
			}
					
			//check every candidate returned by provider
			Node candidate = requirementProvider.getNext();
				
			while(candidate != null) {
				List<Node> insertedNodes = new ArrayList<>();
				List<Edge> insertedEdges = new ArrayList<>();
				insertSuccess = insertCandidate(candidate, rootNode, insertedNodes, insertedEdges);

				RequirementInsert requirementInsert = null;
					
					if(insertedEdges.size() > 0) {//wenn ein knoten eingefügt wurde, danna auch safe ne kante, wenn keine kanten, dann auch keine knoten
						requirementInsert = new RequirementInsert(currentRequirement, insertedNodes, insertedEdges);
					} 
					if(requirementInsert != null) { //remember insertion in stack if at least one edge was inserted
						insertStack.push(requirementInsert);
					
						//resolve dependencies of nodes inserted for this requirement
						resolved = true;
						boolean returnValue;
						int i;
						for(i = 0; i < insertedNodes.size(); i++) {
							returnValue = resolveDependenciesOf(insertedNodes.get(i));
							if(returnValue == false) {
								resolved = false;
								break;
							}
						}
						if(resolved == true) { //all dependencies of requirement insert could be resolved
							break; //break while and take next requirement
						} 
					} 
					if(insertSuccess == false) { //failed to insert candidate
						candidate = requirementProvider.getNext();
						continue;
					}
					if(resolved == false) { //could not resolve dependencies
						SkillInsert insert = insertStack.pop();
						while(insert != requirementInsert) {
							for(Edge e : insert.getInsertedEdges()) {
								SynthesisUtil.removeEdge(e);
							}
							insert = insertStack.pop();
						}
						for(Edge e : requirementInsert.getInsertedEdges()) {
							SynthesisUtil.removeEdge(e);
						}
						candidate = requirementProvider.getNext();
						continue;
					} else {
						break;
					}
				}
				
				//if there is no more candidate left, current requirement not satisfiable
				if(candidate == null) {
					unsatisfiableReqs.add(currentRequirement);
					return null;
				} else {
					satisfiedRequirements.add(currentRequirement);
				}
				requirementIndex++;
			} 
		return rootNode;
	}
	
	/*
	 * resolves dependencies of given node
	 */
	private boolean resolveDependenciesOf(Node node) {
		List<SkillInsert> variableInserts = new ArrayList<>();
		boolean insertSuccess = true;
		
		List<String> requiredVariables = node.getRequiredVariables();
		for(int i = 0; i < requiredVariables.size(); i++) {
				
			String requiredVar = requiredVariables.get(i);
			List<String> providedVariables = SynthesisUtil.providedVariablesOf(node);
				
			if(!providedVariables.contains(requiredVar)) { //variable still unsatisfied
				VariableSkillProvider variableProvider = new VariableSkillProvider(requiredVar, node.getDefinedVariables());
					
				//check every candidate returned by provider
				Node candidate = variableProvider.getNext();
				while(candidate != null) {
					List<Node> insertedNodes = new ArrayList<>();
					List<Edge> insertedEdges = new ArrayList<>();
					insertSuccess = insertCandidate(candidate, node, insertedNodes, insertedEdges);
					DependencyInsert variableInsert = null;
					boolean returnValue = true;
					
					if(insertedEdges.size() > 0) {//wenn ein knoten eingefügt wurde, danna auch safe ne kante, wenn keine kanten, dann auch keine knoten
						variableInsert = new DependencyInsert(requiredVar, insertedNodes, insertedEdges); 
						variableInserts.add(variableInsert);
					}
					if(variableInsert != null) {
							
						insertStack.push(variableInsert);
						for(int j = 0; j < insertedNodes.size(); j++) {
							returnValue = resolveDependenciesOf(insertedNodes.get(j));	
							if(returnValue == false) {
								break;
							}
						}
					}
					if(returnValue == true && insertSuccess == true) {
						break;
					} else if(insertSuccess == false) {
						candidate = variableProvider.getNext();
					} else {
						SkillInsert insert = insertStack.pop();
						while(insert != variableInsert) {
							for(Edge e : insert.getInsertedEdges()) {
								SynthesisUtil.removeEdge(e);
							}
							insert = insertStack.pop();
						}
						for(Edge e : variableInsert.getInsertedEdges()) {
							SynthesisUtil.removeEdge(e);
						}
						variableInserts.remove(variableInsert);
						candidate = variableProvider.getNext();
					}
				}
				if (candidate == null) {
						
					if(variableInserts.size() == 0) { //we have no satisfied variable, so return false
						return false;
					} else { //revert to previous satisfied variable
						SkillInsert insert = insertStack.pop();
						while(insert != variableInserts.get(0)) {
							for(Edge e : insert.getInsertedEdges()) {
								SynthesisUtil.removeEdge(e);
							}
							insert = insertStack.pop();
						}
						for(Edge e : insert.getInsertedEdges()) {
							SynthesisUtil.removeEdge(e);
						}
						return false;
					}
				} 
			}	
		}
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
		if(!SynthesisUtil.canCreateEdge(parentNode, candidate)) { // we cannot create edge from parent to candidate, so ignore candidate
			return false;
		}
		//check if candidate contains a forbidden skill
		for(int i = 0; i < depth; i++) {
			if(forbiddenSkills.contains(temp.getName().replace(" ", "_"))) { //skill is forbidden
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
		if(guarantees.length() == 0) {
			return false;
		}
		
		String[] currentRequirementStrings = new String[currentRequirementList.size()+1];
		int i;
		for(i = 0; i < currentRequirementList.size(); i++) {
			currentRequirementStrings[i] = currentRequirementList.get(i).getFormula();
		}
		currentRequirementStrings[i] = guarantees;
		System.out.println("toProve ist: "+toCheck);
		boolean result = true;
		if(toCheck.length() > 0) {
			result = prover.prove(toCheck, guarantees);
		}
		return result&prover.check(currentRequirementStrings);
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
					List<Requirement> requirementList;
					
					if(ctx.boolexpression() != null) {
						requirementList = new ArrayList<>();
						requirementList.add(new Requirement(ctx.boolexpression().getText()));
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
			System.out.println(list1);
			List<List<Requirement>> DNFs = new ArrayList<>();
			DNFs.add(list1);
			DNFs.add(list2);
			System.out.println("Möflichkeiten: "+getAllPossibleCombinations(DNFs).toString());
		}
		
		/*
		 * retrieve all possible combinations of requirements from the given DNFs
		 */
		private static List<List<Requirement>> getAllPossibleCombinations(List<List<Requirement>> DNFLists){
			List<List<Requirement>> toReturn = new ArrayList<>();
			if(DNFLists.isEmpty()) {
				return toReturn;
			} else if(DNFLists.size() == 1) {
				for(Requirement r : DNFLists.remove(0)) {
					List<Requirement> newList = new ArrayList<>();
					newList.add(r);
					toReturn.add(newList);
				}
				return toReturn;
			} 
			List<Requirement> reqs = DNFLists.remove(0);
			List<List<Requirement>> otherLists = getAllPossibleCombinations(DNFLists);
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
}
