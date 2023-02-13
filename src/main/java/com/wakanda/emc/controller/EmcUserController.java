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
import com.wakanda.emc.dto.LoginRequest;
import com.wakanda.emc.dto.LoginResponse;
import com.wakanda.emc.dto.RegisterRequest;
import com.wakanda.emc.model.EmcUser;


@RestController
@RequestMapping("/api/users")
public class EmcUserController {

	EmcUserRepository userRepo;

	public EmcUserController(EmcUserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@PostMapping("/register")
	public ResponseEntity<LoginResponse> registerUser(@RequestBody RegisterRequest registerRequest) {
		EmcUser user = addNewUser(registerRequest);
        if (user != null) {
            return ResponseEntity.ok(new LoginResponse(true, registerRequest.getUserHandle(), user.getOrgHandles()));
        } else {
            return ResponseEntity.ok(new LoginResponse(false, null, null));
        }
	}

	@PostMapping("/login")
    public ResponseEntity<LoginResponse> handleLogin(@RequestBody LoginRequest loginRequest) {
        // authentication logic here
		EmcUser user = validCredentials(loginRequest.getUserHandle(), loginRequest.getPassword());
        if (user != null) {
            return ResponseEntity.ok(new LoginResponse(true, loginRequest.getUserHandle(), user.getOrgHandles()));
        } else {
            return ResponseEntity.ok(new LoginResponse(false, null, null));
        }
    }

	private EmcUser validCredentials(String userHandle, String password) {
        userHandle = userHandle.toLowerCase();
		EmcUser user = userRepo.findByUserHandle(userHandle);
		if (user == null) {
			return null;
		}
		if (!user.getPassword().equals(password)) {
			return null;
		}
        return user;
    }

	private EmcUser addNewUser(RegisterRequest registerRequest) {
		String userHandle = registerRequest.getUserHandle().toLowerCase();
		EmcUser oldUser = userRepo.findByUserHandle(userHandle);
		if (oldUser != null) {
			return null;
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
		return newUser;
	}
}
