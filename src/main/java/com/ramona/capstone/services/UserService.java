package com.ramona.capstone.services;

import com.ramona.capstone.dtos.RegisterUserRequest;
import com.ramona.capstone.dtos.UpdateUserRequest;
import com.ramona.capstone.dtos.UserDto;
import com.ramona.capstone.exceptions.DuplicateUserException;
import com.ramona.capstone.exceptions.UserNotFoundException;
import com.ramona.capstone.mappers.UserMapper;
import com.ramona.capstone.models.Role;
import com.ramona.capstone.repositories.UserRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  public Iterable<UserDto> getAllUsers(String sortBy) {
    if (!Set.of("name", "email").contains(sortBy)) sortBy = "name";

    return userRepository.findAll(Sort.by(sortBy)).stream().map(userMapper::toDto).toList();
  }

  public UserDto getUser(Long userId) {
    var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    return userMapper.toDto(user);
  }

  public UserDto registerUser(RegisterUserRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new DuplicateUserException("Email already exists");
    }

    var user = userMapper.toEntity(request);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole(Role.USER);
    userRepository.save(user);

    return userMapper.toDto(user);
  }

  public UserDto updateUser(Long userId, UpdateUserRequest request) {
    var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    userMapper.update(request, user);
    userRepository.save(user);

    return userMapper.toDto(user);
  }

  public void deleteUser(Long userId) {
    var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    userRepository.delete(user);
  }
}
