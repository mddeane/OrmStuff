package com.revature.models;

import java.io.Serializable;
import java.util.Objects;

import com.revature.annotations.Entity;
import com.revature.annotations.Id;
import com.revature.annotations.NotNull;
import com.revature.annotations.Unique;
import com.revature.annotations.Column;

@Entity(entityName="users")
public class User implements Serializable {

	@Id(columnName = "id")
	private int id;
	
	@Column(columnName = "firstName")
	private String firstName;
	
	@Column(columnName = "lastName")
	private String lastName;
	
	@Column(columnName = "username")
	//@NotNull(isNotNull = true)
	@Unique(isUnique = true)
	private String username;
	
	@Column(columnName = "pwd")
	//@NotNull(isNotNull = true)
	private String password;
	
	public User() {
		
	}

	public User(int id, String firstName, String lastName, String username, String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}

	public User(String firstName, String lastName, String username, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
				+ ", password=" + password + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, id, lastName, password, username);
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
		return Objects.equals(firstName, other.firstName) && id == other.id && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password) && Objects.equals(username, other.username);
	}
}
