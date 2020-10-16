package de.tubs.skeditor.synthesis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;

import SkillGraph.Category;
import SkillGraph.Edge;
import SkillGraph.Node;
import SkillGraph.SkillGraphFactory;
import de.tubs.skeditor.contracting.Contract;
import de.tubs.skeditor.contracting.ContractPropagator;
import de.tubs.skeditor.synthesis.prover.TermProver;
import de.tubs.skeditor.synthesis.search.FilterFormatException;
import de.tubs.skeditor.synthesis.search.SkillSearch;
import de.tubs.skeditor.utils.SynthesisUtil;

public class RequirementSkillProvider extends SkillProvider {

	private Requirement requirement;
	private TermProver prover;
	
	public RequirementSkillProvider(Requirement req) {
		super();
		this.requirement = req;
		this.prover = new TermProver();
		addNodes(depth);
	}
	
	
	/*
	 * adds nodes with a given depth to a set and adds that set to nodeMap
	 */
	@Override
	protected void addNodes(int depth) {
		List<Node> nodeList = new ArrayList<>();
		if(depth == 1) {
			String searchString = "";
			for (String var : requirement.getVariables()) {
				if(searchString.length() == 0) {
					searchString = "provided=\""+var;
				} else {
					searchString += ","+var;
				}
				
			}
			if(searchString.length() > 0) {
				searchString += "\"";
			}
			System.out.println("Searchstring depth"+depth+" "+searchString);
			try {
				
				for(Node n : searcher.searchSkills(searchString)) {
					String[] safetyGoals = new String[n.getRequirements().size()];
					for(int i = 0; i < n.getRequirements().size(); i++) {
						safetyGoals[i] = n.getRequirements().get(i).getTerm();
					}
					//prove if safety goals of node n satisfies requirement
					if(prover.prove(requirement.getFormula(), safetyGoals)) {
						nodeList.add(EcoreUtil.copy(n));
					}
					
				}
				
			} catch (FilterFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			nodeMap.put(1, nodeList);
		} else { // add other nodes to existing nodes of previous depth if available
			
			for(Node node : nodeMap.get(depth-1)) {
				List<String> providedVars = new ArrayList<>();
				providedVars.addAll(node.getProvidedVariables());
				providedVars.addAll(node.getRequiredVariables());
				//providedVars.removeAll(requirement.getVariables());
				String searchString = "";
				for (String var : providedVars) {
					if(searchString.length() == 0) {
						searchString = "required=\""+var+"\"";
					} else {
						searchString += "|required=\""+var+"\"";
					}
					
				}
				System.out.println("SUCHE REQUIREMENT: "+searchString);
				try {
					if(searchString.length() > 0) {
						for(Node n : searcher.searchSkills(searchString)) {
							if(SynthesisUtil.canCreateEdge(n, node)) {
								Node temp = node;
								boolean categoryExists = false;
								//check if node already exists that has same category
								if(!n.getCategory().equals(temp.getCategory())) {
									while(!temp.getChildEdges().isEmpty()) {
										temp = temp.getChildEdges().get(0).getChildNode(); //has only one child
										if(temp.getCategory().equals(n.getCategory())) {
											categoryExists = true;
											break;
										}
									}
								} else {
									categoryExists = true;
								}
								if(!categoryExists) {
									Node parent = EcoreUtil.copy(n);
									Node child = EcoreUtil.copy(node);
									Edge e = SkillGraphFactory.eINSTANCE.createEdge();
									e.setChildNode(child);
									e.setParentNode(parent);
									parent.getChildEdges().add(e);
									child.getParentNodes().add(parent);
									//check if new node guarantees requirement
									Contract contract = ContractPropagator.computeContract(parent);
									List<String> guarantees = Arrays.asList(contract.getGuarantee().split("&"));
									/*for(int i = 0; i < guarantees.size(); i++) {
										if(guarantees.get(i).equals("true")) { //we dont want true word in assumptions for prover
											guarantees.remove(i);
										}
									}*/
									if(prover.prove(requirement.getFormula(), (String[])guarantees.toArray())) {
										nodeList.add(parent);
									}
									
								}
							}
						}
					} 
				} catch (FilterFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			nodeMap.put(depth, nodeList);
		}
		
	}
}
