package de.tubs.skeditor.keymaera.generator.evaluator;

import de.tubs.skeditor.sdl.Minus;

public class MinusEvaluator  implements Evaluator<Minus> {
	private Visitor visitor;

	public MinusEvaluator(Visitor visitor) {
		this.visitor = visitor;
	}

	public String evaluate(Minus expression) {
		String left = visitor.doSwitch(expression.getLeft());
		String right = visitor.doSwitch(expression.getRight());
		
		return left + " - " + right;
	}
}
