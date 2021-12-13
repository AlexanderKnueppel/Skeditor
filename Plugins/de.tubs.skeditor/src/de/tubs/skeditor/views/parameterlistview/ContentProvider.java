package de.tubs.skeditor.views.parameterlistview;

import java.util.Comparator;
import java.util.stream.Collectors;

import org.eclipse.jface.viewers.IStructuredContentProvider;

import SkillGraph.Graph;
import SkillGraph.Parameter;

public class ContentProvider implements IStructuredContentProvider {

	@Override
	public Object[] getElements(Object input) {
		Graph graph = (Graph) input;
		// Order: 1. parameters, 2. variables
		Comparator<Parameter> compareByType = Comparator.comparing(Parameter::isVariable);
		return graph.getParameterList().stream().sorted(compareByType).collect(Collectors.toList()).toArray();
	}

}
