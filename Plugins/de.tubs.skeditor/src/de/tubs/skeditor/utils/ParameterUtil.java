package de.tubs.skeditor.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;

import SkillGraph.Graph;
import SkillGraph.Parameter;
import SkillGraph.SkillGraphFactory;
import de.tubs.skeditor.utils.ParameterUtil.GlobalParameter;

public class ParameterUtil {

	public static class GlobalParameter {
		private String name = "";
		private String abbr = "";
		private boolean isVariable = false;
		private String from = "[global]";
		private String unit = "";
		private String range = "";

		public GlobalParameter(String name, String abbr, boolean isVariable, String unit, String range) {
			this.name = name;
			this.abbr = abbr;
			this.isVariable = isVariable;
			this.unit = unit;
			this.range = range;
		}

		public String getName() {
			return name;
		}

		public String getAbbr() {
			return abbr;
		}

		public boolean isVariable() {
			return isVariable;
		}

		public String getFrom() {
			return from;
		}
		
		public String getUnit() {
			return unit;
		}
		
		public String getRange() {
			return range;
		}

		public boolean match(Parameter parameter) {
			return getAbbr().equals(parameter.getAbbreviation()) && (isVariable() == parameter.isVariable());
		}
	}
	
	public static Set<GlobalParameter> getGlobals() {
		Set<GlobalParameter> result = new HashSet<GlobalParameter>();
		result.add(new GlobalParameter("Time", "t", true, "[time]", ""));
		result.add(new GlobalParameter("Control Cycle", "ep", false, "[time]", "(0:)"));
		return result;
	}
	
	public static boolean isGlobalParameter(String abbr) {
		for(GlobalParameter parameter : getGlobals()) {
			if(parameter.getAbbr().equals(abbr))
				return true;
		}
		return false;
	}
	
	public static boolean existsGlobalParameters(GlobalParameter p, Graph graph) {
		for(Parameter p2 : graph.getParameterList()) {
			if(p.match(p2)) {
				return true;
			}
		}
		return false;
	}
	
	public static void addGlobalParameters(Graph graph) {
		for(GlobalParameter p : ParameterUtil.getGlobals()) {
			if(existsGlobalParameters(p, graph))
				continue;
			
			Parameter parameter = SkillGraphFactory.eINSTANCE.createParameter();
			parameter.setName(p.getName());
			parameter.setAbbreviation(p.getAbbr());
			parameter.setUnit(p.getUnit());
			parameter.setDefaultValue(p.getRange());
			parameter.setVariable(p.isVariable());
			
			TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(graph);
			domain.getCommandStack().execute(new RecordingCommand(domain) {
				@Override
				protected void doExecute() {
					graph.getParameterList().add(parameter);
				}
			});
		}
	}

	public enum ParameterValue {

		NUMBER("^((\\d*)(.\\d*)?)$"), CLOSED("^(\\[((\\d*)(.\\d*)?)?\\s*:\\s*((\\d*)(.\\d*)?)?\\])$"),
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
		if (value == null || value.isEmpty())
			return "";

		List<String> parts = new ArrayList<String>();

		if (ParameterValue.NUMBER.test(value)) {
			return var + " = " + value;
		} else if (ParameterValue.CLOSED.test(value)) {
			String first = value.split(":")[0].replace("[", "");
			String second = value.split(":")[1].replace("]", "");
			if (!first.trim().isEmpty())
				parts.add(var + " >= " + first);
			if (!second.trim().isEmpty())
				parts.add(var + " <= " + second);
		} else if (ParameterValue.LEFT_OPEN.test(value)) {
			String first = value.split(":")[0].replace("(", "");
			String second = value.split(":")[1].replace("]", "");
			if (!first.trim().isEmpty())
				parts.add(var + " > " + first);
			if (!second.trim().isEmpty())
				parts.add(var + " <= " + second);
		} else if (ParameterValue.RIGHT_OPEN.test(value)) {
			String first = value.split(":")[0].replace("[", "");
			String second = value.split(":")[1].replace(")", "");
			if (!first.trim().isEmpty())
				parts.add(var + " >= " + first);
			if (!second.trim().isEmpty())
				parts.add(var + " < " + second);
		} else if (ParameterValue.OPEN.test(value)) {
			String first = value.split(":")[0].replace("(", "");
			String second = value.split(":")[1].replace(")", "");
			if (!first.trim().isEmpty())
				parts.add(var + " > " + first);
			if (!second.trim().isEmpty())
				parts.add(var + " < " + second);
		}

		return String.join(" & ", parts);
	}

}
