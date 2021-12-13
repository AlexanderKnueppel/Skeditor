package de.tubs.skeditor.keymaera.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.tubs.skeditor.keymaera.generator.evaluator.Visitor;
import de.tubs.skeditor.sdl.Assert;
import de.tubs.skeditor.sdl.Assignment;
import de.tubs.skeditor.sdl.Assume;
import de.tubs.skeditor.sdl.Dynamics;
import de.tubs.skeditor.sdl.Expression;
import de.tubs.skeditor.sdl.Formula;
import de.tubs.skeditor.sdl.Havoc;
import de.tubs.skeditor.sdl.Hoare;
import de.tubs.skeditor.sdl.IfThenElse;
import de.tubs.skeditor.sdl.Invoke;
import de.tubs.skeditor.sdl.ODE;
import de.tubs.skeditor.sdl.SdlFactory;
import de.tubs.skeditor.sdl.SkipStatement;
import de.tubs.skeditor.sdl.Statement;
import de.tubs.skeditor.sdl.Variable;

public class SDLTranslator {
	public final static String ASSERT_ID = "assert";

	public static String translate(Dynamics dynamics) {
		List<String> odes = new ArrayList<String>();
		for (ODE ode : dynamics.getOde()) {
			// odes.add(ode.getVariable() + "=" +
			// transformExpressionPast(ode.getExpression()));
			odes.add(ode.getVariable() + "=" + transformExpression(ode.getExpression()));
		}
		odes.add("t'=1");

		String evoCond = transformFormula(dynamics.getEvolution());
		if (evoCond.equals("true"))
			evoCond = "t <= ep";
		else
			evoCond += " & t <= ep";
		return "{" + String.join(",", odes) + " & " + evoCond + "}";
	}

	public static String translate(List<Statement> stmts) {
		return String.join(";", stmts.stream().map(s -> SDLTranslator.translate(s)).collect(Collectors.toList())) + ";";
	}

	public static String translate(Statement statement) {
		if (statement instanceof Assignment) {
			return translateAssignment((Assignment) statement);
		} else if (statement instanceof Havoc) {
			return translateHavoc((Havoc) statement);
		} else if (statement instanceof Assume) {
			return translateAssume((Assume) statement);
		} else if (statement instanceof SkipStatement) {
			return "?true";
		} else if (statement instanceof IfThenElse) {
			return translateIfThenElse((IfThenElse) statement);
		} else if (statement instanceof Assert) {
			return translateAssert((Assert) statement);
		} else if (statement instanceof Invoke) {
			return translateInvoke((Invoke) statement);
		} else if (statement instanceof Hoare) {
			return translateHoare((Hoare) statement);
		}
		return "error";
	}

	private static String translateHoare(Hoare statement) {
		// TODO Auto-generated method stub
		Assert a = SdlFactory.eINSTANCE.createAssert();
		a.setFormula(statement.getPreconditon());
		Assume b = SdlFactory.eINSTANCE.createAssume();
		b.setFormula(statement.getPostcondition());
		Havoc c = SdlFactory.eINSTANCE.createHavoc();
		c.getVariables().addAll(statement.getFramevars());

		return translateAssert(a) + "; " + translateHavoc(c) + "; " + translateAssume(b);
	}

	private static String translateInvoke(Invoke statement) {
		// TODO Auto-generated method stub
		return statement.getCall().getName();
	}

	private static String translateAssert(Assert statement) {
		// TODO Auto-generated method stub
		return "{?!(" + transformFormula(statement.getFormula()) + "); " + ASSERT_ID + " := 1; ++ ?"
				+ transformFormula(statement.getFormula()) + ";}";
	}

	private static String translateIfThenElse(IfThenElse statement) {
		// TODO Auto-generated method stub
		return "{?" + transformFormula(statement.getExpression()) + "; " + translate(statement.getIfcase().getStmt())
				+ " ++ ?!(" + transformFormula(statement.getExpression()) + "); "
				+ translate(statement.getIfcase().getStmt()) + "}";
	}

	private static String translateAssume(Assume statement) {
		// TODO Auto-generated method stub
		return "?" + transformFormula(statement.getFormula());
	}

	private static String translateHavoc(Havoc statement) {
		// TODO Auto-generated method stub
		List<String> parts = new ArrayList<String>();
		for (Variable v : statement.getVariables()) {
			parts.add(v.getName() + " := *");
		}
		return String.join(";", parts);
	}

	private static String translateAssignment(Assignment statement) {
		// TODO Auto-generated method stub
		return statement.getVariable().getName() + " := " + transformExpressionPast(statement.getExpr());
	}

	private static String transformExpression(Expression expr) {
		Visitor visitor = new Visitor();
		return visitor.doSwitch(expr);
	}

	private static String transformExpressionPast(Expression expr) {
		Visitor visitor = new Visitor();
		visitor.enablePast(true);
		return visitor.doSwitch(expr);
	}

	private static String transformFormula(Formula formula) {
		Visitor visitor = new Visitor();
		return visitor.doSwitch(formula);
	}
}
