package de.tubs.skeditor.views.infoview;

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
		InfoModelRow infoRow = (InfoModelRow) element;
		switch (columnIndex) {
		case 0:
			if (infoRow.getCategory() != null) {
				return infoRow.getCategory();
			}
			break;
		case 1:
			if (infoRow.getContent() != null) {
				return infoRow.getContent();
			}
			break;
		default:
			return "";
		}
		return "";
	}

}
