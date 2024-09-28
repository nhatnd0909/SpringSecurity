package com.example.demoSpringSecurity.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demoSpringSecurity.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = userService.findByEmail(username).orElseThrow();
		
		 return UserPrinciple.builder()
	                .userID(user.getId())
	                .email(user.getEmail())
	                .password(user.getPassword())
	                .authorities(List.of(new SimpleGrantedAuthority(user.getRole())))
	                .build();
	}
	
}
