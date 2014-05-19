package database.beans;

import java.io.Serializable;

/**
 * Bean that represents an instance of the entity Candidate in the recruitment database. 
 * @author Charlie
 */
public class Candidate implements Serializable {
	private int id;
	private String firstName;
	private String surname;
	private String jobTitle;
	private String phoneNumber;
	private String emailAddress;
	private String address;
	private String notes;
	private String linkedInProfile;
	private String CV;
	private String userId;

	public Candidate(int id, String firstName, String surname, String jobTitle, String phoneNumber, String emailAddress, String address, String notes, String linkedInProfile, String cV, String userId) {
		this.id = id;
		this.firstName = firstName;
		this.surname = surname;
		this.jobTitle = jobTitle;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.address = address;
		this.notes = notes;
		this.linkedInProfile = linkedInProfile;
		CV = cV;
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Candidate [id=" + id + ", firstName=" + firstName + ", surname=" + surname + ", jobTitle=" + jobTitle + ", phoneNumber=" + phoneNumber + ", emailAddress=" + emailAddress + ", address=" + address + ", notes=" + notes + ", linkedInProfile=" + linkedInProfile + ", CV=" + CV + ", userId=" + userId + "]";
	}
}
