package database.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Bean that represents an instance of the entity Task in the recruitment database. 
 * @author Charlie
 */
public class Task implements Serializable, Comparable<Task> {
	private static final long serialVersionUID = -6125534226598651393L;
	private int taskId;
	private Date date;
	private Date time;
	private String description;
	private String userId;
	
	public Task(int taskId, Date date, Date time, String description, String userId) {
		super();
		this.taskId = taskId;
		this.date = date;
		this.time = time;
		this.description = description;
		this.userId = userId;
	}
	
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

	@Override
	public int compareTo(Task task) {
		int compare = this.date.compareTo(task.date);
		
		if(compare == 0) {
			compare = this.time.compareTo(task.time);
		}
		return compare;
	}
}
