package database.beans;

import java.io.Serializable;

/**
 * Bean that represents an instance of the entity Skill in the recruitment database. 
 * @author Charlie
 */
public class Skill implements Serializable, Comparable<Skill> {
	private String skillName;
	private String userId;
	private int usage;
	
	public Skill(String skillName, String userId) {
		super();
		this.skillName = skillName;
		this.userId = userId;
	}
	
	public Skill(String skillName, String userId, int usage) {
		super();
		this.skillName = skillName;
		this.userId = userId;
		this.usage = usage;
	}

	public String getSkillName() {
		return skillName;
	}

	public String getUserId() {
		return userId;
	}
	
	public int getUsage() {
		return usage;
	}
	
	@Override
	public String toString() {
		return skillName;
	}
	
	@Override
	public int compareTo(Skill skill) {
		return this.skillName.compareTo(skill.skillName);
	}
}
