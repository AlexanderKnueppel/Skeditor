package de.tubs.skeditor.synthesis;

import java.util.List;

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

	public DependencyInsert(int depth, SkillInsert parent, Node node, List<Node> insertedSkills, List<Edge> insertedEdges, VariableSkillProvider provider) {
		super(depth, parent, insertedSkills, insertedEdges, provider);
		this.node = node;
		this.variable = provider.getRequiredVariable();
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
	
	@Override
	public String toString() {
		String s = super.toString();
		s += "node: "+node.getName()+" variable: "+variable;
		return s;
	}
	
}
