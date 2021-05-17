package de.tubs.skeditor.contracting.grammar;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.apache.logging.log4j.core.Filter.Result;

import com.microsoft.z3.ApplyResult;
import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import com.microsoft.z3.FuncDecl;
import com.microsoft.z3.Goal;
import com.microsoft.z3.IntNum;
import com.microsoft.z3.RatNum;
import com.microsoft.z3.RealSort;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Sort;
import com.microsoft.z3.Status;
import com.microsoft.z3.Tactic;
import com.microsoft.z3.Z3Exception;
import com.microsoft.z3.enumerations.Z3_ast_kind;

import de.tubs.skeditor.contracting.grammar.folParser.MathematicalExpressionContext;

public class Z3Converter {

//	public void visit(Expr e) {
//		if(e.isAnd()) {
//			for() {
//				
//			}
//			
//		}
//	}

	private Context z3context;

	public Z3Converter() {
		z3context = new Context();
	}

	public Context getContext() {
		return z3context;
	}

	public Solver getZ3SolverFromFormula(String formula) {
		Solver solver = z3context.mkSolver();
		folLexer lexer = new folLexer(CharStreams.fromString(formula));
		folParser parser = new folParser(new CommonTokenStream(lexer));
		ExpressionVisitor myVisitor = new ExpressionVisitor();

		try {
			BoolExpr expr = (BoolExpr) myVisitor.visit(parser.condition());
			solver.add(expr);
		} catch (Z3Exception | NullPointerException e) {
			e.printStackTrace();
			return null;
		}

		return solver;
	}

	public BoolExpr getZ3BoolExpression(String formula) {
		folLexer lexer = new folLexer(CharStreams.fromString(formula));
		folParser parser = new folParser(new CommonTokenStream(lexer));
		ExpressionVisitor myVisitor = new ExpressionVisitor();
		try {
			return (BoolExpr) myVisitor.visit(parser.condition());
		} catch (Z3Exception | NullPointerException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String toFormulaFromZ3Expression(Expr formula) {
		List<String> tmp = new ArrayList<String>();
		visitZ3Expr(formula, tmp);
		if (!tmp.isEmpty()) {
			String res = tmp.get(0);
			return res;
		}
		return "";
	}

	public String simplifyFormula(String formula) {
		Goal goal = getContext().mkGoal(true, false, false);
		goal.add(getZ3BoolExpression(formula));

		Tactic ctxSolverSimplify = getContext().mkTactic("ctx-solver-simplify");
		ApplyResult res3 = ctxSolverSimplify.apply(goal);

		Solver solver = z3context.mkSolver();
		List<String> assertions = new ArrayList<String>();
		for (Goal sub : res3.getSubgoals()) {
			for (BoolExpr expr : sub.getFormulas()) {
				assertions.add(toFormulaFromZ3Expression(expr));
			}
			return String.join(" & ", assertions);
			// assertions.add(toFormulaFromZ3Expression(sub.getFormulas());
			// solver.add(sub.getFormulas()); // Should only be one, right?
		}

		// solver.check();

//		if(solver.getAssertions().length > 0) {
//			for(int i = 0; i < solver.getAssertions().length; ++i) {
//				
//				return toFormulaFromZ3Expression(solver.getAssertions()[0]);
//			}
//			return toFormulaFromZ3Expression(solver.getAssertions()[0]);
//		}

		return "\\true";
	}

	private void visitZ3Expr(Expr e, List<String> parts) {
		if (e.isAnd()) {
			List<String> tmp = new ArrayList<String>();
			for (int i = 0; i < e.getNumArgs(); ++i) {
				visitZ3Expr(e.getArgs()[i], tmp);
			}
			parts.add("(" + String.join(" & ", tmp) + ")");
		} else if (e.isOr()) {
			List<String> tmp = new ArrayList<String>();
			for (int i = 0; i < e.getNumArgs(); ++i) {
				visitZ3Expr(e.getArgs()[i], tmp);
			}
			parts.add("(" + String.join(" | ", tmp) + ")");
		} else if (e.isImplies()) {
			List<String> tmp = new ArrayList<String>();
			for (int i = 0; i < e.getNumArgs(); ++i) {
				visitZ3Expr(e.getArgs()[i], tmp);
			}
			parts.add("(" + String.join(" => ", tmp) + ")");
		} else if (e.isIff()) {
			List<String> tmp = new ArrayList<String>();
			for (int i = 0; i < e.getNumArgs(); ++i) {
				visitZ3Expr(e.getArgs()[i], tmp);
			}
			parts.add("(" + String.join(" <=> ", tmp) + ")");
		} else if (e.isGE()) {
			List<String> tmp1 = new ArrayList<String>();
			List<String> tmp2 = new ArrayList<String>();
			visitZ3Expr(e.getArgs()[0], tmp1);
			visitZ3Expr(e.getArgs()[1], tmp2);
			parts.add(tmp1.get(0) + " >= " + tmp2.get(0));
		} else if (e.isGT()) {
			List<String> tmp1 = new ArrayList<String>();
			List<String> tmp2 = new ArrayList<String>();
			visitZ3Expr(e.getArgs()[0], tmp1);
			visitZ3Expr(e.getArgs()[1], tmp2);
			parts.add(tmp1.get(0) + " > " + tmp2.get(0));
		} else if (e.isLT()) {
			List<String> tmp1 = new ArrayList<String>();
			List<String> tmp2 = new ArrayList<String>();
			visitZ3Expr(e.getArgs()[0], tmp1);
			visitZ3Expr(e.getArgs()[1], tmp2);
			parts.add(tmp1.get(0) + " < " + tmp2.get(0));
		} else if (e.isLE()) {
			List<String> tmp1 = new ArrayList<String>();
			List<String> tmp2 = new ArrayList<String>();
			visitZ3Expr(e.getArgs()[0], tmp1);
			visitZ3Expr(e.getArgs()[1], tmp2);
			parts.add(tmp1.get(0) + " <= " + tmp2.get(0));
		} else if (e.isEq()) {
			List<String> tmp1 = new ArrayList<String>();
			List<String> tmp2 = new ArrayList<String>();
			visitZ3Expr(e.getArgs()[0], tmp1);
			visitZ3Expr(e.getArgs()[1], tmp2);
			parts.add(tmp1.get(0) + " == " + tmp2.get(0));
		} else if (e.isAdd()) {
			List<String> tmp1 = new ArrayList<String>();
			List<String> tmp2 = new ArrayList<String>();
			visitZ3Expr(e.getArgs()[0], tmp1);
			visitZ3Expr(e.getArgs()[1], tmp2);
			parts.add("(" + tmp1.get(0) + "+" + tmp2.get(0) + ")");
		} else if (e.isSub()) {
			List<String> tmp1 = new ArrayList<String>();
			List<String> tmp2 = new ArrayList<String>();
			visitZ3Expr(e.getArgs()[0], tmp1);
			visitZ3Expr(e.getArgs()[1], tmp2);
			parts.add("(" + tmp1.get(0) + "-" + tmp2.get(0) + ")");
		} else if (e.isMul()) {
			List<String> tmp1 = new ArrayList<String>();
			List<String> tmp2 = new ArrayList<String>();
			visitZ3Expr(e.getArgs()[0], tmp1);
			visitZ3Expr(e.getArgs()[1], tmp2);
			parts.add("(" + tmp1.get(0) + "*" + tmp2.get(0) + ")");
		} else if (e.isDiv()) {
			List<String> tmp1 = new ArrayList<String>();
			List<String> tmp2 = new ArrayList<String>();
			visitZ3Expr(e.getArgs()[0], tmp1);
			visitZ3Expr(e.getArgs()[1], tmp2);
			parts.add("(" + tmp1.get(0) + "/" + tmp2.get(0) + ")");
		} else if (e.isNot()) {
			List<String> tmp = new ArrayList<String>();
			visitZ3Expr(e.getArgs()[0], tmp);
			parts.add("!(" + tmp.get(0) + ")");
		} else if (e.isTrue()) {
			parts.add("\\true");
		} else if (e.isFalse()) {
			parts.add("\\false");
		} else if (e.isNumeral()) {
			if (e.isRatNum()) {
				RatNum rational = (RatNum) e;
				IntNum num = rational.getNumerator(), den = rational.getDenominator();
				parts.add("" + ((double) num.getInt() / den.getInt()));
			} else {
				parts.add(e.getSExpr());
			}
		} else if (e.isConst()) {
			parts.add(e.getSExpr());
		} else if (e.isVar()) {
			parts.add(e.getString());
		} else if (e.isApp()) {
			System.out.println(e.getFuncDecl().getName() + " currently not supported!");
		}
	}

	class ExpressionVisitor extends folBaseVisitor<Expr> {

		@Override
		public Expr visitCondition(folParser.ConditionContext ctx) {
			if (ctx.formula() != null) {
				return visitFormula(ctx.formula());
			}
			return null;
		}

		@Override
		public Expr visitFormula(folParser.FormulaContext ctx) {
			if (ctx.formula() != null) { // more than one formula in this context
				return visitFormula(ctx.formula());
			} else if (ctx.connectiveformula() != null) {
				return visitConnectiveformula(ctx.connectiveformula());
			} else if (ctx.quantifier() != null) {
				return visitQuantifier(ctx.quantifier());
			} else {
				return null;
			}
		}

		@Override
		public Expr visitHascondition(folParser.HasconditionContext ctx) {
			String name = "has_" + ctx.STRING().getText().split("\"", 3)[1];
			name = name.replace(" ", "");
			BoolExpr expr = getContext().mkBoolConst(name);
			return expr;
		}

		@Override
		public Expr visitQuantifier(folParser.QuantifierContext ctx) {
			Expr[] bound = new Expr[ctx.IDENTIFIER().size()];
			for (int i = 0; i < bound.length; i++) {
				bound[i] = getContext().mkRealConst(ctx.IDENTIFIER(i).getText());
			}
			BoolExpr body = (BoolExpr) visitFormula(ctx.formula());
			switch (ctx.QUANTIFER().getText()) {
			case "\\forall":
				return getContext().mkForall(bound, body, 1, null, null, null, null);
			case "\\exists":
				return getContext().mkExists(bound, body, 1, null, null, null, null);
			default:
				return null;
			}
		}

		@Override
		public Expr visitConnectiveformula(folParser.ConnectiveformulaContext ctx) {
			BoolExpr result;
			if (ctx.boolexpression() != null) {
				result = (BoolExpr) visitBoolexpression(ctx.boolexpression());
				int i;
				for (i = 0; i < ctx.connectiveformula().size(); i++) {
					BoolExpr expr = (BoolExpr) visitConnectiveformula(ctx.connectiveformula(i));
					switch (ctx.connectoperator().get(i).getText()) {
					case "&":
						/** AND OPERATOR **/
					case "&&":
					case "and":
						result = getContext().mkAnd(result, expr);
						break;
					case "|":
						/** OR OPERATOR **/
					case "||":
					case "or":
						result = getContext().mkOr(result, expr);
						break;
					case "=>":
						/** IMPLIES OPERATOR **/
						result = getContext().mkImplies(result, expr);
						break;
					case "<=>":
						/** BICONDITION OPERATOR **/
						result = getContext().mkAnd(getContext().mkImplies(result, expr),
								getContext().mkImplies(expr, result));
						break;
					default:
						return null;
					}
				}
			} else {
				result = (BoolExpr) visitConnectiveformula(ctx.connectiveformula(0));
				if (ctx.NOT() != null) {
					result = getContext().mkNot(result);
				}
				int i;
				for (i = 1; i < ctx.connectiveformula().size(); i++) {
					BoolExpr expr = (BoolExpr) visitConnectiveformula(ctx.connectiveformula(i));
					switch (ctx.connectoperator().get(i - 1).getText()) {
					case "&":
						/** AND OPERATOR **/
					case "&&":
					case "and":
						result = getContext().mkAnd(result, expr);
						break;
					case "|":
						/** OR OPERATOR **/
					case "||":
					case "or":
						result = getContext().mkOr(result, expr);
						break;
					case "=>":
						/** IMPLIES OPERATOR **/
						result = getContext().mkImplies(result, expr);
						break;
					case "<=>":
						/** LOGICAL EQUIVALENCE **/
						result = getContext().mkAnd(getContext().mkImplies(result, expr),
								getContext().mkImplies(expr, result));
						break;
					default:
						return null;
					}
				}
			}
			return result;
		}

		@Override
		public Expr visitBoolexpression(folParser.BoolexpressionContext ctx) {
			BoolExpr result;
			if (ctx.quantifier() != null) {
				result = (BoolExpr) visitQuantifier(ctx.quantifier());
			} else if (ctx.compareformula() != null) {
				result = (BoolExpr) visitCompareformula(ctx.compareformula());
			} else if (ctx.TRUE() != null) {
				result = getContext().mkTrue();
			} else if (ctx.FALSE() != null) {
				result = getContext().mkFalse();
			} else if (ctx.hascondition() != null) {
				result = (BoolExpr) visitHascondition(ctx.hascondition());
			} else if (ctx.boolexpression() != null) {
				result = (BoolExpr) visitBoolexpression(ctx.boolexpression());
				if (ctx.NOT() != null) {
					result = getContext().mkNot(result);
				}
			} else {
				result = null;
			}

			return result;
		}

		@Override
		public Expr visitCompareformula(folParser.CompareformulaContext ctx) {
			BoolExpr result = null;
			if (ctx.compareformula() != null) {
				return visitCompareformula(ctx.compareformula());
			}

			ArithExpr first = (ArithExpr) visitMathematicalExpression(ctx.mathematicalExpression(0));
			for (int i = 1; i < ctx.mathematicalExpression().size(); i++) {
				ArithExpr expr = (ArithExpr) visitMathematicalExpression(ctx.mathematicalExpression(i));
				if (first != null && expr != null) {
					switch (ctx.compoperator(i - 1).getText()) {
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
					case "=":
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
			}
			return result;
		}

		@Override
		public Expr visitMathematicalExpression(folParser.MathematicalExpressionContext ctx) {
			if (ctx.term() != null) {
				// if (ctx.MINUS() != null) {
				if (!ctx.MINUS().isEmpty()) {
					return getContext().mkUnaryMinus((ArithExpr) visitTerm(ctx.term()));
				}
				return visitTerm(ctx.term());
			}

			if (ctx.mathematicalExpression() != null) {
				ArithExpr left = (ArithExpr) visitMathematicalExpression(ctx.mathematicalExpression(0));
				if (ctx.mathematicalExpression().size() == 2) {
					ArithExpr right = (ArithExpr) visitMathematicalExpression(ctx.mathematicalExpression(1));
					ArithExpr result = null;
					switch (ctx.getChild(1).getText()) {
					case "^":
						result = getContext().mkPower(left, right);
						break;
					case "*":
						result = getContext().mkMul(left, right);
						break;
					case "/":
						result = getContext().mkDiv(left, right);
						break;
					case "+":
						result = getContext().mkAdd(left, right);
						break;
					case "-":
						result = getContext().mkSub(left, right);
						break;
					default:
						result = null;
					}
					return result;
				} else if (ctx.mathematicalExpression().size() == 1) {
					return left;
				}
			}
			return null;
		}

//		@Override
//		public Expr visitSummformula(folParser.SummformulaContext ctx) {
//			if (ctx.faktorformula() == null) {
//				return visitSummformula(ctx.summformula(0));
//			}
//			ArithExpr result = (ArithExpr) visitFaktorformula(ctx.faktorformula());
//			for (int i = 0; i < ctx.summformula().size(); i++) {
//				ArithExpr expr = (ArithExpr) visitSummformula(ctx.summformula(i));
//				switch (ctx.addoperator(i).getText()) {
//				case "+":
//					result = getContext().mkAdd(result, expr);
//					break;
//				case "-":
//					result = getContext().mkSub(result, expr);
//					break;
//				default:
//					result = null;
//				}
//			}
//			return result;
//		}

//		@Override
//		public Expr visitFaktorformula(folParser.FaktorformulaContext ctx) {
//			if (ctx.powerformula() == null) {
//				return visitFaktorformula(ctx.faktorformula(0));
//			}
//			ArithExpr result = (ArithExpr) visitPowerformula(ctx.powerformula());
//			for (int i = 0; i < ctx.faktorformula().size(); i++) {
//				ArithExpr expr = (ArithExpr) visitFaktorformula(ctx.faktorformula(i));
//				switch (ctx.multoperator(i).getText()) {
//				case "*":
//					result = getContext().mkMul(result, expr);
//					break;
//				case "/":
//					result = getContext().mkDiv(result, expr);
//					break;
//				default:
//					result = null;
//				}
//			}
//			return result;
//		}

//		@Override
//		public Expr visitPowerformula(folParser.PowerformulaContext ctx) {
//			if (ctx.term() == null) {
//				return visitPowerformula(ctx.powerformula(0));
//			}
//			ArithExpr result = (ArithExpr) visitTerm(ctx.term());
//			for (int i = 0; i < ctx.powerformula().size(); i++) {
//				ArithExpr expr = (ArithExpr) visitPowerformula(ctx.powerformula(i));
//				result = getContext().mkPower(result, expr);
//			}
//			return result;
//		}

		@Override
		public Expr visitTerm(folParser.TermContext ctx) {
			if (ctx.term() != null) {
				if (ctx.MINUS() != null) {
					return getContext().mkSub(getContext().mkReal(0), (ArithExpr) visitTerm(ctx.term()));
				}
				return visitTerm(ctx.term());
			} else if (ctx.variable() != null) {
				return getContext().mkRealConst(ctx.variable().getText());
			} else if (ctx.SCIENTIFIC_NUMBER() != null) {
				return getContext().mkReal(ctx.SCIENTIFIC_NUMBER().getText());
			} else if (ctx.functioncall() != null) {
				return visitFunctioncall(ctx.functioncall());
			} else {
				return null;
			}
		}

		@Override
		public Expr visitFunctioncall(folParser.FunctioncallContext ctx) {
			List<Expr> results = new ArrayList<>();
			for (MathematicalExpressionContext expr : ctx.mathematicalExpression()) {
				Expr resultExpr = visitMathematicalExpression(expr);
				results.add(resultExpr);
			}
			if (ctx.prefix() != null && ctx.prefix().getText().equals("\\")) {
				for (KnownFunctions function : KnownFunctions.values()) {
					if (function.name().equalsIgnoreCase(ctx.functionname().getText())) { // known function
						if (ctx.mathematicalExpression().size() == function.getNumOfParameters()
								&& ctx.formula().size() == 0) {
							RealSort range = getContext().mkRealSort();
							RealSort[] domain = new RealSort[] { getContext().mkRealSort(), getContext().mkRealSort() };
							switch (function) {
							case min:
								BoolExpr minExpr = getContext().mkLt((ArithExpr) results.get(0),
										(ArithExpr) results.get(1));
								String minResult = minExpr.simplify().toString();
								if (minResult.equals("true")) {
									return results.get(0);
								} else if (minResult.equals("false")) {
									return results.get(1);
								} else {
									return getContext().mkApp(getContext().mkFuncDecl("min", domain, range),
											results.toArray(new Expr[0]));
								}
							case max:
								BoolExpr maxExpr = getContext().mkGt((ArithExpr) results.get(0),
										(ArithExpr) results.get(1));
								String maxResult = maxExpr.simplify().toString();
								if (maxResult.equals("true")) {
									return results.get(0);
								} else if (maxResult.equals("false")) {
									return results.get(1);
								} else {
									return getContext().mkApp(getContext().mkFuncDecl("max", domain, range),
											results.toArray(new Expr[0]));
								}
							case abs:
								BoolExpr absExpr = getContext().mkLt((ArithExpr) results.get(0),
										getContext().mkReal(0));
								if (absExpr.simplify().toString().equals("true")) {
									return getContext().mkUnaryMinus((ArithExpr) results.get(0));
								} else if (absExpr.simplify().toString().equals("false")) {
									return results.get(0);
								} else {
									return getContext().mkApp(getContext().mkFuncDecl("abs", domain[0], range),
											results.toArray(new Expr[0]));
								}
							default:
								return getContext().mkApp(getContext()
										.mkFuncDecl(ctx.functionname().getText().toLowerCase(), domain[0], range),
										results.toArray(new Expr[0]));
							}

						}
					}
				}
				SyntaxErrorListener.INSTANCE.syntaxError(null, ctx, 0, 0,
						"parameter syntax error in known function -" + ctx.functionname().getText(),
						new RecognitionException(null, null, ctx));
				return getContext().mkReal(0); //
			} else {
				Sort[] domain = new Sort[results.size()];
				for (int i = 0; i < results.size(); i++) {
					domain[i] = results.get(i).getSort();
				}

				return getContext().mkApp(getContext().mkFuncDecl(ctx.functionname().getText().toLowerCase(), domain,
						getContext().mkRealSort()), results.toArray(new Expr[0])); // Placeholder return type
			}
		}
	}

}
