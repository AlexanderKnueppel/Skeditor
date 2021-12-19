package de.tubs.skeditor.keymaera.generator.evaluator;

import de.tubs.skeditor.sdl.Abs;
import de.tubs.skeditor.sdl.Exists;
import de.tubs.skeditor.sdl.Forall;
import de.tubs.skeditor.sdl.FunctionReference;
import de.tubs.skeditor.sdl.Max;
import de.tubs.skeditor.sdl.Min;
import de.tubs.skeditor.sdl.Past;

//   Past | Min | Max | Forall | Exists 

public class FunctionRefEvaluator implements Evaluator<FunctionReference> {
	private Visitor visitor;

	public FunctionRefEvaluator(Visitor visitor) {
		this.visitor = visitor;
	}

	public String evaluate(FunctionReference expression) {
		if (expression.getRef() instanceof Past) {
			return pastFunction((Past) expression.getRef());
		} else if (expression.getRef() instanceof Min) {
			return minFunction((Min) expression.getRef());
		} else if (expression.getRef() instanceof Max) {
			return maxFunction((Max) expression.getRef());
		} else if (expression.getRef() instanceof Abs) {
			return absFunction((Abs) expression.getRef());
		} 
		else if (expression.getRef() instanceof Forall) {
			return forallFunction((Forall) expression.getRef());
		} else if (expression.getRef() instanceof Exists) {
			return existsFunction((Exists) expression.getRef());
		}

		return "";
	}

	private String existsFunction(Exists ref) {
		// TODO Auto-generated method stub
		String var = ref.getVariable().getName();
		String formula = visitor.doSwitch(ref.getFormula());
		return "\\exists " + var + " " + formula;
	}

	private String forallFunction(Forall ref) {
		// TODO Auto-generated method stub
		String var = ref.getVariable().getName();
		String formula = visitor.doSwitch(ref.getFormula());
		return "\\forall " + var + " " + formula;
	}

	private String maxFunction(Max ref) {
		// TODO Auto-generated method stub
		return "max(" + visitor.doSwitch(ref.getExp1()) + "," + visitor.doSwitch(ref.getExp2()) + ")";
	}
	
	private String absFunction(Abs abs) {
		// TODO Auto-generated method stub
		return "abs(" + visitor.doSwitch(abs.getExp1()) + ")";
	}

	private String minFunction(Min ref) {
		// TODO Auto-generated method stub
		return "min(" + visitor.doSwitch(ref.getExp1()) + "," + visitor.doSwitch(ref.getExp2()) + ")";
	}

	private String pastFunction(Past ref) {
		// TODO Auto-generated method stub
		return ref.getVariable() + "past";
	}

}
