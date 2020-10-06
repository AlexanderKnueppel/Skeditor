package de.tubs.skeditor.synthesis;

import SkillGraph.Edge;
import SkillGraph.Node;

/**
 * Class for Synthesis to remember inserted skills and the state of the provider to fulfill dependency of given skill
 * 
 * @author Christopher
 *
 */
public class DependencyInsert extends SkillInsert {

	private final Node node;
	private final String variable;
	private String[] satisfiedVariables;
	
	public DependencyInsert(Node node, String variable, String[] satisfiedVariables, Node[] insertedSkills, Edge[] insertedEdges, SkillProvider provider) {
		super(insertedSkills, insertedEdges, provider);
		this.node = node;
		this.variable = variable;
		this.satisfiedVariables = satisfiedVariables;
	}
	
	/**
	 * Returns the skill that has the dependency
	 * 
	 * @return the node with dependency
	 */
	public Node getNode() {
		return this.node;
	}
	
	/**
	 * Returns the variable of that dependency
	 * 
	 * @return the variable 
	 */
	public String getVariable() {
		return this.variable;
	}
	
	public void setSatisfiedVariables(String[] variables) {
		this.satisfiedVariables = variables;
	}
	
	public String[] getSatisfiedVariables() {
		return this.satisfiedVariables;
	}
}
