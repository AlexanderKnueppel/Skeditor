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

	private List<Node> insertedSkills;
	private List<Edge> insertedEdges;
	private SkillProvider provider;
	
	public SkillInsert(List<Node> insertedSkills, List<Edge> insertedEdges, SkillProvider provider) {
		this.insertedSkills = insertedSkills;
		this.insertedEdges = insertedEdges; 
		this.provider = provider;
	}
	
	/**
	 * Returns the skills that was newly inserted 
	 * 
	 * @return the inserted skills for this insertion
	 */
	public List<Node> getInsertedSkills() {
		return insertedSkills;
	}
	
	/**
	 * Returns the edges that was newly inserted 
	 * 
	 * @return the inserted skills for this insertion
	 */
	public List<Edge> getInsertedEdges() {
		return insertedEdges;
	}
	
	/**
	 * Returns the skills that was newly inserted 
	 * 
	 * @return the inserted skills for this insertion
	 */
	public void setInsertedSkills(List<Node> inserted) {
		this.insertedSkills = inserted;
	}
	
	/**
	 * Returns the edges that was newly inserted 
	 * 
	 * @return the inserted skills for this insertion
	 */
	public void setInsertedEdges(List<Edge> inserted) {
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
