package com.example.demoSpringSecurity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserEntity {
	private long id;
	private String email;

	@JsonIgnore
	private String password;
	private String role;
	private String name;
}
