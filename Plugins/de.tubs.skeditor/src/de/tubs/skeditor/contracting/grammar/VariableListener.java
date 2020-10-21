package de.tubs.skeditor.contracting.grammar;

import java.util.HashSet;
import java.util.Set;

public class VariableListener extends folBaseListener {
	private Set<String> variables = new HashSet<String>();
	
	@Override public void enterVariable(folParser.VariableContext ctx) { 
		variables.add(ctx.IDENTIFIER().getText());
	}
	
	public Set<String> getVariables() {
		return variables;
	}
}
