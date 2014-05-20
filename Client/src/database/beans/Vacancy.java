package database.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Bean that represents an instance of the entity Vacancy in the recruitment database. 
 * @author Charlie
 */
public class Vacancy implements Serializable {
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

	@Override
	public String toString() {
		return "Vacancy [vacancyId=" + vacancyId + ", status=" + status + ", name=" + name + ", vacancyDate=" + vacancyDate + ", text=" + text + ", profile=" + profile + ", organisationId=" + organisationId + ", organisationName=" + organisationName + ", userId=" + userId + ", contactId=" + contactId + ", contactName=" + contactName + "]";
	}
	
	public Object getName() {
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
}
