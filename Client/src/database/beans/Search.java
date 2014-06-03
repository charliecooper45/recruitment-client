package database.beans;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Bean that represents a search for candidates to be run on the recruitment database.
 * @author Charlie
 */
public class Search implements Serializable {
	private String name;
	private String jobTitle;
	private Set<Skill> skills = null;
	
	public Search() {
		name = "";
		jobTitle = "";
		skills = new HashSet<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Set<Skill> getSkills() {
		return skills;
	}
}
