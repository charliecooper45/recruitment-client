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
	private int organisationId;
	private String organisationName;
	private String phoneNumber;
	private String emailAddress;
	private String address;
	private String notes;
	private String linkedInProfile;
	private String CV;
	private String userId;

	public Candidate(int id, String firstName, String surname, String jobTitle, int organisationId, String organisationName, String phoneNumber, String emailAddress, String address, String notes, String linkedInProfile, String cV, String userId) {
		this.id = id;
		this.firstName = firstName;
		this.surname = surname;
		this.jobTitle = jobTitle;
		this.organisationId = organisationId;
		this.organisationName = organisationName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.address = address;
		this.notes = notes;
		this.linkedInProfile = linkedInProfile;
		CV = cV;
		this.userId = userId;
	}
	
	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getSurname() {
		return surname;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getAddress() {
		return address;
	}

	public String getNotes() {
		return notes;
	}

	public String getLinkedInProfile() {
		return linkedInProfile;
	}

	public String getCV() {
		return CV;
	}

	public String getUserId() {
		return userId;
	}

	public void setCV(String CV) {
		this.CV = CV;
	}
	
	public String getOrganisationName() {
		return organisationName;
	}
	
	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;
	}
	
	public int getOrganisationId() {
		return organisationId;
	}
	
	public void setOrganisationId(int organisationId) {
		this.organisationId = organisationId;
	}
	
	@Override
	public String toString() {
		return id + ": " + firstName + " " + surname;
	}
}
