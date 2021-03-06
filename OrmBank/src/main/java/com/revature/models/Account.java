package com.revature.models;

import java.io.Serializable;
import java.util.Objects;

import com.revature.annotations.Entity;
import com.revature.annotations.Id;
import com.revature.annotations.Column;
import com.revature.annotations.Default;
import com.revature.annotations.JoinColumn;

@Entity(entityName = "accounts")
public class Account implements Serializable {

	@Id(columnName="account_id", isSerial=true)
	private int accountId;

	@Column(columnName="balance")
	private double balance;
	
	@JoinColumn(columnName="user_id")
	private int userId;
	
	@Column(columnName="is_active")
	boolean isActive;
	
	public Account() {
		
	}

	public Account(int accountId, double balance, boolean isActive, int userId) {
		super();
		this.accountId = accountId;
		this.balance = balance;
		this.userId = userId;
		this.isActive = isActive;
	}
	
	public Account(double balance, boolean isActive, int userId) {
		super();
		this.balance = balance;
		this.userId = userId;
		this.isActive = isActive;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", balance=" + balance + ", userId=" + userId + ", isActive="
				+ isActive + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountId, balance, isActive, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return accountId == other.accountId
				&& Double.doubleToLongBits(balance) == Double.doubleToLongBits(other.balance)
				&& isActive == other.isActive && userId == other.userId;
	}
	
	
	
}
