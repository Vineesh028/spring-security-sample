package com.security.test.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.test.entity.User;
import com.security.test.exception.InvalidRequestException;
import com.security.test.model.Login;
import com.security.test.model.UserModel;
import com.security.test.repository.UserRepository;
import com.security.test.security.JwtTokenProvider;


@Service
public class AuthService {

	private CustomAuthenticationProvider customAuthenticationProvider;
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;

	public AuthService(CustomAuthenticationProvider customAuthenticationProvider, UserRepository userRepository,

			PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
		this.customAuthenticationProvider = customAuthenticationProvider;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public String login(Login login) {

		Authentication authentication = customAuthenticationProvider
				.authenticate(login.getEmail(), login.getPassword());

    	if(authentication != null) {
    		SecurityContextHolder.getContext().setAuthentication(authentication);

    		String token = jwtTokenProvider.generateToken(authentication);
    		
    		return token;
    	}
    	else {
    		return null;
    	}
		

    	
	}

	public String register(UserModel userModel) {


		if (userRepository.findByEmail(userModel.getEmail()) == null) {
			User user = new User();
			user.setName(userModel.getName());

			user.setEmail(userModel.getEmail());
			user.setPassword(passwordEncoder.encode(userModel.getPassword()));

			userRepository.save(user);
		} else {

			throw new InvalidRequestException("Email is already exists!.");
		}

		return "User registered successfully!.";
	}

	public User findUser(String name) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(name);
	}
}
