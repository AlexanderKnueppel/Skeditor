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
	 * Visit a parse tree produced by {@link folParser#has_condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHas_condition(folParser.Has_conditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(folParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#scientific}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScientific(folParser.ScientificContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(folParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#binop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinop(folParser.BinopContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#has_skill}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHas_skill(folParser.Has_skillContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#skill_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSkill_name(folParser.Skill_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#bin_connective}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBin_connective(folParser.Bin_connectiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#arith_operation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArith_operation(folParser.Arith_operationContext ctx);
	/**
	 * Visit a parse tree produced by {@link folParser#separator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSeparator(folParser.SeparatorContext ctx);
}