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

	private String requiredVar;
	private List<String> forbiddenVars;
	public VariableSkillProvider(String requiredVar, List<String> forbiddenVars) {
		super();
		this.requiredVar = requiredVar;
		this.forbiddenVars = forbiddenVars;
		addNodes(1);
	}
	
	@Override 
	protected void addNodes(int depth) {
		List<Node> nodeList = new ArrayList<>();
		if(depth == 1) {
			String searchString = "defined=\""+requiredVar+"\"";
		
			//System.out.println("Searchstring depth"+depth+" "+searchString);
			try {
				boolean forbidden = false; //flag that indicates if var is forbidden
			//	System.out.println("depth "+depth+" found: ");
				for(Node n : searcher.searchSkills(searchString)) {
					//System.out.println("depth "+depth+" found: "+n.getName());
					for (String forb : forbiddenVars) {
						//System.out.println("verboten: "+forb);
						if(n.getRequiredVariables().contains(forb)) { //dependency requires var that is defined by parent
							forbidden = true;
							break;
						}
					}
					if (! forbidden) {
						nodeList.add(SynthesisUtil.copyNodeWithChildren(n));
					} else {
						forbidden = false; //reset flag 
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
				//providedVars.addAll(node.getRequiredVariables());
				String searchString = "";
				for (String var : providedVars) {
					if(searchString.length() == 0) {
						searchString = "required=\""+var+"\"";
					} else {
						searchString += "|required=\""+var+"\"";
					}
					
				}
				
				//System.out.println("SUCHE "+searchString);
				try {
					boolean forbidden = false;
					for(Node n : searcher.searchSkills(searchString)) {
						for (String forb : forbiddenVars) {
							//System.out.println("verboten: "+forb);
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
									Node parent = SynthesisUtil.copyNodeWithChildren(n);
									Node child = SynthesisUtil.copyNodeWithChildren(node);
									SynthesisUtil.createEdge(parent, child);
									nodeList.add(parent);
								}
							}
							
						} else {
							forbidden = false; //reset flag 
						}
					}
				} catch (FilterFormatException e) {
					nodeList.clear();
					//e.printStackTrace();
				}
				
			}
			nodeMap.put(depth, nodeList);
			nodeMap.remove(depth-1);
		}
	}
	
	public String getRequiredVariable() {
		return requiredVar;
	}
}
