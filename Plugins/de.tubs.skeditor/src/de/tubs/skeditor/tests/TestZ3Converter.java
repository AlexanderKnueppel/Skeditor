package de.tubs.skeditor.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.microsoft.z3.ApplyResult;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Goal;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;
import com.microsoft.z3.Tactic;

import de.tubs.skeditor.contracting.grammar.SyntaxErrorListener;
import de.tubs.skeditor.contracting.grammar.Z3Converter;

public class TestZ3Converter {

	@Test
	public void testKnownFunctionRecognition() {
		Z3Converter converter = new Z3Converter();

		List<String> formulas = new ArrayList<>();

		// cos, sin, tan, abs (empty, parameter list length > 1)
		// formulasWithSyntaxErrors.add("\\abs() + \\sin(a+3, a, 3, x^2) >
		// a*b*\\tan()^\\cos(x,y)");

		// min, max (empty or parameter list length = 1)
		// formulasWithSyntaxErrors.add("\\min() + \\max() > \\min(-a)");

		// Test min
		formulas.add("\\min(3.5, 1.0 + 2.0) = 3.0");
		formulas.add("\\min(0, 0) < 1");

		// Test max
		formulas.add("\\max(3.5 - 2.0, 1.0 + 2.0) > 1.0");
		formulas.add("\\max(-5.9, -2.0) = -2.0");

		// Test abs
		formulas.add("\\abs(-3.0 + 1.0) = 2.0");
		formulas.add("\\abs(((-3.0 + 1.0)*-5.87) / 2) < 6.0");
		formulas.add("\\abs(1) = 1");

		formulas.forEach(formula -> {
			assertTrue(converter.simplifyFormula(formula).equals(""));
		});

		// Test wrong number of parameters -> Syntax Error
		List<String> formulasWithSyntaxErrors = new ArrayList<>();
		formulasWithSyntaxErrors.add("\\min(1) = 1");
		formulasWithSyntaxErrors.add("\\min(2, 3, 4) = 4+4");
		
		formulasWithSyntaxErrors.add("\\max() >= 1");
		formulasWithSyntaxErrors.add("\\max(a, c, d) != -4.9");
		
		formulasWithSyntaxErrors.add("\\abs(a, b) < 10");
		formulasWithSyntaxErrors.add("\\abs() <= a+b+c");


		formulasWithSyntaxErrors.forEach(formula -> {
			String result = converter.simplifyFormula(formula);
			assertNull(result);
			assertFalse(SyntaxErrorListener.INSTANCE.getSyntaxErrors().isEmpty());
		});

	}

	// @Test
	public void testFormulaConversion() {
		// String formula = "(A==true||B==true)||A==true";

		String formula = "!(y <= (ly+((-1.0)*(0.5*lw))))";

		Z3Converter converter = new Z3Converter();

		Goal goal = converter.getContext().mkGoal(true, false, false);
		goal.add(converter.getZ3BoolExpression(formula));

		Tactic simplify = converter.getContext().mkTactic("simplify");
		Tactic ctxSimplify = converter.getContext().mkTactic("ctx-simplify");
		Tactic ctxSolverSimplify = converter.getContext().mkTactic("ctx-solver-simplify");

		ApplyResult res1 = simplify.apply(goal);
		ApplyResult res2 = ctxSimplify.apply(goal);
		ApplyResult res3 = ctxSolverSimplify.apply(goal);

		System.out.println("original term:\n" + converter.getZ3BoolExpression(formula) + "\n");
		System.out.println("simplify:\n" + res1 + "\n");
		System.out.println("ctxSimplify:\n" + res2 + "\n");
		System.out.println("ctxSolverSimplify:\n" + res3 + "\n");

		Solver s = converter.getZ3SolverFromFormula(formula);
		for (Goal sub : res3.getSubgoals()) {
			s.add(sub.getFormulas());

			// System.out.println(sub);
		}
		if (s.check() == Status.SATISFIABLE)
			System.out.println("Model: " + s.getModel().toString());
		else
			System.out.println("Not satisfiable!");

		BoolExpr be = converter.getZ3BoolExpression(formula);

		for (BoolExpr sub : s.getAssertions()) {
			System.out.println(converter.toFormulaFromZ3Expression(sub));
		}

		converter.getContext().close();
	}

}
