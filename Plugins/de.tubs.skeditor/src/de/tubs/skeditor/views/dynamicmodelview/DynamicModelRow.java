package de.tubs.skeditor.views.dynamicmodelview;

import SkillGraph.Equation;
import SkillGraph.Node;
import SkillGraph.Requirement;

public class DynamicModelRow {
	private Equation equation = null;
	private Requirement preCondition = null;
	private Requirement postCondition = null;
	private String programPath = null;
	private Node node;
	private boolean isEditable = true;
	
	public DynamicModelRow(Equation equation, Requirement preCond, Requirement postCond, Node node) {
		this.equation = equation;
		this.preCondition = preCond;
		this.postCondition = postCond;
		this.node = node;
	}

	public Equation getEquation() {
		return equation;
	}

	public void setEquation(Equation equation) {
		this.equation = equation;
	}

	public Requirement getPreCondition() {
		return preCondition;
	}

	public void setPreCondition(Requirement preCondition) {
		this.preCondition = preCondition;
	}

	public Requirement getPostCondition() {
		return postCondition;
	}

	public void setPostCondition(Requirement postCondition) {
		this.postCondition = postCondition;
	}

	public String getProgramPath() {
		return programPath;
	}

	public void setProgramPath(String programPath) {
		this.programPath = programPath;
	}

	public Node getNode() {
		return node;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	@Override
	public String toString() {
		return equation + " - " + preCondition + " - " + postCondition + " - " + programPath + " - " + super.toString();
	}
}
