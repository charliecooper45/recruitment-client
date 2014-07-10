package database.beans;

import java.io.Serializable;

/**
 * Bean that represents an instance of the entity Organisation in the recruitment database. 
 * @author Charlie
 */
public class Organisation implements Serializable, Comparable<Organisation> {
	private static final long serialVersionUID = 7679658919820411341L;
	private int organisationId;
	private String organisationName;
	private String phoneNumber;
	private String emailAddress;
	private String website;
	private String address;
	private String termsOfBusiness;
	private String notes;
	private String userId;
	private int noOpenVacancies;
	
	public Organisation(int organisationId, String organisationName, String phoneNumber, String emailAddress, String website, String address, String termsOfBusiness, String notes, String userId, int noOpenVacancies) {
		this.organisationId = organisationId;
		this.organisationName = organisationName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.website = website;
		this.address = address;
		this.termsOfBusiness = termsOfBusiness;
		this.notes = notes;
		this.userId = userId;
		this.noOpenVacancies = noOpenVacancies;
	}
	
	public int getOrganisationId() {
		return organisationId;
	}
	
	public String getOrganisationName() {
		return organisationName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public int getNoOpenVacancies() {
		return noOpenVacancies;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public String getWebsite() {
		return website;
	}
	
	public String getTermsOfBusiness() {
		return termsOfBusiness;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setTermsOfBusiness(String termsOfBusiness) {
		this.termsOfBusiness = termsOfBusiness;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public void setWebsite(String website) {
		this.website = website;
	}
	
	public void setAddress(String address) {
		this.address = address;
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
