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
	 * Enter a parse tree produced by {@link folParser#quantifier}.
	 * @param ctx the parse tree
	 */
	void enterQuantifier(folParser.QuantifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#quantifier}.
	 * @param ctx the parse tree
	 */
	void exitQuantifier(folParser.QuantifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#operatorformula}.
	 * @param ctx the parse tree
	 */
	void enterOperatorformula(folParser.OperatorformulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#operatorformula}.
	 * @param ctx the parse tree
	 */
	void exitOperatorformula(folParser.OperatorformulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#connectiveformula}.
	 * @param ctx the parse tree
	 */
	void enterConnectiveformula(folParser.ConnectiveformulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#connectiveformula}.
	 * @param ctx the parse tree
	 */
	void exitConnectiveformula(folParser.ConnectiveformulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#tupelformula}.
	 * @param ctx the parse tree
	 */
	void enterTupelformula(folParser.TupelformulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#tupelformula}.
	 * @param ctx the parse tree
	 */
	void exitTupelformula(folParser.TupelformulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#compareformula}.
	 * @param ctx the parse tree
	 */
	void enterCompareformula(folParser.CompareformulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#compareformula}.
	 * @param ctx the parse tree
	 */
	void exitCompareformula(folParser.CompareformulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#tupel}.
	 * @param ctx the parse tree
	 */
	void enterTupel(folParser.TupelContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#tupel}.
	 * @param ctx the parse tree
	 */
	void exitTupel(folParser.TupelContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#summformula}.
	 * @param ctx the parse tree
	 */
	void enterSummformula(folParser.SummformulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#summformula}.
	 * @param ctx the parse tree
	 */
	void exitSummformula(folParser.SummformulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#faktorformula}.
	 * @param ctx the parse tree
	 */
	void enterFaktorformula(folParser.FaktorformulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#faktorformula}.
	 * @param ctx the parse tree
	 */
	void exitFaktorformula(folParser.FaktorformulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#powerformula}.
	 * @param ctx the parse tree
	 */
	void enterPowerformula(folParser.PowerformulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#powerformula}.
	 * @param ctx the parse tree
	 */
	void exitPowerformula(folParser.PowerformulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#notterm}.
	 * @param ctx the parse tree
	 */
	void enterNotterm(folParser.NottermContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#notterm}.
	 * @param ctx the parse tree
	 */
	void exitNotterm(folParser.NottermContext ctx);
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
	 * Enter a parse tree produced by {@link folParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArray(folParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArray(folParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#compproperty}.
	 * @param ctx the parse tree
	 */
	void enterCompproperty(folParser.ComppropertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#compproperty}.
	 * @param ctx the parse tree
	 */
	void exitCompproperty(folParser.ComppropertyContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#portproperty}.
	 * @param ctx the parse tree
	 */
	void enterPortproperty(folParser.PortpropertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#portproperty}.
	 * @param ctx the parse tree
	 */
	void exitPortproperty(folParser.PortpropertyContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#port}.
	 * @param ctx the parse tree
	 */
	void enterPort(folParser.PortContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#port}.
	 * @param ctx the parse tree
	 */
	void exitPort(folParser.PortContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#functioncall}.
	 * @param ctx the parse tree
	 */
	void enterFunctioncall(folParser.FunctioncallContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#functioncall}.
	 * @param ctx the parse tree
	 */
	void exitFunctioncall(folParser.FunctioncallContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#functionname}.
	 * @param ctx the parse tree
	 */
	void enterFunctionname(folParser.FunctionnameContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#functionname}.
	 * @param ctx the parse tree
	 */
	void exitFunctionname(folParser.FunctionnameContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#compoperator}.
	 * @param ctx the parse tree
	 */
	void enterCompoperator(folParser.CompoperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#compoperator}.
	 * @param ctx the parse tree
	 */
	void exitCompoperator(folParser.CompoperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#multoperator}.
	 * @param ctx the parse tree
	 */
	void enterMultoperator(folParser.MultoperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#multoperator}.
	 * @param ctx the parse tree
	 */
	void exitMultoperator(folParser.MultoperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#addoperator}.
	 * @param ctx the parse tree
	 */
	void enterAddoperator(folParser.AddoperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#addoperator}.
	 * @param ctx the parse tree
	 */
	void exitAddoperator(folParser.AddoperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#connectoperator}.
	 * @param ctx the parse tree
	 */
	void enterConnectoperator(folParser.ConnectoperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#connectoperator}.
	 * @param ctx the parse tree
	 */
	void exitConnectoperator(folParser.ConnectoperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link folParser#pred_constant}.
	 * @param ctx the parse tree
	 */
	void enterPred_constant(folParser.Pred_constantContext ctx);
	/**
	 * Exit a parse tree produced by {@link folParser#pred_constant}.
	 * @param ctx the parse tree
	 */
	void exitPred_constant(folParser.Pred_constantContext ctx);
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
}