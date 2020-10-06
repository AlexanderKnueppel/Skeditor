package de.tubs.skeditor.synthesis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;

import SkillGraph.Edge;
import SkillGraph.Node;
import SkillGraph.SkillGraphFactory;
import de.tubs.skeditor.synthesis.search.FilterFormatException;
import de.tubs.skeditor.utils.SynthesisUtil;

public class VariableSkillProvider extends SkillProvider {

	private String[] requiredVars;
	private String[] forbiddenVars;
	public VariableSkillProvider(String[] requiredVars, String[] forbiddenVars) {
		super();
		this.requiredVars = requiredVars;
		this.forbiddenVars = forbiddenVars;
		addNodes(1);
	}
	
	@Override 
	protected void addNodes(int depth) {
		List<Node> nodeList = new ArrayList<>();
		if(depth == 1) {
			String searchString = "";
			for (String var : requiredVars) {
				if(searchString.length() == 0) {
					searchString = "defined=\""+var;
				} else {
					searchString += ","+var;
				}
				
			}
			if(searchString.length() > 0) {
				searchString += "\"";
				System.out.println("Searchstring depth"+depth+" "+searchString);
				try {
					boolean forbidden = false; //flag that indicates if var is forbidden
					for(Node n : searcher.searchSkills(searchString)) {
						for (String forb : forbiddenVars) {
							if(n.getRequiredVariables().contains(forb)) { //dependency requires var that is defined by parent
								forbidden = true;
								break;
							}
						}
						if (! forbidden) {
							nodeList.add(EcoreUtil.copy(n));
						} else {
							forbidden = false; //reset flag 
						}
						
					}
					
				} catch (FilterFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nodeMap.put(1, nodeList);
			}
		} else { // add other nodes to existing nodes of previous depth if available
			
			for(Node node : nodeMap.get(depth-1)) {
				List<String> providedVars = new ArrayList<>();
				providedVars.addAll(node.getProvidedVariables());
				//providedVars.addAll(node.getRequiredVariables());
				String searchString = "";
				for (String var : requiredVars) { //remove all 
					providedVars.remove(var);
				}
				for (String var : providedVars) {
					if(searchString.length() == 0) {
						searchString = "required=\""+var+"\"";
					} else {
						searchString += "|required=\""+var+"\"";
					}
					
				}
				if(requiredVars.length > 0) {
					if(searchString.length() == 0) {
						searchString = "!(";
					} else {
						searchString += "&!(";
					}
					searchString += "required=\""+requiredVars[0]+"\"";
					if(requiredVars.length > 1) {
						for (int i = 1; i < requiredVars.length; i++) { //remove all 
							searchString += "|required=\""+requiredVars[0]+"\"";
						}
					}
					searchString += ")";
				}
				
				//System.out.println("SUCHE "+searchString);
				try {
					boolean forbidden = false;
					for(Node n : searcher.searchSkills(searchString)) {
						for (String forb : forbiddenVars) {
							if(n.getRequiredVariables().contains(forb)) { //dependency requires var that is defined by parent
								forbidden = true;
								break;
							}
						}
						if (! forbidden) {
							if(SynthesisUtil.canCreateEdge(n, node)) {
								Node temp = node;
								//System.out.println(temp);
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
									Edge e = SynthesisUtil.createEdge(parent, child);
									nodeList.add(parent);
								}
							}
							
						} else {
							forbidden = false; //reset flag 
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
