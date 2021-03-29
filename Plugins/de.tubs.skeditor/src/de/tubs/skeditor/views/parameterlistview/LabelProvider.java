package de.tubs.skeditor.views.parameterlistview;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import SkillGraph.Parameter;

public class LabelProvider implements ITableLabelProvider {
	private ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources());
	private final ImageDescriptor CHECKED = getImageDescriptor("checked.png");
	private final ImageDescriptor UNCHECKED = getImageDescriptor("unchecked.png");

	private static ImageDescriptor getImageDescriptor(String file) {
		// assume that the current class is called View.java
		Bundle bundle = FrameworkUtil.getBundle(LabelProvider.class);
		URL url = FileLocator.find(bundle, new Path("icons/" + file), null);
		return ImageDescriptor.createFromURL(url);
	}

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
		if (columnIndex == 4) {
			Parameter parameter = (Parameter) element;
			if (parameter.isVariable()) {
				return resourceManager.createImage(CHECKED);
			} else {
				return resourceManager.createImage(UNCHECKED);
			}
		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		Parameter parameter = (Parameter) element;
		switch (columnIndex) {
		case 0:
			return parameter.getName();
		case 1:
			return parameter.getAbbreviation();
		case 2:
			return parameter.getUnit();
		case 3:
			return String.valueOf(parameter.getDefaultValue());
		case 4:
			return parameter.isVariable() ? "Var." : "Const.";
		default:
			return null;
		}
	}

}
