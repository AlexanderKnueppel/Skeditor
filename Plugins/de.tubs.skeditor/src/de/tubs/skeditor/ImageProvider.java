package de.tubs.skeditor;

import org.eclipse.graphiti.ui.platform.AbstractImageProvider;

public class ImageProvider extends AbstractImageProvider {

	protected static final String PREFIX = "de.tubs.skeditor.";

	public static final String IMG_ARROW_HEAD = PREFIX + "arrowHead";
	public static final String IMG_EDIT_PEN = PREFIX + "editpen";

	public static final String IMG_MAIN = PREFIX + "main";
	public static final String IMG_OBSERVE = PREFIX + "observe";
	public static final String IMG_PERCEPTION = PREFIX + "perception";
	public static final String IMG_PLANNING = PREFIX + "planning";
	public static final String IMG_ACTION = PREFIX + "action";
	public static final String IMG_SENSOR = PREFIX + "sensor";
	public static final String IMG_ACTUATOR = PREFIX + "actuator";
	
	public static final String IMG_CHANGE_CAT = PREFIX + "change_category";
	public static final String IMG_SAFETY = PREFIX + "safety";

	@Override
	protected void addAvailableImages() {
		addImageFilePath(IMG_ARROW_HEAD, "icons/arrowhead.png");
		addImageFilePath(IMG_EDIT_PEN, "icons/editpen.png");

		addImageFilePath(IMG_MAIN, "icons/main.png");
		addImageFilePath(IMG_OBSERVE, "icons/observe.png");
		addImageFilePath(IMG_PERCEPTION, "icons/perception.png");
		addImageFilePath(IMG_PLANNING, "icons/planning.png");
		addImageFilePath(IMG_ACTION, "icons/action.png");
		addImageFilePath(IMG_SENSOR, "icons/sensor.png");
		addImageFilePath(IMG_ACTUATOR, "icons/actuator.png");

		addImageFilePath(IMG_CHANGE_CAT, "icons/change_category.png");
		addImageFilePath(IMG_SAFETY, "icons/safety.png");

	}

	public static String getImage(String name) {
		switch (name) {
		case "main":
			return IMG_MAIN;
		case "observable_external_behavior":
			return IMG_OBSERVE;
		case "perception":
			return IMG_PERCEPTION;
		case "planning":
			return IMG_PLANNING;
		case "action":
			return IMG_ACTION;
		case "sensor":
			return IMG_SENSOR;
		case "actuator":
			return IMG_ACTUATOR;
		default:
			return null;
		}
	}

}
