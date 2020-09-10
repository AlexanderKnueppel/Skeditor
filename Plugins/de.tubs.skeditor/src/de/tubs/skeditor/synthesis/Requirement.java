package de.tubs.skeditor.synthesis;

public class Requirement {

	private String formula;
	
	public Requirement(String formula) {
		this.formula = formula;
	}
	
	public String getFormula() {
		return this.formula;
	}
	
	public void setFormula(String formula) {
		this.formula = formula;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other instanceof Requirement) {
			Requirement otherReq = (Requirement) other;
			if(this.formula.equals(otherReq.getFormula())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int hash = 17;
		int multi = 31;
		hash = hash*multi + this.formula.hashCode()*multi;
		return hash;
	}
	
}
