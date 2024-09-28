package com.example.demoSpringSecurity.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
public class UserEntity {
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String email;

	@JsonIgnore
	private String password;
	private String role;
	private String name;
	@Column(name = "verification_code")
    private String verificationCode;
    @Column(name = "verification_expiration")
    private LocalDateTime verificationCodeExpiresAt;
    @Column(name = "create_date")
    private LocalDateTime createDate;
	private boolean enabled;
	public UserEntity(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	
	
}
