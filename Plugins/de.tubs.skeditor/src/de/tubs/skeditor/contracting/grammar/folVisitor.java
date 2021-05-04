// Generated from fol.g4 by ANTLR 4.7.1
package de.tubs.skeditor.contracting.grammar;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link folParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface folVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link folParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(folParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormula(folParser.FormulaContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#hascondition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHascondition(folParser.HasconditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#quantifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuantifier(folParser.QuantifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#operatorformula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorformula(folParser.OperatorformulaContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#connectiveformula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConnectiveformula(folParser.ConnectiveformulaContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#tupelformula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTupelformula(folParser.TupelformulaContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#boolexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolexpression(folParser.BoolexpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#compareformula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompareformula(folParser.CompareformulaContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#mathematicalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMathematicalExpression(folParser.MathematicalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#tupel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTupel(folParser.TupelContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(folParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#array}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray(folParser.ArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#compproperty}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompproperty(folParser.ComppropertyContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#portproperty}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPortproperty(folParser.PortpropertyContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#port}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPort(folParser.PortContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPredicate(folParser.PredicateContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#prefix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefix(folParser.PrefixContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#functioncall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctioncall(folParser.FunctioncallContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#functionname}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionname(folParser.FunctionnameContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#compoperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompoperator(folParser.CompoperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#multoperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultoperator(folParser.MultoperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#addoperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddoperator(folParser.AddoperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#connectoperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConnectoperator(folParser.ConnectoperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#pred_constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPred_constant(folParser.Pred_constantContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(folParser.VariableContext ctx);
}