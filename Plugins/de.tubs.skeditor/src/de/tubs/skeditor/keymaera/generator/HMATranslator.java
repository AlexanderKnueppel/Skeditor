package de.tubs.skeditor.keymaera.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import de.tubs.skeditor.HMA.HybridModeAutomaton;
import de.tubs.skeditor.HMA.Mode;
import de.tubs.skeditor.HMA.Transition;
import de.tubs.skeditor.keymaera.generator.evaluator.Visitor;
import de.tubs.skeditor.sdl.Assignment;

public class HMATranslator {
	public final static String STATE_VARIABLE = "state";

	public static String translateHMA(HybridModeAutomaton A) {
		HashMap<String, Integer> id = new HashMap<String, Integer>();

		A.getModes().stream().forEach(m -> id.put(m.getName(), id.size()));

		String result = "/* Translate " + A.getName() + " */\n";
		result += "{\n";
		result += "\t" + STATE_VARIABLE + ":=" + id.get(A.getStart().getName()) + "; /* Init: "+ A.getStart().getName() +"*/\n";
		/*Bindings*/
		int counter = 1;
		for(Assignment assg : A.getBindings()) {
			result += "\t" + SDLTranslator.translate(assg) + " /* Binding #"+ counter++ +"*/\n";
		}
		result += "\t" + "{\n";
		result += "\t" + translateTransitions(A, id);
		result += "\t" + translateModes(A, id);
		result += "\t" + "}*\n";
		result += "}\n";
		return result;
	}

	private static String translateModes(HybridModeAutomaton A, HashMap<String, Integer> id) {
		String result = "";
		List<String> parts = new ArrayList<String>();
		for (Mode m : A.getModes()) {
			String part = "\t\t  ?(" + STATE_VARIABLE + "=" + id.get(m.getName())+"); ";
			part += translatePast(A) + ";";
			part += translateMode(A,m);
			parts.add(part);
		}
		result = String.join("\t\t  ++\n", parts);
		return "\t{ /* Modes */\n" + result + "\n\t\t}\n";
	}

	private static String translateMode(HybridModeAutomaton A, Mode m) {
		String result = "";
//		for(Assignment assg : A.getBindings()) {
//			result += "\t\t\t" + SDLTranslator.translate(assg) + "\n";
//		}
		result += "\t\t\t" + SDLTranslator.translate(m.getController().getStmt()) + "\n";
		result += "\t\t\t" + "t:=0;\n";
		if(m.getDynamics() != null)
			result += "\t\t\t" + SDLTranslator.translate(m.getDynamics()) + "\n";
		return "\t{ /* Mode: " + m.getName() + "*/\n" + result + "\t\t  }\n";
	}

	private static String translatePast(HybridModeAutomaton A) {
		HashSet<String> variables = new HashSet<String>();
		variables.addAll(A.getOutputs());
		variables.addAll(A.getInputs());
		List<String> parts = new ArrayList<String>();
		for(String v : variables) {
			String part = v+"past := " + v;
			parts.add(part);
		}
		return String.join("; ", parts);
	}

	private static String translateTransitions(HybridModeAutomaton A, HashMap<String, Integer> id) {
		String result = "";
		List<String> parts = new ArrayList<String>();
		Visitor visitor = new Visitor();

		for (Transition t : A.getTransitions()) {
			String part = "\t\t  ?(" + STATE_VARIABLE + "=" + id.get(t.getFrom().getName()) + " & "
					+ visitor.caseFormula(t.getGuard()) + "); ";
			part += STATE_VARIABLE + ":=" + id.get(t.getTo().getName()); 
			parts.add(part + ";");
		}
		result = String.join("\n\t\t  ++\n", parts);
		return "\t{ /* Transitions */\n" + result + "\n\t\t}\n";
	}
}
