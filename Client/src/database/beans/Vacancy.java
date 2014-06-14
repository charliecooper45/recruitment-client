package database.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Bean that represents an instance of the entity Vacancy in the recruitment database. 
 * @author Charlie
 */
public class Vacancy implements Serializable, Comparable<Vacancy> {
	private static final long serialVersionUID = -2963989512119626414L;
	private int vacancyId;
	private boolean status;
	private String name;
	private Date vacancyDate;
	private String text;
	private String profile;
	private int organisationId;
	private String organisationName;
	private String userId;
	private int contactId;
	private String contactName;
	private String contactPhoneNumber;
	
	public Vacancy(int vacancyId, boolean status, String name, Date vacancyDate, String text, String profile, int organisationId, String organisationName, String userId, int contactId, String contactName, String contactPhoneNumber) {
		this.vacancyId = vacancyId;
		this.status = status;
		this.name = name;
		this.vacancyDate = vacancyDate;
		this.text = text;
		this.profile = profile;
		this.organisationId = organisationId;
		this.organisationName = organisationName;
		this.userId = userId;
		this.contactId = contactId;
		this.contactName = contactName;
		this.contactPhoneNumber = contactPhoneNumber;
	}

	public String getName() {
		return name;
	}

	public boolean getStatus() {
		return status;
	}

	public Date getVacancyDate() {
		return vacancyDate;
	}

	public String getOrganisationName() {
		return organisationName;
	}

	public String getUserId() {
		return userId;
	}

	public int getVacancyId() {
		return vacancyId;
	}
	
	public String getContactName() {
		return contactName;
	}

	public String getContactPhoneNumber() {
		return contactPhoneNumber;
	}

	public String getProfile() {
		return profile;
	}
	
	public String getText() {
		return text;
	}
	
	public int getOrganisationId() {
		return organisationId;
	}
	
	public int getContactId() {
		return contactId;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public void setVacancyDate(Date vacancyDate) {
		this.vacancyDate = vacancyDate;
	}
	
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	
	@Override
	public String toString() {
		String statusString = "open";
		if(!status) {
			statusString = "closed";
		}
		return name + " @ " + organisationName + " (" + statusString + ")";
	}
	
	@Override
	public int compareTo(Vacancy vacancy) {
		return this.name.compareTo(vacancy.name);
	}
}
