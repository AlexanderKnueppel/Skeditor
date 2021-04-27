package de.tubs.skeditor.views.infoview;

import SkillGraph.Equation;
import SkillGraph.Node;
import SkillGraph.Requirement;

public class InfoModelRow {
	private String category = null;
	private String content = null;
	private Node node;
	private boolean isEditable = false;
	
	public InfoModelRow(String category, String content, Node node) {
		this.category = category;
		this.content = content;
		this.node = node;
	}

	public String getCategory() {
		return category;
	}
	
	public String getContent() {
		return content;
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
		return category + ": " + content + "\n"+ super.toString();
	}
}
