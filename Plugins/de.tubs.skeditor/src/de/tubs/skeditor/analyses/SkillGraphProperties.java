package de.tubs.skeditor.analyses;

public class SkillGraphProperties {
	
	public enum SkillGraphStatus {
		WELL_FORMED,  // No contradiction in contracts
		VALID,        // WELL_FORMED + all skills proven correct
		INVALID,      // At least on skill not proven correct; unfulfilleed requirements
		ANOMALIES     // Odd/unnecessary modelling or specification
	}
	
	private SkillGraphStatus skillGraphStatus = SkillGraphStatus.VALID;
	
	public void setStatus(SkillGraphStatus skillGraphStatus) {
		this.skillGraphStatus = skillGraphStatus;
	}
	
	public boolean hasStatus(SkillGraphStatus status) {
		return skillGraphStatus == status;
	}
	
	
}
