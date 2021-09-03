package de.tubs.skeditor.simulation.airsim.utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Structs {
	private final Set<String> parameters = new HashSet<>();
	private final Set<String> inputs = new HashSet<>();
	private final Set<String> states = new HashSet<>();

	public void parseCCode(StateMachine stateMachine) {
		Set<String> varType = null;
		for (String line : stateMachine.getCode().split("\n")) {
			if (line.contains("typedef struct state"))
				varType = states;
			if (line.contains("typedef struct input")) {
				if (!line.contains(";")) {
					varType = inputs;
				}
			}
			if (line.contains("typedef struct parameter"))
				varType = parameters;
			if (line.contains("}"))
				varType = null;

			if (varType != null && line.contains("long double")) {
				String varName = line.split("long double")[1].split(";")[0].trim();
				varType.add(varName);
			}
		}
	}

	public void writeDefinitions(StringBuilder sb) {
		// @formatter:off
		sb.append("/*\n" 
				+ " * ##############################################################\n"
				+ " * GENERATE STRUCTS FOR COMMUNICATION WITH KEYMAERAX STATEMACHINE\n"
				+ " * ##############################################################\n" 
				+ " */\n");
		sb.append("typedef struct parameters {\n");
		parameters.forEach(p -> sb.append(String.format("    long double %s = 0;%n", p)));
		sb.append("} Parameters;\n" 
				+ "\n");

		sb.append("typedef struct state {\n");
		states.forEach(s -> sb.append(String.format("    long double %s = 0;%n", s)));
		sb.append("} State;\n" 
				+ "\n");

		sb.append("typedef struct input {\n");
		inputs.forEach(i -> sb.append(String.format("    long double %s = 0;%n", i)));
		sb.append("} Input;\n");

		sb.append("/*\n" 
				+ " * ##################################################################\n"
				+ " * END GENERATE STRUCTS FOR COMMUNICATION WITH KEYMAERAX STATEMACHINE\n"
				+ " * ##################################################################\n" 
				+ " */\n\n");

		sb.append("struct Prg {\n" 
				+ "    explicit Prg(State state): state(state) {}\n"
				+ "    State state = {};\n" 
				+ "    int success = 0;\n" 
				+ "};\n\n");
		// @formatter:on
	}

	public void writeMain(StringBuilder sb, Map<String, String> graphParams) {
		// @formatter:off
		sb.append("    /*\n" 
				+ "     * #######################\n" 
				+ "     * GENERATE DEFAULT VALUES\n"
				+ "     * #######################\n" 
				+ "     */\n\n");

		if (parameters.contains("ep")) {
			sb.append("    params.ep = EP;\n");
		}

		for (String param : parameters) {
			if (param.equals("ep")) {
				continue;
			}
			String value = graphParams.getOrDefault(param, null);
			if (value != null && !value.trim().isEmpty()) {
				sb.append(String.format("    params.%s = %s;%n", param, value));
			}
		}

		sb.append("    /*\n" 
				+ "     * ###########################\n" 
				+ "     * END GENERATE DEFAULT VALUES\n"
				+ "     * ###########################\n" 
				+ "     */\n\n");
		// @formatter:on
	}
}