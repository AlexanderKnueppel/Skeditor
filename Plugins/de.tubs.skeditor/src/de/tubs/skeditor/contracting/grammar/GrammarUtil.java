package de.tubs.skeditor.contracting.grammar;

import com.microsoft.z3.*;

import java.util.ArrayList;
import java.util.List;

import java.util.Set;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.CharStreams;

public class GrammarUtil {
	public static Set<String> getVariables(String cond) {
		folLexer lexer = new folLexer(CharStreams.fromString(cond));
		folParser parser = new folParser(new CommonTokenStream(lexer));

		ParseTree tree = parser.formula();

		VariableListener listener = new VariableListener();

		ParseTreeWalker walker = new ParseTreeWalker();
		walker.walk(listener, tree);

		return listener.getVariables();
	}

	public static List<SyntaxError> tryToParse(String text) throws ParseCancellationException {
		folLexer lexer = new folLexer(CharStreams.fromString(text));
		
		SyntaxErrorListener errorListener = new SyntaxErrorListener();
		
		lexer.removeErrorListeners();
		lexer.addErrorListener(errorListener);

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		folParser parser = new folParser(tokens);
		parser.removeErrorListeners();
		parser.addErrorListener(errorListener);

		ParseTree tree = parser.condition();
		
		return errorListener.getSyntaxErrors();
	}

	public static void main(String[] args) {	
		
//		try {
//			System.out.println(tryToParse("A<5 & b > 7 & true & c = d & x -> y | 12 > VBNM"));
//			
//		} catch(ParseCancellationException e) {
//			System.out.println(e.getMessage());
//		}
		
		
		
//        System.out.println("Simplify Formula");
//        //Log.append("Simplify Formula");
//
//        {
//            Context ctx = new Context();
//            //Tactic css = ctx.mkTactic("ctx-solver-simplify");
//            
//            Solver s = ctx.mkSolver();
//            
//            //x == 5 && x == 6 && x == 7 && x == 8 && x > 5
//            
//            IntExpr x = ctx.mkIntConst("x");
//            
//            BoolExpr c1 = ctx.mkEq(x, ctx.mkInt(5));
//            BoolExpr c2 = ctx.mkEq(x, ctx.mkInt(6));
//            BoolExpr c3 = ctx.mkEq(x, ctx.mkInt(7));
//            BoolExpr c4 = ctx.mkEq(x, ctx.mkInt(8));
//            BoolExpr c5 = ctx.mkEq(x, ctx.mkInt(9));
//            
//            BoolExpr c6 = ctx.mkOr(c1,c2,c3,c4,c5);
//            BoolExpr c7 = ctx.mkAnd(c6, ctx.mkGt(x, ctx.mkInt(6)));
//            
//            Goal goal = ctx.mkGoal(true, false, false);
//            goal.add(c7);
//            
//            Tactic simplify = ctx.mkTactic("simplify");
//            Tactic ctxSimplify = ctx.mkTactic("ctx-simplify");
//            Tactic ctxSolverSimplify = ctx.mkTactic("ctx-solver-simplify");
//         
//            ApplyResult res1 = simplify.apply(goal);
//            ApplyResult res2 = ctxSimplify.apply(goal);
//            ApplyResult res3 = ctxSolverSimplify.apply(goal);
//            
//            
//            System.out.println("original term:\n" + c7 + "\n");
//            System.out.println("simplify:\n" + res1 + "\n");
//            System.out.println("ctxSimplify:\n" + res2 + "\n");
//            System.out.println("ctxSolverSimplify:\n" + res3 + "\n");
//            
//            for(Goal sub : res3.getSubgoals()) {
//            	s.add(sub.getFormulas());
//            }
//            
//        	List<String> conditions = new ArrayList<String>();
//        	for(BoolExpr expr : s.getAssertions()) {
//        		String cond = "";
//        		//expr.isG
//        	}
//            
//            if (s.check() == Status.SATISFIABLE)
//            	System.out.println("Model: " + s.getModel().toString());
//            else
//            	System.out.println("Not satisfiable!");
//            
//            ctx.close();
//        }
	}
}
