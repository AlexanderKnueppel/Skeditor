package de.tubs.skeditor.synthesis;

import java.util.List;

import SkillGraph.Edge;
import SkillGraph.Node;

public class RequirementInsert extends SkillInsert {

	private final Requirement req;

	
	public RequirementInsert(Requirement req, List<Node> insertedNodes, List<Edge> insertedEdges) {
		super(insertedNodes, insertedEdges);
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
