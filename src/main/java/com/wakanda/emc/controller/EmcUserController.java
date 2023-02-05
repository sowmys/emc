package com.wakanda.emc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wakanda.emc.model.EmcUser;

import java.util.ArrayList;
import java.util.List;


@RestController
public class EmcUserController {
	List<EmcUser> userList = new ArrayList<EmcUser>();

	@PostMapping("/users")
	public EmcUser createUser(@RequestBody EmcUser user) {
		userList.add(user);
		return user;
	}

	@PutMapping("/users/{id}")
	public EmcUser updateUser(@PathVariable Long id, @RequestBody EmcUser user) {
		return user;
	}

	@GetMapping("/users")
	public EmcUser[] getUsers() {
		EmcUser[] result =  userList.toArray(new EmcUser[0]);
		return result;
	}
}
