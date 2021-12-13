package de.tubs.skeditor.keymaera.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import SkillGraph.Graph;
import SkillGraph.Node;
import SkillGraph.Parameter;
import de.tubs.skeditor.HMA.HybridModeAutomaton;
import de.tubs.skeditor.HMA.composition.Resolver;
import de.tubs.skeditor.HMA.utils.HMAUtil;
import de.tubs.skeditor.keymaera.generator.evaluator.Visitor;
import de.tubs.skeditor.sdl.SDLModel;
import de.tubs.skeditor.ui.handler.SkillDescriptionLanguageHandler;
import de.tubs.skeditor.utils.ParameterUtil;

public class ProblemGenerator {

//	public static String generate(Graph graph, Node skill) {
//	}
	// TODO assert
	// + abstractions
	public static class Configuration {
		//... LOOP, PAST, ABSTRACTION etc
	}

	public static String generateMonolithicProblemForSkill(Node skill) {
		List<HybridModeAutomaton> hmas = HMAUtil.createHMAsForSkillAndBelow(skill);
		if (hmas.isEmpty())
			return "(Nothing)";

		HybridModeAutomaton finalHMA = Resolver.resolveAllSubmodes(hmas.get(0),
				hmas.toArray(new HybridModeAutomaton[0]));
		
		//if(abstraction)
		//finalHMA = Resolver.abstractSubmodes(hmas.get(0), skill);
		finalHMA = Resolver.abstractSubmodes(finalHMA, skill);
		
		Graph g = (Graph)skill.eContainer();

		String result = "ArchiveEntry \"" + skill.getName() + "-Monolithic\"\n\n";
		result += getDefinitions(finalHMA, g) + "\n";
		result += getProgramVariables(finalHMA, g) + "\n";
		
		SDLModel model = null;
		try {
			model = SkillDescriptionLanguageHandler.textToModel(skill.getSDLModel());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Visitor visitor = new Visitor();
		
		// Ranges of parameters
		List<String> ranges = new ArrayList<String>();
		for(Parameter p : g.getParameterList()) {
			if(p.getDefaultValue() != "") {
				ranges.add(ParameterUtil.createBounds(p.getAbbreviation(), p.getDefaultValue()));
			}
		}
		
		result += "Problem\n";
		result += visitor.doSwitch(model.getSkill().getAssumption().getFormula()) + getPastAssumption(finalHMA)
				+ " & (" + String.join(" & ", ranges) + ")"
				+ " & " + SDLTranslator.ASSERT_ID + " = 0 ->\n";
		result += "[\n";
		result += HMATranslator.translateHMA(finalHMA);
		result += "] ";
		result += "(" + visitor.doSwitch(model.getSkill().getGuarantee().getFormula()) + " & " + SDLTranslator.ASSERT_ID + " = 0)\n\nEnd.\nEnd.";

		return result;
	}

	private static String getPastAssumption(HybridModeAutomaton A) {
		String result = "";
		Set<String> variables = new HashSet<String>();
		variables.addAll(A.getInputs());
		variables.addAll(A.getOutputs());
		for (String variable : variables) {
			result += " & " + variable + "=" + variable + "past";
		}
		return result;
	}

	private static String getDefinitions(HybridModeAutomaton A, Graph g) {
		String result = "Definitions\n";
		for (String parameter : A.getParameters()) {
			result += "\t Real " + parameter + ";\n";
		}
		for(Parameter p : g.getParameterList()) {
			if(ParameterUtil.isGlobalParameter(p.getAbbreviation()) && !p.isVariable()) {
				if(!A.getParameters().contains(p.getAbbreviation()))
					result += "\t Real " + p.getAbbreviation() + ";\n";
			}
		}
		result += "End.\n";
		return result;
	}

	private static String getProgramVariables(HybridModeAutomaton A, Graph g) {
		String result = "ProgramVariables\n";
		Set<String> variables = new HashSet<String>();
		variables.addAll(A.getInputs());
		variables.addAll(A.getOutputs());
		for (String variable : variables) {
			result += "\t Real " + variable + ";\n";
			result += "\t Real " + variable + "past;\n";
		}
		result += "\t Real state;\n";
		for(Parameter p : g.getParameterList()) {
			if(ParameterUtil.isGlobalParameter(p.getAbbreviation()) && p.isVariable()) {
				if(!variables.contains(p.getAbbreviation()))
					result += "\t Real " + p.getAbbreviation() + ";\n";
			}
		}
		result += "\t Real " + SDLTranslator.ASSERT_ID + ";\n";
		result += "End.\n";
		return result;
	}
}
