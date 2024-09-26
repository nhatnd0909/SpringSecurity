package com.example.demoSpringSecurity.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class UserPrincipalAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 1L;

	private UserPrinciple principle;

	public UserPrincipalAuthenticationToken(UserPrinciple principle) {
		super(principle.getAuthorities());
		this.principle = principle;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public UserPrinciple getPrincipal() {
		return principle;
	}

}
