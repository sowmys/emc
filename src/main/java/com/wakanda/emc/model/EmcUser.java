package com.wakanda.emc.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "emc-users")
@Data
public class EmcUser {
	@Indexed(unique = true)
	private String userHandle;
	private String password;
	private String firstName;
	private String lastName;
	private String address;
	private String email;
	private String phone;
	private List<String> orgHandles = new ArrayList<>();
	@Id
	private ObjectId id;

	public EmcUser() {
	}

	public EmcUser(String userHandle, String password, String firstName, String lastName, String address, String email, String phone) {
		this.userHandle = userHandle;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		this.phone = phone;
	}
}
