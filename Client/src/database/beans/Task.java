package database.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Bean that represents an instance of the entity Task in the recruitment database. 
 * @author Charlie
 */
public class Task implements Serializable{
	private int taskId;
	private Date date;
	private Date time;
	private String description;
	private String userId;
	
	public Task(Date date, Date time, String description, String userId) {
		super();
		this.date = date;
		this.time = time;
		this.description = description;
		this.userId = userId;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setTime(Date time) {
		this.time = time;
	}
	
	public Date getTime() {
		return time;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public int getTaskId() {
		return taskId;
	}
}
