package de.tubs.skeditor.propagator;

import java.util.stream.Collectors;

import SkillGraph.Node;
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
}
