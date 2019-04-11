package de.tubs.skeditor.views.dynamicmodelview;

import java.util.ArrayList;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IStructuredContentProvider;

import SkillGraph.Equation;
import SkillGraph.Node;
import SkillGraph.Requirement;
import de.tubs.skeditor.utils.GraphUtil;

public class ContentProvider implements IStructuredContentProvider {
	@Override
	public Object[] getElements(Object input) {
		Node node = (Node) input;
		EList<Equation> eqList = new BasicEList<Equation>();
		eqList.addAll(node.getEquations());
		eqList.addAll(GraphUtil.getPropagatedEquations(node));
		EList<Requirement> preCondList = GraphUtil.getPreConditionRequirements(node);
		EList<Requirement> postCondList = node.getRequirements();
		
		int listSize = Math.max(eqList.size(), Math.max(preCondList.size(), postCondList.size()));

		ArrayList<DynamicModelRow> rowList = new ArrayList<DynamicModelRow>();
		for (int i = 0; i < listSize; i++) {
			DynamicModelRow diffRow = new DynamicModelRow(getterUtil(eqList, i), getterUtil(i, preCondList), getterUtil(i, postCondList), node);
			if (i == 0) {
				diffRow.setProgramPath(node.getProgramPath());
			}
			if(getterUtil(eqList, i) != null && !getterUtil(eqList, i).getNode().equals(node)) {
				diffRow.setEditable(false);
			}
			rowList.add(diffRow);
		}
		return rowList.toArray();
	}

	private Equation getterUtil(EList<Equation> equationList, int index) {
		try {
			return equationList.get(index);
		} catch (Exception e) {
			return null;
		}
	}

	private Requirement getterUtil(int index, EList<Requirement> reqList) { // TODO overloading is not very nicely done
		try {
			return reqList.get(index);
		} catch (Exception e) {
			return null;
		}
	}
}
