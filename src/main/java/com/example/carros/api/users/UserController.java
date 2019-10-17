package com.example.carros.api.users;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.carros.security.jwt.JwtLoginInput;
import com.example.carros.security.jwt.JwtUtil;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService service;
	
	@GetMapping()
	public ResponseEntity get() {
		List<UserDTO> list = service.getUsers();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/info")
//	public UserDTO userInfo(@AuthenticationPrincipal UserDetails user) {
	public UserDTO userInfo(@AuthenticationPrincipal User user) {
		
//		UserDetails userDetails = JwtUtil.getUserDetails();
//		return UserDTO.create((User) userDetails);
		
		return UserDTO.create(user);
	}
}
