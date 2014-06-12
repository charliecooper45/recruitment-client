package database.beans;

import java.io.Serializable;

/**
 * Bean that represents an instance of the entity Candidate has skill in the recruitment database. 
 * @author Charlie
 */
public class CandidateSkill implements Serializable {
	private int candidateId;
	private String skillName;
	private String userId;
	
	public CandidateSkill(int candidateId, String skillName, String userId) {
		this.candidateId = candidateId;
		this.skillName = skillName;
		this.userId = userId;
	}

	public int getCandidateId() {
		return candidateId;
	}

	public String getSkillName() {
		return skillName;
	}

	public String getUserId() {
		return userId;
	}
}
