package de.tubs.skeditor.examples.wizards;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;

import de.tubs.skeditor.examples.utils.ProjectRecord;

class ExampleLabelProvider extends LabelProvider implements IColorProvider {

	private static final Color YELLOW = new Color(null, 183, 187, 11);
	private static final Color RED = new Color(null, 240, 0, 0);
	private static final Color BLACK = new Color(null, 0, 0, 0);
	private static final Color WHITE = new Color(null, 255, 255, 255);

	@Override
	public String getText(Object element) {
		if (element instanceof ProjectRecord.TreeItem) {
			return ((ProjectRecord.TreeItem) element).toString();
		} else if (element instanceof IPath) {
			return ((IPath) element).lastSegment();
		} else {
			return "";
		}
	}

	@Override
	public Color getForeground(Object element) {
		if (element instanceof ProjectRecord.TreeItem) {
			final ProjectRecord tmpRecord = ((ProjectRecord.TreeItem) element).getRecord();
			if (tmpRecord.hasErrors()) {
				return RED;
			} else if (tmpRecord.hasWarnings()) {
				return YELLOW;
			}
		}
		return BLACK;
	}

	@Override
	public Color getBackground(Object element) {
		return WHITE;
	}

}
