package de.tubs.skeditor.views.dynamicmodelview;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

public class LabelProvider implements ITableLabelProvider {

	@Override
	public void dispose() {

	}

	@Override
	public void addListener(ILabelProviderListener listener) {

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {

	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if(columnIndex == 4) {
			
		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		DynamicModelRow diffRow = (DynamicModelRow) element;
		switch (columnIndex) {
		case 0:
			if (diffRow.getEquation() != null) {
				return diffRow.getEquation().getEquation();
			}
			break;
		case 1:
			if (diffRow.getPreCondition() != null) {
				return diffRow.getPreCondition().getTerm();
			}
			break;
		case 2:
			if (diffRow.getPostCondition() != null) {
				return diffRow.getPostCondition().getTerm();
			}
			break;
		case 3:
			if (diffRow.getProgramPath() != null) {
				return diffRow.getProgramPath();
			}
			break;
		default:
			return "";
		}
		return "";
	}

}
