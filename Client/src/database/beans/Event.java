package database.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Bean that represents an instance of the entity Event in the recruitment database. 
 * @author Charlie
 */
public class Event implements Serializable {
	private EventType eventType;
	private Candidate candidate;
	private Date date;
	private String userId;
	private int vacancyId;
	
	public Event(EventType eventType, Candidate candidate, Date date, String userId, int vacancyId) {
		this.eventType = eventType;
		this.candidate = candidate;
		this.date = date;
		this.userId = userId;
		this.vacancyId = vacancyId;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getVacancyId() {
		return vacancyId;
	}

	public void setVacancyId(int vacancyId) {
		this.vacancyId = vacancyId;
	}
}
