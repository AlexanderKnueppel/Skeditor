package de.tubs.skeditor.property;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import SkillGraph.Node;

public class SkillPropertySource implements IPropertySource {

	private static final String NAME_ID = "name";
	private static final PropertyDescriptor NAME_PROP_DESC = new PropertyDescriptor(NAME_ID, "Name");
	private static final IPropertyDescriptor[] DESCRIPTORS = { NAME_PROP_DESC };

	private Node skill;

	public SkillPropertySource(Node skill) {
		super();
		this.skill = skill;
	}

	@Override
	public Object getEditableValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return DESCRIPTORS;
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (NAME_ID.equals(id)) {
			return skill.getName();
		}
		return null;
	}

	@Override
	public boolean isPropertySet(Object id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		// TODO Auto-generated method stub
	}
}
