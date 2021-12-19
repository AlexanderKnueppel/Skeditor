package de.tubs.skeditor.keymaera.generator.evaluator;

import java.util.Arrays;
import java.util.List;

import de.tubs.skeditor.sdl.Comparison;

public class ComparisonEvaluator implements Evaluator<Comparison> {

	private static List<String> LT = Arrays.asList("<");
	private static List<String> LEQ = Arrays.asList("<=");
	private static List<String> GT = Arrays.asList(">");
	private static List<String> GEQ = Arrays.asList(">=");

	private Visitor visitor;

	public ComparisonEvaluator(Visitor visitor) {
		this.visitor = visitor;
	}

	public String evaluate(Comparison expression) {
		String left = visitor.doSwitch(expression.getLeft());
		String right = visitor.doSwitch(expression.getRight());

		String op = expression.getOp();

		if (LT.contains(op)) {
			op = " < ";
		} else if (LEQ.contains(op)) {
			op = " <= ";
		} else if (GT.contains(op)) {
			op = " > ";
		} else if (GEQ.contains(op)) {
			op = " >= ";
		} else {
			return String.format("Operator unknown: {%s}", op.toString());
		}

		return left + op + right;
	}
}
