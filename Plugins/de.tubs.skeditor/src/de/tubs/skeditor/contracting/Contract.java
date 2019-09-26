package de.tubs.skeditor.contracting;

public class Contract {
	private String assumption;
	private String guarantee;
	
	public Contract(String assumption, String guarantee) {
		super();
		this.assumption = assumption;
		this.guarantee = guarantee;
	}
	
	public String getAssumption() {
		return assumption;
	}
	public void setAssumption(String assumption) {
		this.assumption = assumption;
	}
	public String getGuarantee() {
		return guarantee;
	}
	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}
	
	@Override
	public String toString() {
		return "Contract [assumption=" + assumption + ", guarantee=" + guarantee + "]";
	}
}
