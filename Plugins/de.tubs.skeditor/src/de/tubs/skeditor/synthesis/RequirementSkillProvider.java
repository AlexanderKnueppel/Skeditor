package de.tubs.skeditor.synthesis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import SkillGraph.Node;
import de.tubs.skeditor.contracting.Contract;
import de.tubs.skeditor.contracting.ContractPropagator;
import de.tubs.skeditor.synthesis.prover.TermProver;
import de.tubs.skeditor.synthesis.search.FilterFormatException;
import de.tubs.skeditor.utils.SynthesisUtil;

public class RequirementSkillProvider extends SkillProvider {

	protected Requirement requirement;
	private TermProver prover;
	
	public RequirementSkillProvider(Requirement req) {
		super();
		this.requirement = req;
		this.prover = new TermProver();
		addNodes(1);
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
			//System.out.println("Searchstring depth"+depth+" "+searchString);
			try {
				
				for(Node n : searcher.searchSkills(searchString)) {
					//System.out.println("skill con search: ");SynthesisUtil.childsToString(n);
					String[] safetyGoals = new String[n.getRequirements().size()];
					for(int i = 0; i < n.getRequirements().size(); i++) {
						safetyGoals[i] = n.getRequirements().get(i).getTerm();
					}
					//prove if safety goals of node n satisfies requirement
					if(prover.prove(requirement.getFormula(), safetyGoals)) {
						nodeList.add(SynthesisUtil.copyNode(n));
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
				providedVars.addAll(node.getDefinedVariables());
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
				//System.out.println("SUCHE REQUIREMENT: "+searchString);
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
									Node parent = SynthesisUtil.copyNodeWithChildren(n);
									Node child = SynthesisUtil.copyNodeWithChildren(node);
									SynthesisUtil.createEdge(parent, child);
									
									//check if new node guarantees requirement
									Contract contract = ContractPropagator.computeContract(parent);
									List<String> guarantees = Arrays.asList(contract.getGuarantee().split("&"));
									if(prover.prove(requirement.getFormula(), (String[])guarantees.toArray())) {
										nodeList.add(parent);
									}
								}
							}
						}
					} 
				} catch (FilterFormatException e) {
					// TODO Auto-generated catch block
					nodeList.clear();
				}
			}
			nodeMap.put(depth, nodeList);
			nodeMap.remove(depth-1);
		}
		
	}
	@Override
	public String toString() {
		return "Tiefe: "+depth+" Current Index="+currentIndex+" requirement:"+requirement;
	}
}
