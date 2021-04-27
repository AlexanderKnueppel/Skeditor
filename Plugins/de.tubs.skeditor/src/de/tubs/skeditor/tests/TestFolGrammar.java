package de.tubs.skeditor.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.tubs.skeditor.contracting.grammar.GrammarUtil;
import de.tubs.skeditor.contracting.grammar.SyntaxError;

/**
 * JUnit tests for fol grammar
 * 
 * @author Dibo Gonda
 * 
 */
public class TestFolGrammar {

	@Test
	public void testParsing() {

		List<String> formulas = new ArrayList<>();

		// ARITHMETIC

		// simple formula (arithmetic operations)
		formulas.add("b*5 <= 8");
		formulas.add("b*5 <= ((-2))");
		formulas.add("a*b*c <= a+b+c");

		// simple formula with brackets
		formulas.add("(y-ly) + (v^2/(2*b)) < lw");
		formulas.add("(y-x) * (20*x) > 25");
		
		// scientific numbers
		formulas.add("1E42 + 4");
		formulas.add("abs(1E42)");

		// FUNCTION CALLS

		// function calls
		formulas.add("A.method(1, 12, (a+b)/5, \\abs(x-b)) + \\cos(3) + B.add(a, b)");
		formulas.add("SomeObject.someMethod(1, 12, (a+b)/5, \\abs(x-b)) ");
		formulas.add("xmethod()");
		formulas.add("sincos(1) < a");

		// known unary function calls (sin, cos, tan, abs)
		formulas.add("\\sin(x+3)^\\abs(y/3) + \\tan(\\abs(x-7)) != x*y/\\cos(x)");
		formulas.add("\\abs(-x) + \\abs(x-y+3) + \\abs(-12-a)"); // error -x

		// known binary function calls (min, max)
		formulas.add("\\max(x+3, y) = \\min(z, a+b)");
		formulas.add("\\min(1,2) + \\min(\\sin(x), \\cos(y+3)^3) > \\max(\\abs(a-b), 2)");

		// FORMULAS

		// forall exists
		formulas.add("\\forall (a;b) (a+b = 8)");
		formulas.add("\\exists (a) (\\forall (a;x) (a+b > cos(x^x)))");
		formulas.add("\\forall (a;b) (a + c > b)");

		// =>, <=, ->, <->
		formulas.add("(\\false -> #d() || #b()) | (!#a() || #b())");
		formulas.add("(#a()) -> (#y() & #x() && #z())"); // TODO fix
		formulas.add("(#a() | #b() || #c() | #d()) <-> (#a() & #b() -> #c())"); // TODO fix
		formulas.add("((#a() | #b()) & #c()) <-> \\true"); // TODO fix
		
		// LL-GRAMMAR


		// assertFormulas(testFormulas);
		formulas.forEach(formula -> {
			printErrorMessage(formula);
			Assert.assertTrue(GrammarUtil.tryToParse(formula).isEmpty());
		});

	}

	@Test
	public void testParsingWithSyntaxErrors() {
		List<String> formulasWithSyntaxErrors = new ArrayList<>();

		// typos
		formulasWithSyntaxErrors.add("(-x + 4) + 10 >");
		formulasWithSyntaxErrors.add("- <= 5");
		formulasWithSyntaxErrors.add("\\foral(a;b)(a > b)");


		// missing brackets
		formulasWithSyntaxErrors.add("abs-23+x) - (a*b*c+3)) + 3) > a+b");

		// not allowed symbols
		formulasWithSyntaxErrors.add("abs(1+x) $<> + a");

		// cos, sin, tan, abs (empty, parameter list length > 1)
		//formulasWithSyntaxErrors.add("\\abs() + \\sin(a+3, a, 3, x^2) > a*b*\\tan()^\\cos(x,y)"); // TODO recognize known methods

		// min, max (empty or parameter list length = 1)
		//formulasWithSyntaxErrors.add("\\min() + \\max() > \\min(-a)"); // TODO recognize known methods

		// assertFormulas(formulasWithSyntaxErrors);
		formulasWithSyntaxErrors.forEach(formula -> {
			printErrorMessage(formula);
			Assert.assertFalse(GrammarUtil.tryToParse(formula).isEmpty());
		});
	}

	private void printErrorMessage(String formula) { // TODO remove method
		for (SyntaxError s : GrammarUtil.tryToParse(formula)) {
			System.out.println(formula + ": " + s.getMessage());
		}
	}

}
