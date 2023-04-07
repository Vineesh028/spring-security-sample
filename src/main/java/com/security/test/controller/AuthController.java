package com.security.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.test.model.JWTAuthResponse;
import com.security.test.model.Login;
import com.security.test.model.UserModel;
import com.security.test.service.AuthService;


@RestController
public class AuthController {

	private AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping(value = { "/login", "/signin" })
	public ResponseEntity<JWTAuthResponse> login(@RequestBody Login login) {

		String token = authService.login(login);
		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();

		jwtAuthResponse.setAccessToken(token);

		return ResponseEntity.ok(jwtAuthResponse);

	}

	@PostMapping(value = { "/register", "/signup" })
	public ResponseEntity<String> register(@RequestBody UserModel userModel) {
		String response = authService.register(userModel);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

}