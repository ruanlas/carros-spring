package com.example.carros.security.jwt;

import lombok.Data;

@Data
public class JwtLoginInput {
	private String username;
    private String password;
}
