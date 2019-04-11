package de.tubs.skeditor.views.safetygoalsview;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import SkillGraph.Node;
import SkillGraph.Requirement;

public class LabelProvider implements ITableLabelProvider {

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
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof Node) {
			Node node = (Node) element;
			switch (columnIndex) {
			case 0:
				return node.getName();
			default:
				break;
			}
		} else if (element instanceof Requirement) {
			Requirement req = (Requirement) element;
			switch (columnIndex) {
			case 0:
				String name = "RS";
				for (int i = 0; i < req.getNode().getRequirements().size(); i++) {
					if (req.equals(req.getNode().getRequirements().get(i))) {
						name += (i + 1);
					}
				}
				return name;
			case 1:
				return req.getTerm();
			case 2:
				return req.getComment();
			case 3:
				return req.getType().getLiteral();
			default:
				break;
			}
		}

		return null;
	}

}
