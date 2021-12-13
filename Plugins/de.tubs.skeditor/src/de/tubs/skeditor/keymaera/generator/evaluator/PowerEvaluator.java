package de.tubs.skeditor.keymaera.generator.evaluator;

import de.tubs.skeditor.sdl.Plus;
import de.tubs.skeditor.sdl.Power;

public class PowerEvaluator implements Evaluator<Power> {
	private Visitor visitor;

	public PowerEvaluator(Visitor visitor) {
		this.visitor = visitor;
	}

	public String evaluate(Power expression) {
		String left = visitor.doSwitch(expression.getLeft());
		String right = visitor.doSwitch(expression.getRight());

		return "(" + left + ")^(" + right + ")";
	}
}
