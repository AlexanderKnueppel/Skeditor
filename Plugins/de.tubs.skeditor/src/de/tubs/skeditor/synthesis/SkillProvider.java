package de.tubs.skeditor.synthesis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import SkillGraph.Node;
import de.tubs.skeditor.synthesis.search.SkillSearch;

public abstract class SkillProvider {

	protected Map<Integer, List<Node>> nodeMap;
	protected SkillSearch searcher;
	protected int depth;
	protected int currentIndex;
	private boolean empty = false;
	
	public SkillProvider() {
		this.nodeMap = new HashMap<>();
		this.searcher = SkillSearch.getInstance();
		this.currentIndex = 0;
		this.depth = 1;
	}
	
	/*
	 * Returns the next skill from this provider
	 * 
	 * @return the next skill or null if there are no more left
	 */
	public Node getNext() {
		if(empty) {
			System.out.println("LEEEER der provider");
			return null;
		}
		if(nodeMap.get(depth).size() > currentIndex) {
			currentIndex++;
			return nodeMap.get(depth).get(currentIndex-1);
		} else {
			addNodes(depth+1);
			if(nodeMap.get(depth+1).size() > 0) {
				depth++;
				currentIndex = 1;
				return nodeMap.get(depth).get(currentIndex-1);
			} else {
				empty = true;
				return null;
			}
		}
	}
	
	protected abstract void addNodes(int depth);
	
	
}
