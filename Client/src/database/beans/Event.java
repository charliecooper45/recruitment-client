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
	private int vacancyId;
	private String vacancyName;
	
	public Event(EventType eventType, Candidate candidate, Date date, String userId, int vacancyId) {
		this.eventType = eventType;
		this.candidate = candidate;
		this.date = date;
		this.userId = userId;
		this.vacancyId = vacancyId;
	}
	
	public Event(EventType eventType, Candidate candidate, Date date, Time time, String userId, int vacancyId, String vacancyName) {
		this.eventType = eventType;
		this.candidate = candidate;
		this.date = date;
		this.time = time;
		this.userId = userId;
		this.vacancyId = vacancyId;
		this.vacancyName = vacancyName;
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
		return vacancyId;
	}

	public void setVacancyId(int vacancyId) {
		this.vacancyId = vacancyId;
	}
	
	public Time getTime() {
		return time;
	}
	
	public String getVacancyName() {
		return vacancyName;
	}
	
	@Override
	public String toString() {
		return eventType + ": " + vacancyName + ": " + date + " " + time;
	}
}
