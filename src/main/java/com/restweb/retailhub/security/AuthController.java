package com.restweb.retailhub.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restweb.retailhub.user.IUserRepository;
import com.restweb.retailhub.user.User;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final IUserRepository ur;
	private final PasswordEncoder pe;
	private final JwtUtil ju;

	@Autowired
	public AuthController(IUserRepository ur, PasswordEncoder pe, JwtUtil ju) {
		this.ur = ur;
		this.pe = pe;
		this.ju = ju;
	}

	@PostMapping("/register")
	public String register(@RequestBody User user) {
		user.setRole(user.getRole().toUpperCase());
		user.setPassword(pe.encode(user.getPassword()));
		ur.save(user);
		return "User registered successfully";
	}

	@PostMapping("/login")
	public String login(@RequestBody User user) {
		User existingUser = ur.findByUsername(user.getUsername())
				.orElseThrow(() -> new RuntimeException("User not found"));

		if (pe.matches(user.getPassword(), existingUser.getPassword())) {
			return ju.generateToken(existingUser.getUsername());
		} else {
			throw new RuntimeException("Invalid credentials");
		}
	}

	@GetMapping("getUser/{id}")
	public User getUser(@PathVariable("id") long id) {

		return ur.findById(id).orElseThrow(() -> new EntityNotFoundException("User non trovato"));
	}
}
