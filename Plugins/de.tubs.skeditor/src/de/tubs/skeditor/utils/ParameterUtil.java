package de.tubs.skeditor.utils;

import java.util.ArrayList;
import java.util.List;

import scala.reflect.internal.Trees.This;

public class ParameterUtil {
	
	public enum ParameterValue {
		
		NUMBER("^((\\d*)(.\\d*)?)$"),
		CLOSED("^(\\[((\\d*)(.\\d*)?)?\\s*:\\s*((\\d*)(.\\d*)?)?\\])$"),
		LEFT_OPEN("^(\\(((\\d*)(.\\d*)?)?\\s*:\\s*((\\d*)(.\\d*)?)?\\])$"),
		RIGHT_OPEN("^(\\[((\\d*)(.\\d*)?)?\\s*:\\s*((\\d*)(.\\d*)?)?\\))$"),
		OPEN("^(\\(((\\d*)(.\\d*)?)?\\s*:\\s*((\\d*)(.\\d*)?)?\\))$");
		
		private String regex;
		
		ParameterValue(String regexMatch) {
			this.regex = regexMatch;
		}
		
		public boolean test(String value) {
			return value.matches(regex);
		}
		
		public String getMatchString() {
			return regex;
		}
		
		public static String getCompleteMatch() {
			String match = NUMBER.getMatchString();
			match += "|" + CLOSED.getMatchString();
			match += "|" + LEFT_OPEN.getMatchString();
			match += "|" + RIGHT_OPEN.getMatchString();
			match += "|" + OPEN.getMatchString();
			
			return match;
		}
		
		
	}
	
	
	public static String createBounds(String var, String value) {
		if(value == null || value.isEmpty())
			return "";
		
		List<String> parts = new ArrayList<String>();

		if (ParameterValue.NUMBER.test(value)) {
			return var + " = " + value;
		} else if (ParameterValue.CLOSED.test(value)) {
			String first = value.split(":")[0].replace("[", "");
			String second = value.split(":")[1].replace("]", "");
			if(!first.trim().isEmpty())
				parts.add(var + " >= " + first);
			if(!second.trim().isEmpty())
				parts.add(var + " <= " + second);
		} else if (ParameterValue.LEFT_OPEN.test(value)) {
			String first = value.split(":")[0].replace("(", "");
			String second = value.split(":")[1].replace("]", "");
			if(!first.trim().isEmpty())
				parts.add(var + " > " + first);
			if(!second.trim().isEmpty())
				parts.add(var + " <= " + second);
		} else if (ParameterValue.RIGHT_OPEN.test(value)) {
			String first = value.split(":")[0].replace("[", "");
			String second = value.split(":")[1].replace(")", "");
			if(!first.trim().isEmpty())
				parts.add(var + " >= " + first);
			if(!second.trim().isEmpty())
				parts.add(var + " < " + second);
		} else if (ParameterValue.OPEN.test(value)) {
			String first = value.split(":")[0].replace("(", "");
			String second = value.split(":")[1].replace(")", "");
			if(!first.trim().isEmpty())
				parts.add(var + " > " + first);
			if(!second.trim().isEmpty())
				parts.add(var + " < " + second);
		}

		return String.join(" & ", parts);
	}
}
