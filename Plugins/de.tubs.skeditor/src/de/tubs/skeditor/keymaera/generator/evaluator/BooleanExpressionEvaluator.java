package de.tubs.skeditor.keymaera.generator.evaluator;

import java.util.Arrays;
import java.util.List;

import de.tubs.skeditor.sdl.BooleanExpression;

public class BooleanExpressionEvaluator implements Evaluator<BooleanExpression> {

	private static List<String> AND = Arrays.asList("&&", "and");
	private static List<String> OR = Arrays.asList("||", "or");
	private static List<String> IMP = Arrays.asList("->", "implies");
	private static List<String> BIMP = Arrays.asList("<->");

	private Visitor visitor;

	public BooleanExpressionEvaluator(Visitor visitor) {
		this.visitor = visitor;
	}

	public String evaluate(BooleanExpression expression) {
		String left,right;
		
		if(expression.getLeft()==null)
			left = "true";
		else
			left = visitor.doSwitch(expression.getLeft());
		
		if(expression.getRight()==null)
			right = "true";
		else
			right = visitor.doSwitch(expression.getRight());

		String op = expression.getOp();

		if (AND.contains(op)) {
			op = " & ";
		} else if (OR.contains(op)) {
			op = " | ";
		} else if (IMP.contains(op)) {
			op = " -> ";
		} else if (BIMP.contains(op)) {
			op = " <-> ";
		} else {
			return String.format("Operator unknown: {%s}", op.toString());
		}

		return left + op + right;
	}
}
