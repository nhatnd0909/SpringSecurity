package com.example.demoSpringSecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demoSpringSecurity.model.LoginResponse;
import com.example.demoSpringSecurity.security.JwtIssuer;
import com.example.demoSpringSecurity.security.UserPrinciple;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	@Autowired
	private JwtIssuer jwtIssuer;
	@Autowired
	private AuthenticationManager authenticationManager;

	public LoginResponse attemplogin(String email, String password) {
		var authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(email, password));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		var principal = (UserPrinciple) authentication.getPrincipal();

		var roles = principal.getAuthorities().stream().map(grantedAuthority -> grantedAuthority.getAuthority())
				.toList();

		var token = jwtIssuer.issuer(principal.getUserID(), principal.getEmail(), roles);

		return LoginResponse.builder().accessToken(token).build();
	}

}
