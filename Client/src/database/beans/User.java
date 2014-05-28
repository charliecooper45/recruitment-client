package database.beans;

import java.io.Serializable;

/**
 * Bean that represents an instance of the entity User in the recruitment database. 
 * @author Charlie
 */
public class User implements Serializable, Comparable<User> {
	private String userId;
	private String password;
	private String firstName;
	private String surname;
	private String emailAddress;
	private String phoneNumber;
	private boolean accountStatus;
	private String accountType;
	
	public User(String userId, String password, String firstName, String surname, String emailAddress, String phoneNumber, boolean accountStatus, String accountType) {
		this.userId = userId;
		this.password = password;
		this.firstName = firstName;
		this.surname = surname;
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
		this.accountStatus = accountStatus;
		this.accountType = accountType;
	}

	public String getUserId() {
		return userId;
	}
	
	@Override
	public String toString() {
		if(userId == null) {
			return firstName + " " + surname;
		}
		return userId + ": " + firstName + " " + surname;
	}
	
	@Override
	public int compareTo(User user) {
		int compare = this.firstName.compareTo(user.firstName);
		
		if(compare == 0) {
			compare = this.surname.compareTo(user.surname);
		}
		return compare;
	}
}
