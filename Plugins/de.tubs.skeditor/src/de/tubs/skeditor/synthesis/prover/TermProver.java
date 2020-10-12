package de.tubs.skeditor.synthesis.prover;

import java.util.ArrayDeque;
import java.util.Deque;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.RealExpr;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;

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
		ctx = new Context();
	}
	
	/**
	 * proves a term
	 * 
	 * @param toProve, the term to prove
	 * @param assumptions, assumed terms 
	 * @return true if assumptions imply toProve otherwise false 
	 */
	public boolean prove(String toProve, String... assumptions) {
		if(!check(assumptions)) {
			System.out.println("Annahmen sind nicht erfüllbar!");
			return false;
		} else {
			BoolExpr[] assumptionExpressions = new BoolExpr[assumptions.length];
			for (int i = 0; i < assumptions.length; i++) {
				assumptionExpressions[i] = createExpression(assumptions[i]);
				System.out.println("Expression: "+assumptionExpressions[i]);
			}
			BoolExpr toProveExpression = createExpression(toProve);
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
		BoolExpr[] expressions = new BoolExpr[terms.length];
		
		for (int i = 0; i < terms.length; i++) {
			expressions[i] = createExpression(terms[i]);
			System.out.println("Expression: "+expressions[i]);
		}
		for(int i = 0; i < terms.length; i++) {
			solver.add(expressions[i]);
		}
		if(expressions.length > 0) {
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
	}
	
	public static void main(String[] args) {
		TermProver prover = new TermProver();
		System.out.println("Ergebnis: "+prover.check("x*x>4", "x>7", "y<x", "y>3", "z>x+y", "z<10"));
	}
	

}
