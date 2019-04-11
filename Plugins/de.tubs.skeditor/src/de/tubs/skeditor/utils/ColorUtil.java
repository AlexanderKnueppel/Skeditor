package de.tubs.skeditor.utils;

import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;

/**
 * A utility class for color management of the editor
 * 
 * @author Enis
 *
 */
public class ColorUtil {

	public static IColorConstant getEdgeColor() {
		return IColorConstant.BLACK;
	}

	public static IColorConstant getCategoryColor(String color) {
		switch (color) {
		case "grey": // main
			return new ColorConstant(187, 187, 187);
		case "yellow": // observe
			return new ColorConstant(255, 255, 0);
		case "green": // perception
			return new ColorConstant(0, 230, 173);
		case "lightblue": // planning
			return new ColorConstant(102, 217, 255);
		case "orange": // action
			return new ColorConstant(255, 179, 0);
		case "blue": // sensor
			return new ColorConstant(0, 128, 255);
		case "red": // actuator
			return new ColorConstant(204, 51, 51);
		default:
			return new ColorConstant(45, 12, 98);
		}
	}

	public static String getCategoryColorRGB(String color) {
		String r, g, b;
		switch (color) {
		case "grey": // main
			r = Integer.toHexString(187);
			g = Integer.toHexString(187);
			b = Integer.toHexString(187);
			return r + g + b;
		case "yellow": // observe
			r = Integer.toHexString(255);
			g = Integer.toHexString(255);
			b = Integer.toHexString(0);
			b += "0";
			return r + g + b;
		// return "(255, 255, 0)";
		case "green": // perception
			r = Integer.toHexString(0);
			r += "0";
			g = Integer.toHexString(230);
			b = Integer.toHexString(173);
			return r + g + b;
		// return new ColorConstant(0, 230, 173);
		case "lightblue": // planning
			r = Integer.toHexString(102);
			g = Integer.toHexString(217);
			b = Integer.toHexString(255);
			return r + g + b;
		// return new ColorConstant(102, 217, 255);
		case "orange": // action
			r = Integer.toHexString(255);
			g = Integer.toHexString(179);
			b = Integer.toHexString(0);
			b += "0";
			return r + g + b;
		// return new ColorConstant(255, 179, 0);
		case "blue": // sensor
			r = Integer.toHexString(0);
			r += "0";
			g = Integer.toHexString(128);
			b = Integer.toHexString(255);
			return r + g + b;
		// return new ColorConstant(0, 128, 255);
		case "red": // actuator
			r = Integer.toHexString(204);
			g = Integer.toHexString(51);
			b = Integer.toHexString(51);
			return r + g + b;
		// return new ColorConstant(204, 51, 51);
		default:
			r = Integer.toHexString(45);
			g = Integer.toHexString(12);
			b = Integer.toHexString(98);
			return r + g + b;
		// return new ColorConstant(45, 12, 98);
		}
	}
}
