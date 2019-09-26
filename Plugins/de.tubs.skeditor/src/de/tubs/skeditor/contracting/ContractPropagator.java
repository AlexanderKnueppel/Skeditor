package de.tubs.skeditor.contracting;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import SkillGraph.Graph;
import SkillGraph.Node;
import SkillGraph.Parameter;
import de.tubs.skeditor.utils.GraphUtil;

public class ContractPropagator {

	public ContractPropagator() {
	}

	public static Contract computeContract(Node node) {
		String assume = "", safe = "";

		if (GraphUtil.getChildNodes(node).isEmpty()) {
			if(node.getRequirements().isEmpty()) {
				safe = "true";
			} else {
				safe = node.getRequirements().stream().map(n -> n.getTerm().toString())
				.collect(Collectors.joining(" & "));
			}
			return new Contract("true", safe);
		} else {
			assume = GraphUtil.getChildNodes(node).stream().map(n -> computeContract(n).getGuarantee())
					.collect(Collectors.joining(" & "));
			safe = assume;
			if (!node.getRequirements().isEmpty())
				safe += " & " + node.getRequirements().stream().map(n -> n.getTerm().toString())
						.collect(Collectors.joining(" & "));
		}

		return new Contract(assume, safe);
	}
	
	public static Set<String> getParameters(Graph g, String equation) {
		Set<String> result = new HashSet<String>();
		
		for(Parameter p : g.getParameterList()) {
			//needs parser right,...
		}
		
		return result;
	}
}
