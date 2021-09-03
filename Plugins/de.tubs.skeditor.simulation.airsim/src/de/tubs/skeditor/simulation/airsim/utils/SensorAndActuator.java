package de.tubs.skeditor.simulation.airsim.utils;

public class SensorAndActuator {
	private final String name;

	public SensorAndActuator(String name) {
		this.name = name;
	}

	public void writeDefinition(StringBuilder sb) {
		// @formatter:off
		sb.append(String.format(
				"void %s(msr::airlib::CarRpcLibClient* client, state* curr, parameters* params, const input* in) {\n"
			+ "\n" 
			+ "    while(loop()) {\n" 
			+ "        State stateCopy = *curr;\n"
			+ "        Parameters paramsCopy = *params;\n" 
			+ "        Input inputCopy = *in;\n" + "\n"
			+ "        /*\n" 
			+ "         * ################\n" 
			+ "         * INSERT CODE HERE\n"
			+ "         * ################\n" 
			+ "         */\n" 
			+ "    }\n" 
			+ "}\n"
			+ "\n",
				name));
		// @formatter:on
	}

	public void writeThread(StringBuilder sb) {
		sb.append(String.format("        std::thread %s_Thread(%s, &client, &states, &params, &inputs);%n", name,
				name));
	}
}