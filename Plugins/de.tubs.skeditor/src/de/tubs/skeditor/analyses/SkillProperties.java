package de.tubs.skeditor.analyses;

import java.util.EnumSet;

import SkillGraph.Node;

public class SkillProperties {
	public static enum SkillStatus {
		WELL_FORMED, //satisfiable pre- and postcondition + ...?
		VALID,   // proven correct
		INVALID, // not provable
		INCOMPLETE, // unfulfilled requirements
	}
	
	private final EnumSet<SkillStatus> skillStatus = EnumSet.noneOf(SkillStatus.class);
	private final Node skill;
	
	public SkillProperties(Node skill) {
		this.skill = skill;
	}
	
	public Node getSkill() {
		return skill;
	}

	public boolean hasStatus(SkillStatus skillStatus) {
		return this.skillStatus.contains(skillStatus);
	}

	public void resetStatus() {
		skillStatus.clear();
	}
	
	public void setStatus(SkillStatus skillStatus) {
//		if (featureSelectionStatus.contains(featureStatus)) {
//			resetSelectionStatus();
//		} else if (featureParentStatus.contains(featureStatus)) {
//			resetParentStatus();
//		} else if (featureDeterminedStatus.contains(featureStatus)) {
//			resetDeterminationStatus();
//		}
		this.skillStatus.add(skillStatus);
	}
}
