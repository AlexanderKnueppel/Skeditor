package de.tubs.skeditor.synthesis;

import SkillGraph.Edge;
import SkillGraph.Node;

public class RequirementInsert extends SkillInsert {

	private final Requirement req;

	
	public RequirementInsert(Requirement req, SkillProvider provider) {
		super(null, null, provider);
		this.req = req;
	}
	
	public Requirement getRequirement() {
		return this.req;
	}
	
}
