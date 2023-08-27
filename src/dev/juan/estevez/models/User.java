package dev.juan.estevez.models;

import java.util.Objects;

/**
 * @author Juan Carlos Estevez Vargas.
 */
public class User {

	private int userID;
	private String userName;
	private String userEmail;
	private String userPhone;
	private String username;
	private String password;
	private String levelType;
	private String status;
	private String registerBy;
	private String permissions;

	public User() {
	}

	public User(int userID, String userName, String userEmail, String userPhone, String username2, String password,
			String levelType, String status, String registerBy) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPhone = userPhone;
		username = username2;
		this.password = password;
		this.levelType = levelType;
		this.status = status;
		this.registerBy = registerBy;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLevelType() {
		return levelType;
	}

	public void setLevelType(String levelType) {
		this.levelType = levelType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRegisterBy() {
		return registerBy;
	}

	public void setRegisterBy(String registerBy) {
		this.registerBy = registerBy;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setpermissions(String permissions) {
		this.permissions = permissions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(levelType, password, registerBy, status, userEmail, userID, userName, userPhone, username);
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
		return Objects.equals(levelType, other.levelType) && Objects.equals(password, other.password)
				&& Objects.equals(registerBy, other.registerBy) && Objects.equals(status, other.status)
				&& Objects.equals(userEmail, other.userEmail) && userID == other.userID
				&& Objects.equals(userName, other.userName) && Objects.equals(userPhone, other.userPhone)
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", userName=" + userName + ", userEmail=" + userEmail + ", userPhone="
				+ userPhone + ", username=" + username + ", password=" + password + ", levelType=" + levelType
				+ ", status=" + status + ", registerBy=" + registerBy + "]";
	}

}
