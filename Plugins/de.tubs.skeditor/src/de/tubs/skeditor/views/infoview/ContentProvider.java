package de.tubs.skeditor.views.infoview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.jface.viewers.IStructuredContentProvider;

import SkillGraph.Node;
import SkillGraph.Parameter;
import de.tubs.skeditor.contracting.Contract;
import de.tubs.skeditor.contracting.ContractPropagator;
import de.tubs.skeditor.sdl.Field;
import de.tubs.skeditor.sdl.SDLModel;
import de.tubs.skeditor.ui.handler.SkillDescriptionLanguageHandler;
import de.tubs.skeditor.utils.GraphUtil;

public class ContentProvider implements IStructuredContentProvider {
	@Override
	public Object[] getElements(Object input) {
		Node node = (Node) input;

		ArrayList<InfoModelRow> rowList = new ArrayList<InfoModelRow>();

		// Name : Type
		rowList.add(new InfoModelRow("Name : Type", node.getName() + " : " + node.getCategory().getName(), node));
		// Defined variables
		rowList.add(new InfoModelRow("Defined variables", getDefinedVariables(node), node));
		// Required Variables
		rowList.add(new InfoModelRow("Required variables", getRequiredVariables(node), node));
		// Accessible variables
		Set<Parameter> s = GraphUtil.getAccessibleVariables(node);
		String str = "{" + String.join(", ", s.stream().map(x -> x.getAbbreviation()).collect(Collectors.toList()))
				+ "}";
		rowList.add(new InfoModelRow("Accessible variables", str, node));
		// Safety obligation
		rowList.add(new InfoModelRow("Safety obligation", getSafetyObligation(node), node));
		// Assumption (computed)
		// Guarantee (computed)
		Contract c = ContractPropagator.computeContract(node);
		rowList.add(new InfoModelRow("Assumption (computed)", c.getAssumption(), node));
		rowList.add(new InfoModelRow("Guarantee (computed)", c.getGuarantee(), node));

		return rowList.toArray();
	}

	private String getSafetyObligation(Node node) {
		return node.getRequirements().stream().map(n -> n.getTerm().toString()).collect(Collectors.joining(" & "));
	}

	private String getDefinedVariables(Node node) {
		if (node.getSDLModel() == null)
			return "{}";

		try {
			SDLModel model = SkillDescriptionLanguageHandler.textToModel(node.getSDLModel());
			if (model.getSkill().getVariables() != null && model.getSkill().getVariables().getProvides() != null) {
				List<Field> l = model.getSkill().getVariables().getProvides().getVariables();
				return "{" + String.join(", ", l.stream().map(e -> e.getName()).collect(Collectors.toList())) + "}";
			}
				

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "{}";
	}
	
	private String getRequiredVariables(Node node) {
		if (node.getSDLModel() == null)
			return "{}";

		try {
			SDLModel model = SkillDescriptionLanguageHandler.textToModel(node.getSDLModel());
			if (model.getSkill().getVariables() != null && model.getSkill().getVariables().getRequires() != null) {
				List<Field> l = model.getSkill().getVariables().getRequires().getVariables();
				return "{" + String.join(", ", l.stream().map(e -> e.getName()).collect(Collectors.toList())) + "}";
			}
				

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "{}";
	}
}
