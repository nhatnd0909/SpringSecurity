package com.example.demoSpringSecurity.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demoSpringSecurity.model.UserEntity;
import com.example.demoSpringSecurity.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public Optional<UserEntity> findByEmail(String email) {

		var user = userRepository.findByEmail(email);
		System.out.println(user);
		if (user.isPresent()) {
			return user;
		}
		return Optional.empty();
	}
}
