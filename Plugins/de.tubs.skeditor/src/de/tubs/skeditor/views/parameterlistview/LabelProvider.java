package de.tubs.skeditor.views.parameterlistview;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableFontProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import SkillGraph.Parameter;
import de.tubs.skeditor.utils.ParameterUtil;

public class LabelProvider implements ITableLabelProvider, ITableFontProvider, ITableColorProvider {
	
	private ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources());
	private final ImageDescriptor CHECKED = getImageDescriptor("checked.png");
	private final ImageDescriptor UNCHECKED = getImageDescriptor("unchecked.png");

	private static ImageDescriptor getImageDescriptor(String file) {
		Bundle bundle = FrameworkUtil.getBundle(LabelProvider.class);
		URL url = FileLocator.find(bundle, new Path("icons/" + file), null);
		return ImageDescriptor.createFromURL(url);
	}

	@Override
	public void dispose() {}

	@Override
	public void addListener(ILabelProviderListener listener) {}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
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
		case 5:
			return parameter.isVariable() ? "-" : "-";
		default:
			return null;
		}
	}

	@Override
	public Color getForeground(Object element, int columnIndex) {
		Parameter parameter = (Parameter) element;
		if(parameter.getName().equals(AbbreviationEditingSupport.INVALID_NAME))
			return new Color(null, new RGB(255,64,64));
		else if(ParameterUtil.isGlobalParameter(parameter.getAbbreviation())) {
			return new Color(null, new RGB(65,105,225));
		}
		return null;
	}

	@Override
	public Color getBackground(Object element, int columnIndex) {
		Parameter parameter = (Parameter) element;
		if(parameter.isVariable()) {
			if(parameter.isAvailable() || ParameterUtil.isGlobalParameter(parameter.getAbbreviation()))
				return new Color(null, new RGB(230,230,230));
			else
				return new Color(null, new RGB(250,128,114));
		}
		return null;
	}

	@Override
	public Font getFont(Object element, int columnIndex) {
		return null;
	}

}
