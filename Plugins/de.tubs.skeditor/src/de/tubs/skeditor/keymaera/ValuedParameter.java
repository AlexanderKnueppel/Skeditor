package de.tubs.skeditor.keymaera;

public class ValuedParameter {
	private String name;
	private String unit;
	private String value;
	private String lowerLimit;
	private String upperLimit;
	private boolean isVar;

	public ValuedParameter(String name, String value, String unit) { // VALUE ONLY CONST
		this.name = name;
		this.value = value;
		this.unit = unit;
	}

	public ValuedParameter(String name, String lowerLimit, String unit, String upperLimit) { // LIMIT ONLY CONST
		this.name = name;
		this.lowerLimit = lowerLimit;
		this.unit = unit;
		this.upperLimit = upperLimit;
	}

	public ValuedParameter(String name, String value, String unit, boolean isVar) { // VALUE ONLY VAR
		this.name = name;
		this.value = value;
		this.unit = unit;
		this.isVar = isVar;
	}

	public ValuedParameter(String name, String lowerLimit, String unit, String upperLimit, boolean isVar) { // LIMIT VAR
		this.name = name;
		this.lowerLimit = lowerLimit;
		this.unit = unit;
		this.upperLimit = upperLimit;
		this.isVar = isVar;
	}

	public ValuedParameter(String name, String lowerLimit, String unit, String upperLimit, boolean isVar, String value) { // LIMIT + VALUE VAR
		this.name = name;
		this.lowerLimit = lowerLimit;
		this.unit = unit;
		this.upperLimit = upperLimit;
		this.isVar = isVar;
		this.value = value;
	}

	public String declaration() {
		return "R " + name + "().";
	}

	@Override
	public String toString() {
		String output = "";
		String and = "";
		String nameReplacement = name;
		if(!isVar) {
			nameReplacement += "()";
		}
		if (value != null) {
			output += nameReplacement + " = " + value;
			and = " & ";
		}
		if (upperLimit != null) {
			output += and + nameReplacement + " >=" + lowerLimit + " & " + nameReplacement + " <=" + upperLimit;
		}
		return output;
	}

	public String getName() {
		return name;
	}

	public String getUnit() {
		return unit;
	}

	public String getValue() {
		return value;
	}

	public String getUpperLimit() {
		return upperLimit;
	}

	public String getLowerLimit() {
		return lowerLimit;
	}

	public boolean isVar() {
		return isVar;
	}

}
