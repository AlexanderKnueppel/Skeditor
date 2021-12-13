package de.tubs.skeditor.keymaera.generator.evaluator;

import java.util.Arrays;
import java.util.List;

import de.tubs.skeditor.sdl.Comparison;
import de.tubs.skeditor.sdl.MultiOrDivOrMod;

public class MultiOrDivOrModEvaluator implements Evaluator<MultiOrDivOrMod> {

	private static List<String> MULT = Arrays.asList("*");
	private static List<String> DIV = Arrays.asList("/");
	private static List<String> MOD = Arrays.asList("%");

	private Visitor visitor;

	public MultiOrDivOrModEvaluator(Visitor visitor) {
		this.visitor = visitor;
	}
	
	public String evaluate(MultiOrDivOrMod expression) {
		String left = visitor.doSwitch(expression.getLeft());
		String right = visitor.doSwitch(expression.getRight());

		String op = expression.getOp();

		if (MULT.contains(op)) {
			op = " * ";
		} else if (DIV.contains(op)) {
			op = " / ";
		} else if (MOD.contains(op)) {
			op = " % ";
		} else {
			return String.format("Operator unknown: {%s}", op.toString());
		}

		return left + op + right;
	}
}
