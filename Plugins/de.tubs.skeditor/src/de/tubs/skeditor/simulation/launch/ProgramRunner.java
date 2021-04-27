package de.tubs.skeditor.simulation.launch;

import SkillGraph.Node;

public class ProgramRunner {

	private Node node;
	private String name;

	public ProgramRunner(Node node) {
		this.node = node;
		this.name = node.getName().replace(" ", "_");
	}
}
