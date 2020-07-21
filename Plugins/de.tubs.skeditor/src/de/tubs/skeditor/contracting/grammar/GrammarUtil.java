package de.tubs.skeditor.contracting.grammar;

import java.util.Set;

import org.antlr.v4.runtime.CommonTokenStream;
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
	    walker.walk(listener,tree);
		
		return listener.getVariables();
	}
	
	public static void main(String[] args) {
		System.out.println(getVariables("A<5 & b > 7 & true & c = d & x -> y | VBNM"));
	}
}
