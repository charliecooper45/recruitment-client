package database.beans;

import interfaces.UserType;

import java.io.Serializable;

/**
 * Bean that represents an instance of the entity User in the recruitment database. 
 * @author Charlie
 */
public class User implements Serializable, Comparable<User> {
	private static final long serialVersionUID = -3005455589401182683L;
	private String userId;
	private String password;
	private String firstName;
	private String surname;
	private String emailAddress;
	private String phoneNumber;
	private boolean accountStatus;
	private UserType accountType;
	
	public User(String userId, String password, String firstName, String surname, String emailAddress, String phoneNumber, boolean accountStatus, UserType accountType) {
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
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public UserType getAccountType() {
		return accountType;
	}
	
	public boolean getAccountStatus() {
		return accountStatus;
	}
	
	public String getPassword() {
		return password;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}
}
