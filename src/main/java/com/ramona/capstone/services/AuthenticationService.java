package com.ramona.capstone.services;

import com.ramona.capstone.dtos.LoginRequest;
import com.ramona.capstone.dtos.LoginResponse;
import com.ramona.capstone.dtos.RegisterUserRequest;
import com.ramona.capstone.dtos.UserDto;
import com.ramona.capstone.entities.User;
import com.ramona.capstone.exceptions.DuplicateUserException;
import com.ramona.capstone.mappers.UserMapper;
import com.ramona.capstone.models.Role;
import com.ramona.capstone.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  public LoginResponse login(LoginRequest loginRequest) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getEmail(), loginRequest.getPassword()));
    var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
    var accessToken = jwtService.generateAccessToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    return new LoginResponse(accessToken, refreshToken);
  }

  public UserDto register(RegisterUserRequest userRequest) {
    if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
      throw new DuplicateUserException("Email already in use");
    }
    User user = new User();
    user.setName(userRequest.getName());
    user.setEmail(userRequest.getEmail());
    user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
    user.setRole(Role.USER);
    return userMapper.toDto(userRepository.save(user));
  }

  public Jwt refreshToken(String refreshToken) {
    var jwt = jwtService.parseToken(refreshToken);
    if (jwt == null || jwt.isExpired()) {
      throw new BadCredentialsException("Invalid refresh token");
    }
    var user = userRepository.findById(jwt.getUserId()).orElseThrow();
    return jwtService.generateRefreshToken(user);
  }
}
