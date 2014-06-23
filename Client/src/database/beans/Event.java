package database.beans;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * Bean that represents an instance of the entity Event in the recruitment database. 
 * @author Charlie
 */
public class Event implements Serializable {
	private int eventId;
	private EventType eventType;
	private Candidate candidate;
	private Date date;
	private Time time;
	private String userId;
	private Vacancy vacancy;
	
	public Event(EventType eventType, Candidate candidate, Date date, Time time, String userId, Vacancy vacancy) {
		this.eventType = eventType;
		this.candidate = candidate;
		this.date = date;
		this.time = time;
		this.userId = userId;
		this.vacancy = vacancy;
	}
	
	public int getEventId() {
		return eventId;
	}
	
	public void setEventId(int eventId) {
		this.eventId = eventId;
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
		return vacancy.getVacancyId();
	}

	public Time getTime() {
		return time;
	}
	
	public String getVacancyName() {
		return vacancy.getName();
	}
	
	public String getVacancyOrganisation() {
		return vacancy.getOrganisationName();
	}
	
	@Override
	public String toString() {
		return eventType + ": " + vacancy.getName() + ": " + date + " " + time;
	}
}
