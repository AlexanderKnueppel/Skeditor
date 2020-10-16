// Generated from fol.g4 by ANTLR 4.7.1
package de.tubs.skeditor.contracting.grammar;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link folParser}.
 */
public interface folListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link folParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(folParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(folParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#formula}.
	 * @param ctx the parse tree
	 */
	void enterFormula(folParser.FormulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#formula}.
	 * @param ctx the parse tree
	 */
	void exitFormula(folParser.FormulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#has_condition}.
	 * @param ctx the parse tree
	 */
	void enterHas_condition(folParser.Has_conditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#has_condition}.
	 * @param ctx the parse tree
	 */
	void exitHas_condition(folParser.Has_conditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(folParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(folParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#scientific}.
	 * @param ctx the parse tree
	 */
	void enterScientific(folParser.ScientificContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#scientific}.
	 * @param ctx the parse tree
	 */
	void exitScientific(folParser.ScientificContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(folParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(folParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#binop}.
	 * @param ctx the parse tree
	 */
	void enterBinop(folParser.BinopContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#binop}.
	 * @param ctx the parse tree
	 */
	void exitBinop(folParser.BinopContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#has_skill}.
	 * @param ctx the parse tree
	 */
	void enterHas_skill(folParser.Has_skillContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#has_skill}.
	 * @param ctx the parse tree
	 */
	void exitHas_skill(folParser.Has_skillContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#skill_name}.
	 * @param ctx the parse tree
	 */
	void enterSkill_name(folParser.Skill_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#skill_name}.
	 * @param ctx the parse tree
	 */
	void exitSkill_name(folParser.Skill_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#bin_connective}.
	 * @param ctx the parse tree
	 */
	void enterBin_connective(folParser.Bin_connectiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#bin_connective}.
	 * @param ctx the parse tree
	 */
	void exitBin_connective(folParser.Bin_connectiveContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#arith_operation}.
	 * @param ctx the parse tree
	 */
	void enterArith_operation(folParser.Arith_operationContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#arith_operation}.
	 * @param ctx the parse tree
	 */
	void exitArith_operation(folParser.Arith_operationContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#separator}.
	 * @param ctx the parse tree
	 */
	void enterSeparator(folParser.SeparatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#separator}.
	 * @param ctx the parse tree
	 */
	void exitSeparator(folParser.SeparatorContext ctx);
}