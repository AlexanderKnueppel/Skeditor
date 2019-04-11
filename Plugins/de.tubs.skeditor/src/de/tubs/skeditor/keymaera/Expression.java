package de.tubs.skeditor.keymaera;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Expression {
	private Set<String> symbols = new HashSet<String>();;
	private String expr;

	private void parseSymbols(String expr) {
		String pattern = "*+/- ()=";
		String tmp = expr;
		for (Character c : pattern.toCharArray()) {
			tmp = tmp.replace(c.charValue(), ' ');
		}

		for (String symbol : tmp.split(" ")) {
			if (!symbol.trim().isEmpty())
				try {
					Double.parseDouble(symbol);
				} catch (Exception e) {
					symbols.add(symbol.trim());
				}
		}
	}

	private Expression(String expr, Set<String> symbols) {
		this.expr = expr;
		this.symbols = symbols;
	}

	public Expression(String expr) {
		this.expr = expr;
		parseSymbols(expr);
	}

	public Set<String> getSymbols() {
		return symbols;
	}

	public Expression simplify() {
		String str = expr;
		str = str.replace("+0", "");
		str = str.replace("-0", "");
		str = str.replace("-1*", "-");
		str = str.replace("+-", "-");
		str = str.replace("-+", "-");
		// what else??
		return new Expression(str);
	}

	private Expression concat(Expression right, String op) {
		return new Expression("(" + this.expr + ")" + op + "(" + right.expr + ")", Stream.concat(symbols.stream(), right.symbols.stream()).collect(Collectors.toSet()));
	}

	public Expression add(Expression right) {
		return concat(right, "+");
	}

	public Expression substract(Expression right) {
		return concat(right, "-");
	}

	public Expression multiply(Expression right) {
		if (this.toString().trim().equals("0") || right.toString().trim().equals("0"))
			return new Expression("0");

		return concat(right, "*");
	}

	public Expression divide(Expression right) {
		return concat(right, "/");
	}

	public String toString() {
		return expr;
	}

	public static void main(String[] args) {
		System.out.println(new Expression("beta' = -beta*(Caf+Car)/(M*v) + psi2*(M*v*v - (Car*Lr-Caf*Lf))/(M*v*v) - d*Caf/(M*v)").getSymbols());
	}
}
