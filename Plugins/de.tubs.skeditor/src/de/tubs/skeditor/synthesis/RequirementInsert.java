package de.tubs.skeditor.synthesis;

import java.util.List;

import SkillGraph.Edge;
import SkillGraph.Node;

public class RequirementInsert extends SkillInsert {

	private final Requirement req;

	
	public RequirementInsert(List<Node> insertedNodes, List<Edge> insertedEdges, Requirement req, SkillProvider provider) {
		super(insertedNodes, insertedEdges, provider);
		this.req = req;
	}
	
	public Requirement getRequirement() {
		return this.req;
	}
	
	@Override
	public String toString() {
		String s = super.toString();
		s += "requirement: "+req;
		return s;
	}
	
}
