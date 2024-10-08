package com.example.demoSpringSecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoSpringSecurity.dto.RegisterUserDto;
import com.example.demoSpringSecurity.dto.VerifyUserDto;
import com.example.demoSpringSecurity.model.LoginRequest;
import com.example.demoSpringSecurity.model.LoginResponse;
import com.example.demoSpringSecurity.model.UserEntity;
import com.example.demoSpringSecurity.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public LoginResponse login(@RequestBody @Validated LoginRequest request) {
		return authService.login(request.getEmail(), request.getPassword());
	}

	@PostMapping("/signup")
	public ResponseEntity<UserEntity> register(@RequestBody RegisterUserDto registerUserDto) {
		UserEntity registeredUser = authService.signup(registerUserDto);
		return ResponseEntity.ok(registeredUser);
	}

	@PostMapping("/verify")
	public ResponseEntity<?> verifyUser(@RequestBody VerifyUserDto verifyUserDto) {
		try {
			authService.verifyUser(verifyUserDto);
			return ResponseEntity.ok("Account verified successfully");
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("/resend")
    public ResponseEntity<?> resendVerificationCode(@RequestParam String email) {
        try {
        	authService.resendVerificationCode(email);
            return ResponseEntity.ok("Verification code sent");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
	

}
