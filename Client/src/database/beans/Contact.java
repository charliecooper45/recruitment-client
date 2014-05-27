package database.beans;

import java.io.Serializable;

/**
 * Bean that represents an instance of the entity Contact in the recruitment database. 
 * @author Charlie
 */
public class Contact implements Serializable {
	private int id;
	private String firstName;
	private String surname;
	private String jobTitle;
	private String phoneNumber;
	private String emailAddress;
	private String address;
	private String notes;
	private int organisationId;
	private String userId;

	public Contact(int id, String firstName, String surname, String jobTitle, String phoneNumber, String emailAddress, String address, String notes, int organisationId, String userId) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.surname = surname;
		this.jobTitle = jobTitle;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.address = address;
		this.notes = notes;
		this.organisationId = organisationId;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Override
	public String toString() {
		return firstName + " " + surname;
	}
}
