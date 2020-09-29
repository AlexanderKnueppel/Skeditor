package de.tubs.skeditor.synthesis;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	public Set<String> getVariables(){
		Set<String> vars = new HashSet<>();
		Pattern pattern = Pattern.compile("([A-Za-z_]\\w*)");
		Matcher m = pattern.matcher(this.formula);
		while(m.find()) {
			vars.add(m.group(0));
		}
		return vars;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((formula == null) ? 0 : formula.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Requirement other = (Requirement) obj;
		if (formula == null) {
			if (other.formula != null)
				return false;
		} else if (!formula.equals(other.formula))
			return false;
		return true;
	}
	
	
	
}
