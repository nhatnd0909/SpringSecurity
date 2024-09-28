package com.example.demoSpringSecurity.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demoSpringSecurity.dto.RegisterUserDto;
import com.example.demoSpringSecurity.dto.VerifyUserDto;
import com.example.demoSpringSecurity.model.LoginResponse;
import com.example.demoSpringSecurity.model.UserEntity;
import com.example.demoSpringSecurity.repository.UserRepository;
import com.example.demoSpringSecurity.security.JwtIssuer;
import com.example.demoSpringSecurity.security.UserPrinciple;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private JwtIssuer jwtIssuer;
	@Autowired
	private AuthenticationManager authenticationManager;

	public LoginResponse login(String email, String password) {
		
		var authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		var principal = (UserPrinciple) authentication.getPrincipal();

		var roles = principal.getAuthorities().stream().map(grantedAuthority -> grantedAuthority.getAuthority())
				.toList();

		var token = jwtIssuer.issuer(principal.getUserID(), principal.getEmail(), roles);

		return LoginResponse.builder().accessToken(token).build();
	}

	public UserEntity signup(RegisterUserDto input) {
		UserEntity user = new UserEntity(input.getEmail(), passwordEncoder.encode(input.getPassword()));
		user.setVerificationCode(generateVerificationCode());
		user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
		user.setEnabled(false);
		user.setRole("ROLE_USER");
		user.setCreateDate(LocalDateTime.now());
		sendVerificationEmail(user);
		return userRepository.save(user);
	}

	public void verifyUser(VerifyUserDto input) {
		Optional<UserEntity> optionalUser = userRepository.findByEmail(input.getEmail());
		if (optionalUser.isPresent()) {
			UserEntity user = optionalUser.get();
			if (user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
				throw new RuntimeException("Verification code has expired");
			}
			if (user.getVerificationCode().equals(input.getVerificationCode())) {
				user.setEnabled(true);
				user.setVerificationCode(null);
				user.setVerificationCodeExpiresAt(null);
				userRepository.save(user);
			} else {
				throw new RuntimeException("Invalid verification code");
			}
		} else {
			throw new RuntimeException("User not found");
		}
	}

	private void sendVerificationEmail(UserEntity user) {
		String subject = "Account Verification";
		String verificationCode = "VERIFICATION CODE " + user.getVerificationCode();
		String htmlMessage = "<html>" + "<body style=\"font-family: Arial, sans-serif;\">"
				+ "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
				+ "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
				+ "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
				+ "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
				+ "<h3 style=\"color: #333;\">Verification Code:</h3>"
				+ "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
				+ "</div>" + "</div>" + "</body>" + "</html>";

		try {
			emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private String generateVerificationCode() {
		Random random = new Random();
		int code = random.nextInt(900000) + 100000;
		return String.valueOf(code);
	}
}
