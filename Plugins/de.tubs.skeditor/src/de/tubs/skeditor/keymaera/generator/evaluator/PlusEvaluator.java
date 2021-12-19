package de.tubs.skeditor.keymaera.generator.evaluator;

import de.tubs.skeditor.sdl.Plus;

public class PlusEvaluator implements Evaluator<Plus> {
	private Visitor visitor;

	public PlusEvaluator(Visitor visitor) {
		this.visitor = visitor;
	}

	public String evaluate(Plus expression) {
		String left = visitor.doSwitch(expression.getLeft());
		String right = visitor.doSwitch(expression.getRight());

		return left + " + " + right;
	}
}