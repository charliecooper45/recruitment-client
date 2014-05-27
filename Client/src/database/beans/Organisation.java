package database.beans;

import java.io.Serializable;

/**
 * Bean that represents an instance of the entity Organisation in the recruitment database. 
 * @author Charlie
 */
public class Organisation implements Serializable, Comparable<Organisation> {
	private int organisationId;
	private String organisationName;
	private String phoneNumber;
	private String emailAddress;
	private String website;
	private String address;
	private String termsOfBusiness;
	private String notes;
	private int userId;
	
	public Organisation(int organisationId, String organisationName, String phoneNumber, String emailAddress, String website, String address, String termsOfBusiness, String notes, int userId) {
		this.organisationId = organisationId;
		this.organisationName = organisationName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.website = website;
		this.address = address;
		this.termsOfBusiness = termsOfBusiness;
		this.notes = notes;
		this.userId = userId;
	}
	
	public String getOrganisationName() {
		return organisationName;
	}

	public int getId() {
		return organisationId;
	}
	
	@Override
	public String toString() {
		return organisationName;
	}

	@Override
	public int compareTo(Organisation o) {
		return this.organisationName.compareTo(o.organisationName);
	}
}
