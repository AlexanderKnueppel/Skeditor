package de.tubs.skeditor.contracting.grammar;

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
		
		try {
			System.out.println(tryToParse("A<5 & b > 7 & true & c = d & x -> y | 12 > VBNM"));
			
		} catch(ParseCancellationException e) {
			System.out.println(e.getMessage());
		}
		
	}
}
