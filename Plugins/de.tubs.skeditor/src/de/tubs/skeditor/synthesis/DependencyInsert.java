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

	private final String variable;

	public DependencyInsert(String variable, List<Node> insertedSkills, List<Edge> insertedEdges) {
		super(insertedSkills, insertedEdges);
		this.variable = variable;
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
		s += "variable: "+variable;
		return s;
	}
	
}
