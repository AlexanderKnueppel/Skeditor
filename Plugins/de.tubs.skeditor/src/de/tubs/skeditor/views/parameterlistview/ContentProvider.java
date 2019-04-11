package de.tubs.skeditor.views.parameterlistview;

import org.eclipse.jface.viewers.IStructuredContentProvider;

import SkillGraph.Graph;

public class ContentProvider implements IStructuredContentProvider {

	@Override
	public Object[] getElements(Object input) {
		Graph graph = (Graph) input;
		return graph.getParameterList().toArray();
	}

}
