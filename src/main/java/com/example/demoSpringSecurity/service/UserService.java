package com.example.demoSpringSecurity.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demoSpringSecurity.model.UserEntity;

@Service
public class UserService {
//	autowired repo

	 private static final String EXISTING_EMAIL = "admin@test.com";
	    private static final String OTHER_EMAIL = "user@test.com";

	    public Optional<UserEntity> findByEmail(String email) {
	        // All of this code would come from the database, but for simplicity of this example
	        // Let's just fake it

	        if (EXISTING_EMAIL.equalsIgnoreCase(email)) {
	            var user = new UserEntity();
	            user.setId(1L);
	            user.setEmail(EXISTING_EMAIL);
	            user.setPassword("$2a$12$OBnerD3ZrnkqY/ofkaxune1jnpUscFhTGCcuVA9x5lgAGAtr6Bss2"); // test
	            user.setRole("ROLE_ADMIN");
	            user.setName("My nice admin user");
	            return Optional.of(user);
	        } else if (OTHER_EMAIL.equalsIgnoreCase(email)) {
	            var user = new UserEntity();
	            user.setId(99L);
	            user.setEmail(OTHER_EMAIL);
	            user.setPassword("$2a$12$OBnerD3ZrnkqY/ofkaxune1jnpUscFhTGCcuVA9x5lgAGAtr6Bss2"); // test
	            user.setRole("ROLE_USER");
	            user.setName("My nice simple user");
	            return Optional.of(user);
	        } else {
	            return Optional.empty();
	        }
	    }
}
