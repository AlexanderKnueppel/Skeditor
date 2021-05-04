package de.tubs.skeditor.contracting.grammar;

import com.microsoft.z3.*;

import de.tubs.skeditor.contracting.grammar.folParser.BoolexpressionContext;
import de.tubs.skeditor.contracting.grammar.folParser.CompareformulaContext;
import de.tubs.skeditor.contracting.grammar.folParser.ConnectiveformulaContext;
import de.tubs.skeditor.contracting.grammar.folParser.FormulaContext;
import de.tubs.skeditor.contracting.grammar.folParser.TermContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Z3ConversionListener extends folBaseListener {
	private Set<String> variables = new HashSet<String>();
	private Context z3context = new Context();
	private BoolExpr result = null;
	
//  IntExpr x = ctx.mkIntConst("x");
//  
//  BoolExpr c1 = ctx.mkEq(x, ctx.mkInt(5));
//  BoolExpr c2 = ctx.mkEq(x, ctx.mkInt(6));
//  BoolExpr c3 = ctx.mkEq(x, ctx.mkInt(7));
//  BoolExpr c4 = ctx.mkEq(x, ctx.mkInt(8));
//  BoolExpr c5 = ctx.mkEq(x, ctx.mkInt(9));
	
	private Context getZ3Context() {
		return z3context;
	}
	
	@Override 
	public void enterFormula(FormulaContext ctx) {
		// TODO Auto-generated method stub
		super.enterFormula(ctx);
		
//		if(ctx.connectiveformula() != null) {
//			result = resolveConnectiveFormula(ctx.connectiveformula(), new ArrayList<BoolExpr>());
//		}
	}
	
	
	
//	private BoolExpr resolveConnectiveFormula(ConnectiveformulaContext formula, List<BoolExpr> expressions) {
//		// TODO Auto-generated method stub
//		boolean negated = false;
//		if(formula.NOT() != null) {
//			negated = true;
//			formula.connectiveformula()
//		}
//		return null;
//	}

//	@Override 
//	public void enterConnectiveformula(ConnectiveformulaContext ctx) {
//		// TODO Auto-generated method stub
//		super.enterConnectiveformula(ctx);
//		if(ctx.boolexpression() != null) {
//			BoolExpr be = resolveBoolExpression(ctx.boolexpression());
//		}
//	}
	
//	private BoolExpr resolveBoolExpression(BoolexpressionContext boolexpression) {
//		if(boolexpression.TRUE() != null) {
//			return z3context.mkTrue();
//		} else if(boolexpression.FALSE() != null) {
//			return z3context.mkFalse();
//		} else if(boolexpression.NOT() != null) {
//			return z3context.mkNot(resolveBoolExpression(boolexpression.boolexpression()));
//		} else if(boolexpression.compareformula() != null) {
//			return resolveCompareFormula(boolexpression.compareformula());
//		}
//		
//		return null;
//	}
	
//	private BoolExpr resolveCompareFormula(CompareformulaContext formula) {
//		if(formula.compareformula() != null) {
//			return resolveCompareFormula(formula.compareformula());
//		} else if(formula.summformula() != null && !formula.summformula().isEmpty()) {
//			BoolExpr res;
//			for(SummformulaContext sum : formula.summformula()) {
//				BoolExpr tmp = resolveSumFormula(sum);
//				
//			}
//			
//			//formula.c
//		}
//		
//		return null;
//	}
	
//	private BoolExpr resolveSumFormula(SummformulaContext sum) {
//		return null;
//	}	
	@Override 
	public void enterTerm(TermContext ctx) {
		// TODO Auto-generated method stub
		super.enterTerm(ctx);
	}
	
	@Override public void enterVariable(folParser.VariableContext ctx) { 
		variables.add(ctx.IDENTIFIER().getText());
	}
	
	public Set<String> getVariables() {
		return variables;
	}
}
