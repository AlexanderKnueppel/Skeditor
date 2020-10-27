package de.tubs.skeditor.views.variableview;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import SkillGraph.Node;

public class LabelProvider implements ITableLabelProvider{

	

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		Node node = (Node) element;
		String defined = "";
		String required = "";
		for(String var : node.getProvidedVariables()) {
			if(defined.length() == 0) {
				defined += var;
			} else {
				defined += ", "+var;
			}
		}
		for(String var : node.getRequiredVariables()) {
			if(required.length() == 0) {
				required += var;
			} else {
				required += ", "+var;
			}
		}
		switch(columnIndex) {
		case 0:
			return node.getName();
		case 1:
			return defined;
		case 2:
			return required;
		default:
			return null;
		}
	}
}
