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