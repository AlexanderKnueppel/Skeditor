package de.tubs.skeditor.synthesis.dialog;

import java.util.ArrayList;

import org.eclipse.jface.viewers.IStructuredContentProvider;

import SkillGraph.Node;

public class VariableContentProvider implements IStructuredContentProvider {

	@Override
	public Object[] getElements(Object input) {
		Node node = (Node) input;
		ArrayList<VariableRow> rows = new ArrayList<VariableRow>();
		for(String req : node.getRequiredVariables()) {
			rows.add(new VariableRow(req, VariableRow.Type.REQUIRED));
		}
		for(String prov : node.getProvidedVariables()) {
			rows.add(new VariableRow(prov, VariableRow.Type.PROVIDED));
		}
		return rows.toArray();
	}

}
