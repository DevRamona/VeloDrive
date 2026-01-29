package com.ramona.capstone.dtos;

import com.ramona.capstone.services.Jwt;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginResponse {
  private Jwt accessToken;
  private Jwt refreshToken;
}
