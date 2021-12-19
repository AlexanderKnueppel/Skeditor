package de.tubs.skeditor.keymaera.generator.evaluator;

import java.util.Arrays;
import java.util.List;

import de.tubs.skeditor.sdl.Comparison;
import de.tubs.skeditor.sdl.Equals;

public class EqualsEvaluator implements Evaluator<Comparison> {

	private static List<String> EQUEALS = Arrays.asList("==", "is");
	private static List<String> NEQUALS = Arrays.asList("!=", "!~");

	private Visitor visitor;

	public EqualsEvaluator(Visitor visitor) {
		this.visitor = visitor;
	}

	public String evaluate(Equals expression) {
		String left = visitor.doSwitch(expression.getLeft());
		String right = visitor.doSwitch(expression.getRight());

		String op = expression.getOp();

		if (EQUEALS.contains(op)) {
			op = " = ";
		} else if (NEQUALS.contains(op)) {
			op = " != ";
		} else {
			return String.format("Operator unknown: {%s}", op.toString());
		}

		return left + op + right;
	}
}
