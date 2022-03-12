package com.revature.models;

import java.io.Serializable;
import java.util.Objects;

public class Account implements Serializable {


	private int id;
	private double balance;
	private int accOwner; // could be some type of ArrayList with all the user id's
	private boolean isActive; // remember the instance variable names do not need to adhere
	
	public Account() {
		
	}

	public Account(int id, double balance, int accOwner, boolean isActive) {
		super();
		this.id = id;
		this.balance = balance;
		this.accOwner = accOwner;
		this.isActive = isActive;
	}
	
	
	// We need this constructor in the case that we instantiate the Account in the Java Program.
	// Then we send it to the database which assigns a Primary Key. (Serial Primary Key)
	// When we return the object from the database, it will have its assigned ID
	public Account(double balance, int accOwner, boolean isActive) {
		super();
		this.balance = balance;
		this.accOwner = accOwner;
		this.isActive = isActive;
	}

	//getters and setters
	// regenerate hashCode. equals, toString
	
	/**
	 * @return the accOwner
	 */
	public int getAccOwner() {
		return accOwner;
	}

	/**
	 * @param accOwner the accOwner to set
	 */
	public void setAccOwner(int accOwner) {
		this.accOwner = accOwner;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accOwner, balance, id, isActive);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Account)) {
			return false;
		}
		Account other = (Account) obj;
		return accOwner == other.accOwner && Double.doubleToLongBits(balance) == Double.doubleToLongBits(other.balance)
				&& id == other.id && isActive == other.isActive;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", balance=" + balance + ", accOwner=" + accOwner + ", isActive=" + isActive + "]";
	}
	
	
}