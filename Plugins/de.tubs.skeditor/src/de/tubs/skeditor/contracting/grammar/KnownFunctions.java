package de.tubs.skeditor.contracting.grammar;

public enum KnownFunctions {
	
	sin(1), cos(1), tan(1), min(2), max(2), abs(1);
	
	private int numOfParameters;

	KnownFunctions(int i) {
		this.numOfParameters = i;
	}

	public int getNumOfParameters() {
		return numOfParameters;
	}
	
}
