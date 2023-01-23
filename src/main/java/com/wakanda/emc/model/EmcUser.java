package com.wakanda.emc.model;

public class EmcUser {
	public EmcUser(){

	}
	public EmcUser(String userName, String address, String email, String phone) {
		this(userName, address, email, phone, -1);

	}

	public EmcUser(String userName, String address, String email, String phone, long id) {
		this.id = id;
		this.userName = userName;
		this.address = address;
		this.email = email;
		this.phone = phone;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	private String userName;
	private String address;
	private String email;
	private String phone;
	private long id;
}
