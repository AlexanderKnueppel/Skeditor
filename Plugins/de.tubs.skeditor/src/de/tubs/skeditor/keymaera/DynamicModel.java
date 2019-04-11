package de.tubs.skeditor.keymaera;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DynamicModel {
	private Set<ValuedParameter> valuedParameters = new HashSet<ValuedParameter>();
	private Map<String, Expression> equations = new HashMap<String, Expression>();
	private Set<String> variables = new HashSet<String>();

	private String template = "";

	public DynamicModel(String template) {
		this.template = template;
	}

	private SymbolicMatrix parseMatrix(String str) { // [[a,b],[c,d]]
		str = str.trim().replaceAll(" ", "");

		String[] spl = str.split("\\],\\[");

		SymbolicMatrix sm = new SymbolicMatrix();

		for (String row : spl) {
			SymbolicVector v = new SymbolicVector();
			row = row.replace("[", "").replace("]", "");
			for (String el : row.split(",")) {
				v.addExpression(new Expression(el));
			}
			sm.addRow(v);
		}

		if (!str.trim().startsWith("[["))
			sm = sm.transpose();

		return sm;
	}

	public void parse(String expression) throws ParsingException {
		String[] rl = expression.split("=");
		// System.out.println(expression);
		if (rl.length != 2)
			throw new ParsingException("Assignment ('=') missing!");

		SymbolicMatrix sm = parseMatrix(rl[0]);
		List<SymbolicMatrix> matrices = new ArrayList<SymbolicMatrix>();

		// Semantic blow up
		rl[1] = rl[1].replace(" ", "").replace("]-", "]+(-1)*");
		rl[1] = rl[1].replace("]+", "]++");
		rl[1] = rl[1].replace("*[", "**[");

		String[] terms = rl[1].split("\\+\\+"); // get addition first

		for (String summand : terms) {
			// multiplication
			String[] factors = summand.split("\\*\\*");

			SymbolicMatrix eval = null;
			int counter = 1;
			if (factors.length > 1 && isScalar(factors[0])) {
				eval = parseMatrix(factors[1]).multiply(new Expression(factors[0]));
				counter = 2;
			} else {
				eval = parseMatrix(factors[0]);
			}

			for (int i = counter; i < factors.length; ++i) {
				eval = eval.multiply(parseMatrix(factors[i]));
			}

			matrices.add(eval); // should be vectors for simple models
		}

		SymbolicMatrix sm2 = matrices.get(0);
		for (int i = 1; i < matrices.size(); ++i) {
			sm2 = sm2.addMatrix(matrices.get(i));
		}

		for (int i = 0; i < sm.rows(); ++i) {
			equations.put(sm.get(i, 0).toString(), sm2.get(i, 0));
		}

		// Debug test

		// for (Entry<String, Expression> e : equations.entrySet()) {
		// System.out.println(e.getKey() + " = " + e.getValue());
		// }
	}

	private boolean isScalar(String string) {
		// TODO Auto-generated method stub
		return !string.matches("\\[(.*)\\]");
	}

	public void addConstant(ValuedParameter c) {
		valuedParameters.add(c);
	}

	public void addVariable(String v) {
		variables.add(v);
	}

	public void addVariable(String v, String lowBound, String highBound) {
		ValuedParameter var = new ValuedParameter(v, lowBound, "", highBound, true);
		valuedParameters.add(var);
	}

	public void addVariable(String v, String lowBound, String highBound, String initialValue) {
		ValuedParameter var = new ValuedParameter(v, lowBound, "", highBound, true, initialValue);
		valuedParameters.add(var);
	}

	public void addVariable(String v, String initialValue) {
		ValuedParameter var = new ValuedParameter(v, initialValue, "", true);
		valuedParameters.add(var);
	}

	public String createKeYmaeraProgram(String precondition, String postcondition) throws IOException {
		String contents = template;
		if (contents.equals("")) {
			contents = Templater.getKeepDistanceTemplate();
		}

		String consts = "";
		String constvalues = "";
		String and = "";
		for (ValuedParameter c : valuedParameters) {
			if (c.isVar()) {
				variables.add(c.getName());
			} else {
				consts += c.declaration() + "\n  ";
			}
			if (!c.toString().equals("")) {
				constvalues += and + c.toString();
				and = " & ";
			}

		}

		String dynamic = "";
		for (Entry<String, Expression> e : equations.entrySet()) {
			dynamic += e.getKey() + " = " + e.getValue() + ", ";
			variables.addAll(e.getValue().getSymbols());
		}

		for (ValuedParameter para : valuedParameters) {
			if (!para.isVar()) {
				variables.remove(para.getName());
			}
		}

		String vars = "";
		for (String v : variables) {
			vars += "R " + v + ".\n  ";
		}

		dynamic = dynamic.substring(0, dynamic.length() - 2);

		// Replacement
		contents = contents.replace("%%constants%%", consts);
		contents = contents.replace("%%variables%%", vars);
		contents = contents.replace("%%constvalues%%", constvalues);
		contents = contents.replace("%%dynamic%%", dynamic);
		contents = contents.replace("%%precondition%%", precondition);
		contents = contents.replace("%%postcondition%%", postcondition);
		//System.out.println(contents);
		return contents;
	}

	public String createKeYmaeraProgramDepcrecated(String precondition, String postcondition) throws IOException {
		String contents = template;
		if (contents.equals("")) {
			contents = Templater.getTemplate();
		}

		String consts = "";
		for (ValuedParameter c : valuedParameters) {
			consts += "R " + c.getName() + ".\n";
		}

		String initial = "";
		for (ValuedParameter c : valuedParameters) {
			initial += c.getName() + " = " + c.getValue() + " & ";
		}
		if (initial.length() >= 3)
			initial = initial.substring(0, initial.length() - 3);

		String bounds = "";

		String vars = "";
		for (String v : variables) {
			vars += "R " + v + ".\n";
		}

		String program = "";
		for (String v : variables) {
			program += v + " := *;\n";
		}

		String dynamic = "";
		for (Entry<String, Expression> e : equations.entrySet()) {
			dynamic += e.getKey() + " = " + e.getValue() + ",\n";
		}
		dynamic = dynamic.substring(0, dynamic.length() - 2);

		// Replacement
		contents = contents.replace("%%init%%", initial);
		contents = contents.replace("%%constants%%", consts);
		contents = contents.replace("%%variables%%", vars);
		contents = contents.replace("%%bounds%%", bounds);
		contents = contents.replace("%%program%%", program);
		contents = contents.replace("%%dynamic%%", dynamic);
		contents = contents.replace("%%precondition%%", precondition);
		contents = contents.replace("%%postcondition%%", postcondition);

		return contents;
	}

	public void addEquation(String eq) {
		String[] eqSplit = eq.split("=");
		equations.put(eqSplit[0], new Expression(eqSplit[1]));
	}
}
