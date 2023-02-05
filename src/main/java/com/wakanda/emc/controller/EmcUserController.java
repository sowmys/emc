package com.wakanda.emc.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wakanda.emc.database.EmcUserRepository;
import com.wakanda.emc.model.EmcUser;
import com.wakanda.emc.viewmodel.LoginRequest;
import com.wakanda.emc.viewmodel.LoginResponse;
import com.wakanda.emc.viewmodel.RegisterRequest;


@RestController
@RequestMapping("/api")
public class EmcUserController {

	EmcUserRepository userRepo;

	public EmcUserController(EmcUserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@PostMapping("/register")
	public ResponseEntity<LoginResponse> registerUser(@RequestBody RegisterRequest registerRequest) {
        if (addNewUser(registerRequest)) {
            return ResponseEntity.ok(new LoginResponse(true, registerRequest.getUserHandle()));
        } else {
            return ResponseEntity.ok(new LoginResponse(false, null));
        }
	}

	@PostMapping("/login")
    public ResponseEntity<LoginResponse> handleLogin(@RequestBody LoginRequest loginRequest) {
        // authentication logic here
        if (validCredentials(loginRequest.getUserHandle(), loginRequest.getPassword())) {
            return ResponseEntity.ok(new LoginResponse(true, loginRequest.getUserHandle()));
        } else {
            return ResponseEntity.ok(new LoginResponse(false, null));
        }
    }

	@GetMapping("/users")
	public Page<EmcUser> getUsers(int pageSize, int pageNumber) {
		return userRepo.findAll(PageRequest.of(pageNumber, pageSize));

	}

	private boolean validCredentials(String username, String password) {
        // check the username and password against the database or any other authentication source
        // return true if the credentials are valid, false otherwise
		EmcUser user = userRepo.findByUserHandle(username);
		if (user == null) {
			return false;
		}
		if (!user.getPassword().equals(password)) {
			return false;
		}
        return true;
    }


	private boolean addNewUser(RegisterRequest registerRequest) {
		EmcUser oldUser = userRepo.findByUserHandle(registerRequest.getUserHandle());
		if (oldUser != null) {
			return false;
		}

		EmcUser newUser = new EmcUser();
		newUser.setUserHandle(registerRequest.getUserHandle());
		newUser.setPassword(registerRequest.getPassword());
		newUser.setFirstName(registerRequest.getFirstName());
		newUser.setLastName(registerRequest.getLastName());
		newUser.setAddress(registerRequest.getAddress());
		newUser.setEmail(registerRequest.getEmail());
		newUser.setPhone(registerRequest.getPhone());
		userRepo.save(newUser);
		return true;
	}
}
