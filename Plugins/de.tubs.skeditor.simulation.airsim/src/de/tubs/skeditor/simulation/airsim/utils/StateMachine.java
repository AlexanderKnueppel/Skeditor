package de.tubs.skeditor.simulation.airsim.utils;

import de.tubs.skeditor.keymaera.KeYmaeraBridge;

public class StateMachine {
	private final String name;
	private final String code;
	private final String stateMachineCode;
	private final String stateMachineCodeName;

	public StateMachine(String name, String ctrl) {
		this.name = name;

		code = KeYmaeraBridge.getInstance().generateCCode(ctrl).get(0);
		int startIndex = code.indexOf("state ctrlStep(");

		stateMachineCodeName = "ctrlStep_" + name;

		// @formatter:off
		stateMachineCode = code.substring(startIndex)
				.replace("ctrlStep(state", stateMachineCodeName + "(state") // rename function
				.replace("struct { state state; int success; } prg = { .state=curr, .success=0 };", "Prg prg(curr);"); // replace c++ 2020 dependency
		// @formatter:on
	}
	
	public String getCode() {
		return this.code;
	}

	public void writeDefinition(StringBuilder sb) {
		sb.append(stateMachineCode);
		sb.append("\n");
	}

	public void writeCall(StringBuilder sb) {
		sb.append(String.format("            states = %s(states, &params, &inputs);%n", stateMachineCodeName));
	}
}