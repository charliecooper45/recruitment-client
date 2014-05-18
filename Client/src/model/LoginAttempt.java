package model;


/**
 * Contains the userId and password associated with a login attempt by the user.
 * @author Charlie
 */
public class LoginAttempt {
	private String userId;
	private String password;
	
	public LoginAttempt(String userId, char[] password) {
		this.userId = userId;
		this.password = new String(password);
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
}
