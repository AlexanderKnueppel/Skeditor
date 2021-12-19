package de.tubs.skeditor.keymaera.generator.evaluator;

import java.util.HashSet;
import java.util.Set;

import de.tubs.skeditor.sdl.ArithmeticSigned;
import de.tubs.skeditor.sdl.BooleanExpression;
import de.tubs.skeditor.sdl.BooleanLiteral;
import de.tubs.skeditor.sdl.BooleanNegation;
import de.tubs.skeditor.sdl.Comparison;
import de.tubs.skeditor.sdl.Equals;
import de.tubs.skeditor.sdl.Expression;
import de.tubs.skeditor.sdl.Formula;
import de.tubs.skeditor.sdl.FunctionReference;
import de.tubs.skeditor.sdl.Minus;
import de.tubs.skeditor.sdl.MultiOrDivOrMod;
import de.tubs.skeditor.sdl.NullLiteral;
import de.tubs.skeditor.sdl.NumberLiteral;
import de.tubs.skeditor.sdl.ParenthesizedExpression;
import de.tubs.skeditor.sdl.Plus;
import de.tubs.skeditor.sdl.Power;
import de.tubs.skeditor.sdl.StringLiteral;
import de.tubs.skeditor.sdl.VariableLiteral;
import de.tubs.skeditor.sdl.VariableReference;
import de.tubs.skeditor.sdl.util.SdlSwitch;

public class Visitor extends SdlSwitch<String>{
	private boolean past = false;
	private Set<String> whitelistPast = new HashSet<String>();
	
	public Set<String> getPastWhitelist() {
		return whitelistPast;
	}
	
	/* Defaults */
	public String caseFormula(Formula object) {
		if(object == null)
			return "true";
	    return doSwitch(object);
	}
	public String caseExpression(Expression object) {
		if(object == null)
			return "";
	    return doSwitch(object);
	}
	
	/* Formulas */
	public String caseBooleanExpression(BooleanExpression object) {
		return new BooleanExpressionEvaluator(this).evaluate(object);
	}
	
	public String caseComparison(Comparison object) {
		return new ComparisonEvaluator(this).evaluate(object);
	}
	
	public String caseEquals(Equals object) {
		return new EqualsEvaluator(this).evaluate(object);
	}
	
	/* Expressions */
	
	public String casePlus(Plus object) {
		return new PlusEvaluator(this).evaluate(object);
	}
	
	public String caseMinus(Minus object) {
		return new MinusEvaluator(this).evaluate(object);
	}
	
	public String caseMultiOrDivOrMod(MultiOrDivOrMod object) {
		return new MultiOrDivOrModEvaluator(this).evaluate(object);
	}
	
	public String casePower(Power object) {
		return new PowerEvaluator(this).evaluate(object);
	}
	
	public String caseBooleanNegation(BooleanNegation object) {
		return "!" + doSwitch(object.getExpression());
	}
	
	public String caseArithmeticSigned(ArithmeticSigned object) {
		return "-" + doSwitch(object.getExpression());
	}
	
	public String caseVariableReference(VariableReference object) {
		return object.getRef().getName();
	}
	
	public String caseFunctionReference(FunctionReference object) {
		return new FunctionRefEvaluator(this).evaluate(object);
	}
	
	public String caseNumberLiteral(NumberLiteral object) {
		return object.getValue() + "";
	}
	
	public String caseVariableLiteral(VariableLiteral object) {
		if(past) {
			if(!whitelistPast.contains(object)) {
				return object.getValue() + "past";
			}
		}
		return object.getValue();
	}
	
	public String caseBooleanLiteral(BooleanLiteral object) {
		return object.getValue();
	}
	
	public String caseStringLiteral(StringLiteral object) {
		return object.getValue();
	}
	
	public String caseNullLiteral(NullLiteral object) {
		return object.getValue();
	}
	
	public String caseParenthesizedExpression(ParenthesizedExpression object) {
		return "(" + doSwitch(object.getExpr()) + ")";
	}
	public void enablePast(boolean b) {
		past  = b;
	}
}
