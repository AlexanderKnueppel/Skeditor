package de.tubs.skeditor.synthesis.dialog;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

public class LabelProvider implements ITableLabelProvider{

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addListener(ILabelProviderListener listener) {
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
		return null;
	}
	
	@Override
	public String getColumnText(Object element, int columnIndex) {
		VariableRow row = (VariableRow) element;
		switch (columnIndex) {
		case 0:
			return row.getName();
		case 1:
			return row.getTypeAsString();
		default:
			return null;
		}
	}
}
