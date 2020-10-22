package de.tubs.skeditor.synthesis.prover;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.FuncDecl;
import com.microsoft.z3.Pattern;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;
import com.microsoft.z3.Z3Exception;

import de.tubs.skeditor.contracting.grammar.folBaseListener;
import de.tubs.skeditor.contracting.grammar.folBaseVisitor;
import de.tubs.skeditor.contracting.grammar.folLexer;
import de.tubs.skeditor.contracting.grammar.folListener;
import de.tubs.skeditor.contracting.grammar.folParser;
import de.tubs.skeditor.contracting.grammar.folParser.ConditionContext;
import de.tubs.skeditor.contracting.grammar.folParser.FormulaContext;
import de.tubs.skeditor.contracting.grammar.folParser.TermContext;
import de.tubs.skeditor.contracting.grammar.folParser.VariableContext;
import de.tubs.skeditor.contracting.grammar.folVisitor;
import de.tubs.skeditor.utils.SynthesisUtil;

/**
 * class for proving terms
 * 
 * @author Christopher
 *
 */
public class TermProver{

	private Context ctx;
	
	public TermProver() {
		System.out.println("In TermProver ");
		ctx = new Context();
	}
	
	public Context getContext() {
		return ctx;
	}
	
	/**
	 * tries to parse the given string
	 * 
	 * @param toParse, the string to parse
	 * @return true if parsing was successful, false otherwise 
	 */
	public boolean tryParse(String toParse) {
		folLexer lexer = new folLexer(CharStreams.fromString(toParse));
		folParser parser = new folParser(new CommonTokenStream(lexer));
		ExpressionVisitor myVisitor = new ExpressionVisitor();
		try{
			Expr expr = myVisitor.visit(parser.condition());
			System.out.println("Expr tryParse"+expr);
		} catch(Z3Exception | NullPointerException e ) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * proves a term
	 * 
	 * @param toProve, the term to prove
	 * @param assumptions, assumed terms 
	 * @return true if assumptions imply toProve otherwise false 
	 */
	public boolean prove(String toProve, String... assumptions) {
		String s;
		for(String term : assumptions) {
			s = term.replace(" ", "");
			if (!SynthesisUtil.isValidRequirement(s)) {
				throw new IllegalArgumentException("Invalid format for assumption: \""+s+"\"");
			}
		}
		s = toProve.replace(" ", "");
		if(!SynthesisUtil.isValidRequirement(s)) {
			throw new IllegalArgumentException("Invalid format for term: \""+s+"\"");
		}
		Solver solver = ctx.mkSolver();
		folLexer lexer;
		folParser parser;
		BoolExpr expr;
		ExpressionVisitor myVisitor = new ExpressionVisitor();
		for (int i = 0; i < assumptions.length; i++) {
			lexer = new folLexer(CharStreams.fromString(assumptions[i]));
			parser = new folParser(new CommonTokenStream(lexer));
			expr = (BoolExpr)myVisitor.visit(parser.condition());
			System.out.println("Expr assumption: "+expr);
			solver.add(expr);
			//System.out.println("Expression: "+expressions[i]);
		}
		lexer = new folLexer(CharStreams.fromString(toProve));
		parser = new folParser(new CommonTokenStream(lexer));
		expr = (BoolExpr)myVisitor.visit(parser.condition());
		System.out.println("Expr toProve: "+expr);
		solver.add(getContext().mkNot(expr));
		Status result = solver.check();
		switch(result) {
		case SATISFIABLE:
			System.out.println("leider gilt das nicht unbedingt");
			return false;
		case UNSATISFIABLE:
			System.out.println("100% wahr");
			return true;
		default:
			System.out.println("Unbekannt");
			return false;
		}
		
	}
	
	/**
	 * checks terms for satisfiability
	 * 
	 * @param terms, the terms to check
	 * @return true if terms are satisfiable otherwise false 
	 */
	public boolean check(String... terms) {
		for(String term : terms) {
			if (!SynthesisUtil.isValidRequirement(term)) {
				throw new IllegalArgumentException("Invalid format for assumption: \""+term+"\"");
			}
		}
		Solver solver = ctx.mkSolver();
		ExpressionVisitor myVisitor = new ExpressionVisitor();
		for (int i = 0; i < terms.length; i++) {
			folLexer lexer = new folLexer(CharStreams.fromString(terms[i]));
			folParser parser = new folParser(new CommonTokenStream(lexer));
			Expr expr = myVisitor.visit(parser.condition());
			System.out.println("Expr: "+expr);
			solver.add((BoolExpr) expr);
			//System.out.println("Expression: "+expressions[i]);
		}
		if(terms.length > 0) {
			Status result = solver.check();
			switch(result) {
			case SATISFIABLE:
				System.out.println("Erf�llbar");
				return true;
			case UNSATISFIABLE:
				System.out.println("Unerf�llbar");
				return false;
			default:
				System.out.println("Unbekannt");
				return false;
			}
		}
		return false;
		
	}
	
	public static void main(String[] args) {
		TermProver prover = new TermProver();
		System.out.println("Ergebnis: "+prover.tryParse("!(!y==(z+(3.12-2*4))||z!=8) & x>4 & \\forall(x; y)(x>y&z==0) & \\true & has(\"Keep_distance_to_leading_vehicle\")"));
		System.out.println("Ergebnis: "+prover.check("x>4", "x<7", "y>7", "has(\"Accelerate\")"));
		//System.out.println("Ergebnis: "+prover.check("x>4", "x>7", "x>7&x>7"));
		//System.out.println("Ergebnis: "+prover.check("x>4", "x>7", "x>7&x>7"));
		System.out.println("Ergebnis: "+prover.prove("x<4", "x<7", "x<y", "y==4"));
	}

	
/**
 * visitor class for parsing expression from string to com.microsoft.z3.Expr
 * 
 * @author Christopher
 *
 */
 class ExpressionVisitor extends folBaseVisitor<Expr>{
		
		@Override 
		public Expr visitCondition(folParser.ConditionContext ctx) {
			System.out.println("Condition: "+ctx.getText());
			if(ctx.formula() != null) {
				return visitFormula(ctx.formula());
			}
			return null;
		}

		@Override
		public Expr visitFormula(folParser.FormulaContext ctx) {
			System.out.println("Formula: "+ctx.getText());
			if(ctx.formula() != null) { //more than one formula in this context
				return visitFormula(ctx.formula());
			} else if(ctx.connectiveformula() != null) {
				return visitConnectiveformula(ctx.connectiveformula());
			} else if(ctx.quantifier() != null) {
				return visitQuantifier(ctx.quantifier());
			} else {
				return null;
			}
		}
		
		/*@Override
		public Expr visitHasconditionformula(folParser.HasconditionformulaContext ctx) {
		//	System.out.println("has_cond: "+ctx.getText());
			BoolExpr result = (BoolExpr) visitHascondition(ctx.hascondition(0));
			int i;
			for(i = 1; i < ctx.hascondition().size(); i++) {
				BoolExpr expr = (BoolExpr) visitHascondition(ctx.hascondition(i));
				switch(ctx.connectoperator(i-1).getText()) {
				case "&": 
				case "&&":
				case "and":
					result = getContext().mkAnd(result, expr);
					break;
				case "|":
				case "||":
				case "or":
					result = getContext().mkOr(result, expr);
					break;
				case "=>":
					result = getContext().mkImplies(result, expr);
					break;
				case "<>":
					result = getContext().mkAnd(getContext().mkImplies(result, expr), getContext().mkImplies(expr, result));
					break;
				default:
					return null;
					
				}
			}
			
			for(int j = 0; j < ctx.formula().size(); j++, i++) {
				BoolExpr expr = (BoolExpr) visitFormula(ctx.formula(j));
				switch(ctx.connectoperator(i-1).getText()) {
				case "&": 
				case "&&":
				case "and":
					result = getContext().mkAnd(result, expr);
					break;
				case "|":
				case "||":
				case "or":
					result = getContext().mkOr(result, expr);
					break;
				case "=>":
					result = getContext().mkImplies(result, expr);
					break;
				case "<>":
					result = getContext().mkAnd(getContext().mkImplies(result, expr), getContext().mkImplies(expr, result));
					break;
				default:
					return null;
					
				}
			}
			return result;
		}*/
		
		@Override 
		public Expr visitHascondition(folParser.HasconditionContext ctx) {
			//System.out.println("has: "+ctx.getText());
			String name = "has_"+ctx.STRING().getText().split("\"", 3)[1];
			name = name.replace(" ", "");
			BoolExpr expr = getContext().mkBoolConst(name);
			return expr;
		}
		
		@Override 
		public Expr visitQuantifier(folParser.QuantifierContext ctx) { 
			//System.out.println("quantifier: "+ctx.getText());
			Expr[] bound = new Expr[ctx.IDENTIFIER().size()];
			for(int i = 0; i < bound.length; i++) {
				bound[i] = getContext().mkRealConst(ctx.IDENTIFIER(i).getText());
			}
			BoolExpr body = (BoolExpr)visitFormula(ctx.formula());
			/*for(int i = 1; i < ctx.formula().size(); i++) {
				BoolExpr expr = (BoolExpr)visitFormula(ctx.formula(i));
				body = getContext().mkAnd(body, expr);
			}*/
			switch(ctx.QUANTIFER().getText()) {
			case "\\forall": 
				return getContext().mkForall(bound, body, 1, null, null, null, null); 
			case "\\exists": 
				return getContext().mkExists(bound, body, 1, null, null, null, null);
			default:
				return null;
			}
		}
		
		@Override 
		public Expr visitTupelformula(folParser.TupelformulaContext ctx) { 
			return visitChildren(ctx); 
		}
		
		@Override 
		public Expr visitConnectiveformula(folParser.ConnectiveformulaContext ctx) {
			//System.out.println("connective: "+ctx.getText());
			BoolExpr result;
			if(ctx.boolexpression() != null) {
				result = (BoolExpr)visitBoolexpression(ctx.boolexpression());
				int i;
				for(i = 0; i < ctx.connectiveformula().size(); i++) {
					BoolExpr expr = (BoolExpr) visitConnectiveformula(ctx.connectiveformula(i));
					System.out.println("expression: "+ctx.connectiveformula(i).getText()+" expr: "+expr);
					System.out.println("result: "+result);
					switch(ctx.connectoperator().get(i).getText()) {
					case "&": /**AND OPERATOR**/
					case "&&":
					case "and":
						result = getContext().mkAnd(result, expr);
						break;
					case "|":/**OR OPERATOR**/
					case "||":
					case "or":
						result = getContext().mkOr(result, expr);
						break;
					case "=>":/**IMPLIES OPERATOR**/
						result = getContext().mkImplies(result, expr);
						break;
					case "<>":/**BICONDITION OPERATOR**/
						result = getContext().mkAnd(getContext().mkImplies(result, expr), getContext().mkImplies(expr, result));
						break;
					default:
						return null;
					}
				}
			} else {
				result = (BoolExpr)visitConnectiveformula(ctx.connectiveformula(0));
				if(ctx.NOT() != null) {
					result = getContext().mkNot(result);
				}
				int i;
				for(i = 1; i < ctx.connectiveformula().size(); i++) {
					BoolExpr expr = (BoolExpr) visitConnectiveformula(ctx.connectiveformula(i));
					//System.out.println("expression: "+ctx.connectiveformula(i).getText()+" expr: "+expr);
					switch(ctx.connectoperator().get(i-1).getText()) {
					case "&": /**AND OPERATOR**/
					case "&&":
					case "and":
						result = getContext().mkAnd(result, expr);
						break;
					case "|":/**OR OPERATOR**/
					case "||":
					case "or":
						result = getContext().mkOr(result, expr);
						break;
					case "=>":/**IMPLIES OPERATOR**/
						result = getContext().mkImplies(result, expr);
						break;
					case "<>":/**BICONDITION OPERATOR**/
						result = getContext().mkAnd(getContext().mkImplies(result, expr), getContext().mkImplies(expr, result));
						break;
					default:
						return null;
					}
				}
				/*for(int j = 1; j < ctx.formula().size(); j++, i++) {
					BoolExpr expr = (BoolExpr) visitFormula(ctx.formula(j));
					switch(ctx.connectoperator().get(i-1).getText()) {
					case "&": 
					case "&&":
					case "and":
						result = getContext().mkAnd(result, expr);
						break;
					case "|":
					case "||":
					case "or":
						result = getContext().mkOr(result, expr);
						break;
					case "=>":
						result = getContext().mkImplies(result, expr);
						break;
					case "<>":
						result = getContext().mkAnd(getContext().mkImplies(result, expr), getContext().mkImplies(expr, result));
						break;
					default:
						return null;
					}
				}*/
			}
			
			return result;
		}
		
		@Override
		public Expr visitBoolexpression(folParser.BoolexpressionContext ctx) { 
			//System.out.println("bool: "+ctx.getText());
			BoolExpr result;
			/*if(ctx.boolexpression() != null) {
				result = (BoolExpr)visitBoolexpression(ctx.boolexpression());
				if(ctx.NOT() != null) {
					result = getContext().mkNot(result);
				}*/
			if(ctx.quantifier() != null) {
				result =  (BoolExpr) visitQuantifier(ctx.quantifier());
			}
			else if(ctx.compareformula() != null) {
				result = (BoolExpr) visitCompareformula(ctx.compareformula());
			} else if(ctx.TRUE() != null) {
				result = getContext().mkTrue();
			} else if(ctx.FALSE() != null) {
				result = getContext().mkFalse();
			} else if(ctx.hascondition() != null){
				result = (BoolExpr)visitHascondition(ctx.hascondition());
			} else if(ctx.boolexpression() != null){
				result = (BoolExpr)visitBoolexpression(ctx.boolexpression());
				if(ctx.NOT() != null) {
					result = getContext().mkNot(result);
				}
			}else {
				result = null;
			}
			
			return result;
		}
		
		@Override 
		public Expr visitCompareformula(folParser.CompareformulaContext ctx) { 
			//System.out.println("compare: "+ctx.getText());
			BoolExpr result = null;
			if(ctx.compareformula() != null) {
				return visitCompareformula(ctx.compareformula());
			}
			ArithExpr first = (ArithExpr) visitSummformula(ctx.summformula(0));
			for(int i = 1; i < ctx.summformula().size(); i++) {
				ArithExpr expr = (ArithExpr) visitSummformula(ctx.summformula(i));
				switch(ctx.compoperator(i-1).getText()) {
				case ">": 
					result = getContext().mkGt(first, expr);
					first = expr;
					break;
				case "<":
					result = getContext().mkLt(first, expr);
					first = expr;
					break;
				case ">=":
					result = getContext().mkGe(first, expr);
					first = expr;
					break;
				case "<=":
					result = getContext().mkLe(first, expr);
					first = expr;
					break;
				case "==":
					result = getContext().mkEq(first, expr);
					first = expr;
					break;
				case "!=":
					result = getContext().mkNot(getContext().mkEq(first, expr));
					first = expr;
					break;
				default:
					return null;
				}
			}
			return result;
		}
		
		@Override 
		public Expr visitSummformula(folParser.SummformulaContext ctx) { 
			//System.out.println("summ: "+ctx.getText());
			if(ctx.faktorformula() == null) {
				return visitSummformula(ctx.summformula(0));
			}
			ArithExpr result = (ArithExpr) visitFaktorformula(ctx.faktorformula());
			for(int i = 0; i < ctx.summformula().size(); i++) {
				ArithExpr expr = (ArithExpr) visitSummformula(ctx.summformula(i));
				switch(ctx.addoperator(i).getText()) {
				case "+":
					result = getContext().mkAdd(result, expr);
					break;
				case "-":
					result = getContext().mkSub(result, expr);
					break;
				default:
					result = null;
				}
			}
			return result;
		}
		
		@Override 
		public Expr visitFaktorformula(folParser.FaktorformulaContext ctx) { 
			//System.out.println("faktor: "+ctx.getText());
			if(ctx.powerformula() == null) {
				return visitFaktorformula(ctx.faktorformula(0));
			}
			ArithExpr result = (ArithExpr) visitPowerformula(ctx.powerformula());
			for(int i = 0; i < ctx.faktorformula().size(); i++) {
				ArithExpr expr = (ArithExpr) visitFaktorformula(ctx.faktorformula(i));
				switch(ctx.multoperator(i).getText()) {
				case "*":
					result = getContext().mkMul(result, expr);
					break;
				case "/":
					result = getContext().mkDiv(result, expr);
					break;
				default:
					result = null;
				}
			}
			return result;
		}
		
		@Override 
		public Expr visitPowerformula(folParser.PowerformulaContext ctx) { 
			//System.out.println("power: "+ctx.getText());
			if(ctx.term() == null) {
				return visitPowerformula(ctx.powerformula(0));
			}
			ArithExpr result = (ArithExpr) visitTerm(ctx.term());
			for(int i = 0; i < ctx.powerformula().size(); i++) {
				ArithExpr expr = (ArithExpr) visitPowerformula(ctx.powerformula(i));
				result = getContext().mkPower(result, expr);
			}
			return result;
		}
		
		@Override
		public Expr visitTerm(folParser.TermContext ctx) {
			//System.out.println("term: "+ctx.getText());
			if(ctx.term() != null) {
				if(ctx.MINUS() != null) {
					return getContext().mkSub(getContext().mkReal(0), (ArithExpr)visitTerm(ctx.term()));
				}
				return visitTerm(ctx.term());
			} else if(ctx.variable() != null) {
				return getContext().mkRealConst(ctx.variable().getText());
			} else if(ctx.NUMBER() != null) {
				return getContext().mkReal(ctx.NUMBER().getText());
			} else {
				return null;
			}
		}
	}
	
	 /*class OperationVisitor extends folBaseVisitor<String>{
			
		@Override
		public String visitSkill_name(Skill_nameContext ctx) {
			//System.out.println(ctx.getText());
			String s = "";
			if(ctx.skill_name().size() > 1) {
				return visitSkill_name(ctx.skill_name(0))+"_"+visitSkill_name(ctx.skill_name(1));
			} else {
				return ctx.VARIABLE().getText();
			}
		}

		@Override
		public String visitBin_connective(Bin_connectiveContext ctx) {
			//System.out.println(ctx.getText());
			if(ctx.CONJ() != null) {
				return ctx.CONJ().getText();
			} else if(ctx.DISJ() != null) {
				return ctx.DISJ().getText();
			} else if(ctx.BICOND() != null) {
				return ctx.BICOND().getText();
			} else if(ctx.IMPL() != null) {
				return ctx.IMPL().getText();
			} else {
				return null;
			}
		}

		@Override
		public String visitBinop(BinopContext ctx) {
			//System.out.println(ctx.getText());
			if(ctx.EQUAL() != null) {
				return ctx.EQUAL().getText();
			} else if(ctx.GT() != null) {
				return ctx.GT().getText();
			} else if(ctx.GEQ() != null) {
				return ctx.GEQ().getText();
			} else if(ctx.LT() != null) {
				return ctx.LT().getText();
			} else if(ctx.LEQ() != null) {
				return ctx.LEQ().getText();
			} else {
				return null;
			}
		}
		
		@Override
		public String visitSeparator(SeparatorContext ctx) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override 
		public String visitArith_operation(folParser.Arith_operationContext ctx) { 
			//System.out.println(ctx.getText());
			if(ctx.PLUS() != null) {
				return ctx.PLUS().getText();
			} else if(ctx.MINUS() != null) {
				return ctx.MINUS().getText();
			} else if(ctx.TIMES() != null) {
				return ctx.TIMES().getText();
			} else if(ctx.DIV() != null) {
				return ctx.DIV().getText();
			} else {
				return null;
			}
		}
	}*/
	

}