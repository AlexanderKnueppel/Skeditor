package de.tubs.skeditor.synthesis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import SkillGraph.Edge;
import SkillGraph.Node;
import SkillGraph.SkillGraphFactory;
import de.tubs.skeditor.synthesis.search.FilterFormatException;
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
		this.depth = 1;
		this.currentIndex = 0;
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
