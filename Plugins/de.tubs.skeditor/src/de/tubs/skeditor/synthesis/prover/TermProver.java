package de.tubs.skeditor.synthesis.prover;

import java.util.ArrayDeque;
import java.util.Deque;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.FuncDecl;
import com.microsoft.z3.Pattern;
//import com.microsoft.z3.Expr;
//import com.microsoft.z3.RealExpr;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;

import de.tubs.skeditor.contracting.grammar.folBaseListener;
import de.tubs.skeditor.contracting.grammar.folBaseVisitor;
import de.tubs.skeditor.contracting.grammar.folLexer;
import de.tubs.skeditor.contracting.grammar.folListener;
import de.tubs.skeditor.contracting.grammar.folParser;
import de.tubs.skeditor.contracting.grammar.folParser.Bin_connectiveContext;
import de.tubs.skeditor.contracting.grammar.folParser.BinopContext;
import de.tubs.skeditor.contracting.grammar.folParser.ConditionContext;
import de.tubs.skeditor.contracting.grammar.folParser.FormulaContext;
import de.tubs.skeditor.contracting.grammar.folParser.Has_conditionContext;
import de.tubs.skeditor.contracting.grammar.folParser.Has_skillContext;
import de.tubs.skeditor.contracting.grammar.folParser.ScientificContext;
import de.tubs.skeditor.contracting.grammar.folParser.SeparatorContext;
import de.tubs.skeditor.contracting.grammar.folParser.Skill_nameContext;
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
		ExpressionVisitor myVisitor = new ExpressionVisitor(getContext());
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
		ExpressionVisitor myVisitor = new ExpressionVisitor(getContext());
		for (int i = 0; i < terms.length; i++) {
			folLexer lexer = new folLexer(CharStreams.fromString(terms[i]));
			folParser parser = new folParser(new CommonTokenStream(lexer));
			Expr expr = myVisitor.visit(parser.condition());
			//System.out.println("Expr: "+expr);
			solver.add((BoolExpr) expr);
			//System.out.println("Expression: "+expressions[i]);
		}
		if(terms.length > 0) {
			Status result = solver.check();
			switch(result) {
			case SATISFIABLE:
				System.out.println("Erfüllbar");
				return true;
			case UNSATISFIABLE:
				System.out.println("Unerfüllbar");
				return false;
			default:
				System.out.println("Unbekannt");
				return false;
			}
		}
		return false;
		
	}
	/*
	private BoolExpr createExpression(String s) {
		char c;
		String operator = "";
		int operatorIndex = 0;
		for(int i = 0; i < s.length(); i++) {
			c = s.charAt(i);
			if (c == '<' || c == '>') {
				operatorIndex = i;
				if(s.charAt(i+1) == '=') { // <= or >=
					operator = String.format("%c=", c);
				} else {
					operator = String.format("%c", c);
				}
				
			} else if (c == '=') {
				operatorIndex = i;
				
				operator = "=";
			} 
		}
		String[] operands = s.split(operator);
		String firstOperandString = operands[0];
		String secondOperandString = operands[1];
		ArithExpr firstOperand = createOperandExpression(firstOperandString);
		ArithExpr secondOperand = createOperandExpression(secondOperandString);
		BoolExpr expression;
		switch (operator) {
		case ">":
			expression = ctx.mkGt(firstOperand, secondOperand);
			break;
		case ">=":
			expression = ctx.mkGe(firstOperand, secondOperand);
			break;
		case "<=":
			expression = ctx.mkLe(firstOperand, secondOperand);
			break;
		case "<":
			expression = ctx.mkLt(firstOperand, secondOperand);
			break;
		case "=":
			expression = ctx.mkEq(firstOperand, secondOperand);
			break;

		default:
			expression = null;
			break;
		}
		return expression;
	}
	
	private ArithExpr createOperandExpression(String operand) {
		ArithExpr expression = null;
		ArithExpr first = null, second = null;
		Deque<Character> stack = new ArrayDeque<>();
		String str1 = "", str2 = "";
		String operator = "";
		if(operand.startsWith("(")) { //if operand starts with parenthesis, find matching closing parenthesis
			int i = 0;
			do {
				if(operand.charAt(i) == '(') {
					stack.push('(');
				} else if(operand.charAt(i) == ')') {
					stack.pop();
				}
				i++;
			} while(!stack.isEmpty() && i < operand.length());
			if(operand.length() > i) {
				operator = ""+operand.charAt(i);
			}
			//create expression for first operand
			str1 = operand.substring(1, i);
			first = createOperandExpression(str1);
			
			if(operator.length() > 0) {
				//create expression for second operand
				str2 = operand.substring(i+1);
				second = createOperandExpression(str2);
			} 
		} else {
			System.out.println(operand);
			for(int i = 0; i < operand.length(); i++) {
				if(operand.charAt(i) == '+' || operand.charAt(i) == '-' || operand.charAt(i) == '*' || operand.charAt(i) == '/' || operand.charAt(i) == '^') {
					str1 = operand.substring(0, i);
					first = createOperandExpression(str1);
					str2 = operand.substring(i+1);
					second = createOperandExpression(str2);
					operator = ""+operand.charAt(i);
					System.out.println(str1+ " und "+str2);
					break;
				}
			}
			if(str2.length() == 0) {
				if(SynthesisUtil.isNumeric(operand)) {
					first = ctx.mkReal(Integer.parseInt(operand)); //create Real number expression
				} else {
					first = ctx.mkRealConst(operand); //create variable as real expression
				}
			}
		}
		if(operator.length() > 0) {
			switch (operator) {
			case "+":
				expression = ctx.mkAdd(first, second);
				break;
			case "-":
				expression = ctx.mkSub(first, second);
				break;
			case "*":
				expression = ctx.mkMul(first, second);
				break;
			case "/":
				expression = ctx.mkDiv(first, second);
				break;
			case "^":
				expression = ctx.mkPower(first, second);
				break;

			default:
				expression = null;
				break;
			}
		} else {
			expression = first;
		}
		return expression;
	}*/
	
	public static void main(String[] args) {
		TermProver prover = new TermProver();
		System.out.println("Ergebnis: "+prover.check("x>4", "x>7", "x>7&x>7"));
		//System.out.println("Ergebnis: "+prover.prove("x<4", "x<7", "x<y", "y=4"));
	}

	

 class ExpressionVisitor extends folBaseVisitor<Expr>{
		
		private Context context;
		
		public ExpressionVisitor(Context context) {
			this.context = context;
		}
		
		@Override 
		public Expr visitCondition(folParser.ConditionContext ctx) {
			//System.out.println(ctx.getText());
			if(ctx.formula() != null) {
				return visitFormula(ctx.formula());
			}
			if(ctx.has_condition() != null) {
				return visitHas_condition(ctx.has_condition());
			}
			return null;
		}

		@Override
		public Expr visitFormula(FormulaContext ctx) {
			//System.out.println("Formula: "+ctx.getText());
			if(ctx.formula().size() > 1) { //more than one formula in this context
				
				BoolExpr firstFormula = (BoolExpr) visitFormula(ctx.formula(0));
				if(ctx.NOT() != null) {
					firstFormula = context.mkNot(firstFormula);
				}
				BoolExpr secondFormula = (BoolExpr) visitFormula(ctx.formula(1)); 
				String binConn = ctx.bin_connective().accept(new OperationVisitor());
				//System.out.println("Verbinder: "+ctx.bin_connective().getText());
				BoolExpr expression = null;
				switch(binConn) {
				case "&":
					expression = context.mkAnd(firstFormula, secondFormula);
					break;
				case "|":
					expression = context.mkOr(firstFormula, secondFormula);
					break;
				case "->":
					expression = context.mkImplies(firstFormula, secondFormula);
					break;
				case "<->":
					expression = context.mkAnd(context.mkImplies(firstFormula, secondFormula), context.mkImplies(secondFormula, firstFormula));
					break;
				default:
					return null;
				}
				return expression;
			} else if (ctx.formula().size() == 1) { //one formula expression
				if(ctx.NOT() != null) {
					return context.mkNot((BoolExpr)visitFormula(ctx.formula(0)));
				} else if(ctx.FORALL() != null) {
					Expr[] boundVariable = {visitVariable(ctx.variable())};
					return context.mkForall(boundVariable, visitFormula(ctx.formula(0)), 0, null, null, null, null);
				} else if(ctx.EXISTS() != null) {
					Expr[] boundVariable = {visitVariable(ctx.variable())};
					return context.mkExists(boundVariable, visitFormula(ctx.formula(0)), 0, null, null, null, null);
				} else {
					return visitFormula(ctx.formula(0));
				}
			} else { //no formula, so only terms
				if(ctx.term().size() > 1) {
					ArithExpr term1 = (ArithExpr)visitTerm(ctx.term(0));
					ArithExpr term2 = (ArithExpr)visitTerm(ctx.term(1));
					Expr expression;
					String operation = ctx.binop().accept(new OperationVisitor());
					switch(operation) {
					case "=": 
						expression = context.mkEq(term1, term2);
						break;
					case ">":
						expression = context.mkGt(term1, term2);
						break;
					case "<":
						expression = context.mkLt(term1, term2);
						break;
					case ">=":
						expression = context.mkGe(term1, term2);
						break;
					case "<=":
						expression = context.mkLe(term1, term2);
						break;
					default: expression = null;
					}
					return expression;
				} else if (ctx.term().size() == 1) {
					return visitTerm(ctx.term(0));
				} else {
					switch(ctx.BOOL_LITERAL().getText()) {
					case "true":
						return context.mkTrue();
					case "false":
						return context.mkFalse();
					default:
						return null;
					}
				}
			}
		}
		
		@Override
		public Expr visitHas_condition(Has_conditionContext ctx) {
		//	System.out.println("has_cond: "+ctx.getText());
			if(ctx.has_condition().size() > 1) { //binary connected conditions
				BoolExpr has1 = (BoolExpr)visitHas_condition(ctx.has_condition(0));
				BoolExpr has2 = (BoolExpr)visitHas_condition(ctx.has_condition(1));
				Expr expression;
				String operation = ctx.bin_connective().accept(new OperationVisitor());
				switch(operation) {
				case "&":
					expression = context.mkAnd(has1, has2);
					break;
				case "|":
					expression = context.mkOr(has1, has2);
					break;
				case "->":
					expression = context.mkImplies(has1, has2);
					break;
				case "<->":
					expression = context.mkAnd(context.mkImplies(has1, has2), context.mkImplies(has2, has1));
					break;
				default:
					expression = null;
				}
				return expression;
			} else if (ctx.has_condition().size() == 1 ) {
				if(ctx.NOT() != null) {
					return context.mkNot((BoolExpr)visitHas_condition(ctx.has_condition(0)));
				}
				return visitHas_condition(ctx.has_condition(0));
			} else {
				return visitHas_skill(ctx.has_skill());
			}
		}
		
		@Override 
		public Expr visitHas_skill(Has_skillContext ctx) {
			//System.out.println("has_skill: "+ctx.getText());
			String skillname = ctx.skill_name().accept(new OperationVisitor());
			return context.mkBoolConst("has_"+skillname);
		}
		
		@Override
		public Expr visitTerm(TermContext ctx) {
			//System.out.println("term: "+ctx.getText());
			if(ctx.term().size() > 1) { //2 terms in this term
				ArithExpr term1 = (ArithExpr)visitTerm(ctx.term(0));
				ArithExpr term2 = (ArithExpr)visitTerm(ctx.term(1));
				Expr expression;
				String operation = ctx.arith_operation().accept(new OperationVisitor());
				switch(operation) {
				case "+":
					expression = context.mkAdd(term1, term2);
					break;
				case "-":
					expression = context.mkSub(term1, term2);
					break;
				case "*":
					expression = context.mkMul(term1, term2);
					break;
				case "/":
					expression = context.mkDiv(term1, term2);
					break;
				default:
					expression = null;
				}
				return expression;
			} else if(ctx.term().size() == 1){
				return visitTerm(ctx.term(0));
			} else {
				if(ctx.PLUS() != null) {
					if(ctx.scientific() != null) {
						return visitScientific(ctx.scientific());
					} else {
						return visitVariable(ctx.variable());
					}
				} else if(ctx.MINUS() != null) {
					if(ctx.scientific() != null) {
						return context.mkSub((ArithExpr)context.mkReal(0), (ArithExpr)visitScientific(ctx.scientific()));
					} else {
						return context.mkSub((ArithExpr)context.mkReal(0), (ArithExpr)visitVariable(ctx.variable()));
					}
				} else {
					if(ctx.scientific() != null) {
						return visitScientific(ctx.scientific());
					} else {
						return visitVariable(ctx.variable());
					}
				}
			}
		}

		@Override
		public Expr visitScientific(ScientificContext ctx) {
			//System.out.println("scientific"+ctx.getText());
			return context.mkReal(ctx.SCIENTIFIC_NUMBER().getText());
			
		}

		@Override
		public Expr visitVariable(VariableContext ctx) {
			//System.out.println("variable "+ctx.getText());
			return context.mkRealConst(ctx.VARIABLE().getText());
		}
	}
	
	 class OperationVisitor extends folBaseVisitor<String>{
			
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
	}
	

}
