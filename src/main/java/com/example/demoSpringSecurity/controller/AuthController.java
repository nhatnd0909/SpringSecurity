package com.example.demoSpringSecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoSpringSecurity.model.LoginRequest;
import com.example.demoSpringSecurity.model.LoginResponse;
import com.example.demoSpringSecurity.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public LoginResponse login(@RequestBody @Validated LoginRequest request) {
		return authService.attemplogin(request.getEmail(), request.getPassword());
	}
}
