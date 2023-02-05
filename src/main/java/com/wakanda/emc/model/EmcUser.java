package com.wakanda.emc.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "users")
@Data
public class EmcUser {
	public EmcUser(String userName, String address, String email, String phone) {
		this.userName = userName;
		this.address = address;
		this.email = email;
		this.phone = phone;
	}

	private String userName;
	private String address;
	private String email;
	private String phone;
	@Id
	private ObjectId id;
}
