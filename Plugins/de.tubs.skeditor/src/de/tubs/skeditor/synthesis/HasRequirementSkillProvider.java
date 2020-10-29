package de.tubs.skeditor.synthesis;

import java.util.ArrayList;
import java.util.List;

import SkillGraph.Node;
import de.tubs.skeditor.synthesis.search.FilterFormatException;
import de.tubs.skeditor.utils.SynthesisUtil;

public class HasRequirementSkillProvider extends RequirementSkillProvider {

	public HasRequirementSkillProvider(Requirement req) {
		super(req);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void addNodes(int depth) {
		List<Node> nodeList = new ArrayList<>();
		if(depth == 1) {
			
			String skillname = requirement.getFormula().split("\"", 3)[1];
			System.out.println("Skillname:"+skillname);
			String searchString = "name=\""+skillname.replace("_", " ")+"\"";
			System.out.println("Searchstring depth"+depth+" "+searchString);
			if(skillname.length() > 0) {
				try {
					for(Node n : searcher.searchSkills(searchString)) {
						nodeList.add(SynthesisUtil.copyNodeWithChildren(n));
					}
					
				} catch (FilterFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
				//System.out.println("SUCHE HAS REQUIREMENT: "+searchString);
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
									nodeList.add(parent);
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
			nodeMap.remove(depth-1);
		}
	}
}
