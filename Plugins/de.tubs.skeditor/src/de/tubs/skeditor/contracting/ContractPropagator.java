package de.tubs.skeditor.contracting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

import SkillGraph.Category;
import SkillGraph.Graph;
import SkillGraph.Node;
import SkillGraph.Parameter;
import SkillGraph.Requirement;
import de.tubs.skeditor.contracting.grammar.GrammarUtil;
import de.tubs.skeditor.contracting.grammar.Z3Converter;
import de.tubs.skeditor.utils.GraphUtil;

public class ContractPropagator {

	public ContractPropagator() {
	}

	private static boolean isSensorCategory(Node node) {
		Category[] categories = { Category.SENSOR, Category.PERCEPTION, Category.PLANNING };
		return Arrays.asList(categories).contains(node.getCategory());
	}

	public static Contract computeContract(Node node) {
		String assume = "", safe = "";

		if (GraphUtil.getChildNodes(node).isEmpty()) {
			if (node.getRequirements().isEmpty()) {
				safe = "\\true";
			} else {
				safe = node.getRequirements().stream().map(n -> n.getTerm().toString())
						.collect(Collectors.joining(" & "));
			}
			
			if (node.getAssumptions().isEmpty()) {
				assume = "\\true";
			} else {
				assume = node.getAssumptions().stream().map(n -> n.getTerm().toString())
						.collect(Collectors.joining(" & "));
			}
		} else {
			List<String> assumptions = new ArrayList<String>();
			List<String> guarantees = new ArrayList<String>();
			
			for (Node child : GraphUtil.getChildNodes(node)) {
				assumptions.add(computeContract(child).getAssumption());
				guarantees.add(computeContract(child).getGuarantee());
			}
			
			guarantees.addAll(node.getRequirements().stream().map(n -> n.getTerm().toString()).collect(Collectors.toList()));
			safe = String.join(" & ", guarantees);
			
			assumptions.addAll(node.getAssumptions().stream().map(n -> n.getTerm().toString()).collect(Collectors.toList()));
			
			assume = "("+safe+") => " + "(" + String.join(" & ", assumptions) + ")";
			
		}
		
		assume = assume.replaceAll("  ", " ").replaceAll("\\\\true & ", "").replaceAll(" & \\\\true", "").trim();
		safe = safe.replaceAll("  ", " ").replaceAll("\\\\true & ", "").replaceAll(" & \\\\true", "").trim();
		
		Z3Converter converter = new Z3Converter();
		assume = converter.simplifyFormula(assume);
		safe = converter.simplifyFormula(safe);
		
		if(assume.isEmpty())
			assume = "\\true";
		if(safe.isEmpty())
			safe = "\\true";
		
		return new Contract(assume, safe);
	}

//	public static Contract computeContract(Node node) {
//		String assume = "", safe = "";
//
//		if (GraphUtil.getChildNodes(node).isEmpty()) {
//			if (node.getRequirements().isEmpty()) {
//				safe = "\\true";
//			} else {
//				safe = node.getRequirements().stream().map(n -> n.getTerm().toString())
//						.collect(Collectors.joining(" & "));
//			}
//			return new Contract("\\true", safe);
//		} else {
//			List<String> guarantees = GraphUtil.getChildNodes(node).stream().map(n -> computeContract(n).getGuarantee()).collect(Collectors.toList());
//			
//			assume = guarantees.stream().collect(Collectors.joining(" & "));
//
//			List<String> validClauses = new ArrayList<String>();
//			for (String req : guarantees) {
//				for(String clause : req.split("&")) {
//					if (validClause(clause, node.getRequirements()))
//						validClauses.add(clause.trim());
//				}
//			}
//			
//			safe = validClauses.stream().collect(Collectors.joining(" & "));
//
//			if (!node.getRequirements().isEmpty())
//				safe += " & " + node.getRequirements().stream().map(n -> n.getTerm().toString())
//						.collect(Collectors.joining(" & "));
//		}
//
//		return new Contract(assume, safe);
//	}

	public static boolean validClause(String clause, List<Requirement> req) {
		// Clause is valid when no requirement overlaps with it (speaking of variables
		// here)
		return !req.stream().anyMatch(r -> Sets
				.intersection(GrammarUtil.getVariables(clause), GrammarUtil.getVariables(r.getTerm())).size() > 0);
	}
}
