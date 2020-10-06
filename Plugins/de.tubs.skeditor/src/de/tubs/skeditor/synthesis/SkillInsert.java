package de.tubs.skeditor.synthesis;

import java.util.List;

import SkillGraph.Edge;
import SkillGraph.Node;

/**
 * Class for Synthesis to remember inserted skills and the state of the provider
 * 
 * @author Christopher
 *
 */
public class SkillInsert {

	private Node[] insertedSkills;
	private Edge[] insertedEdges;
	private SkillProvider provider;
	
	public SkillInsert(Node[] insertedSkills, Edge[] insertedEdges, SkillProvider provider) {
		this.insertedSkills = insertedSkills;
		this.insertedEdges = insertedEdges; 
		this.provider = provider;
	}
	
	/**
	 * Returns the skills that was newly inserted 
	 * 
	 * @return the inserted skills for this insertion
	 */
	public Node[] getInsertedSkills() {
		return insertedSkills;
	}
	
	/**
	 * Returns the edges that was newly inserted 
	 * 
	 * @return the inserted skills for this insertion
	 */
	public Edge[] getInsertedEdges() {
		return insertedEdges;
	}
	
	/**
	 * Returns the skills that was newly inserted 
	 * 
	 * @return the inserted skills for this insertion
	 */
	public void setInsertedSkills(Node[] inserted) {
		this.insertedSkills = inserted;
	}
	
	/**
	 * Returns the edges that was newly inserted 
	 * 
	 * @return the inserted skills for this insertion
	 */
	public void setInsertedEdges(Edge[] inserted) {
		this.insertedEdges = inserted;
	}
	
	/**
	 * Returns the SkillProvider that was used for the insertion
	 * 
	 * @return the used SkillProvider
	 */
	public SkillProvider getSkillProvider() {
		return provider;
	}
}
