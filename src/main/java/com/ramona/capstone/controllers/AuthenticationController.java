package com.ramona.capstone.controllers;

import com.ramona.capstone.config.JwtConfig;
import com.ramona.capstone.config.JwtResponse;
import com.ramona.capstone.dtos.LoginRequest;
import com.ramona.capstone.dtos.RegisterUserRequest;
import com.ramona.capstone.dtos.UserDto;
import com.ramona.capstone.mappers.UserMapper;
import com.ramona.capstone.services.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final JwtConfig jwtConfig;
    private final UserMapper userMapper;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public JwtResponse login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse) {
        var loginResult = authenticationService.login(loginRequest);
        var refreshToken = loginResult.getRefreshToken().toString();
        var cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
        cookie.setSecure(true);
        httpServletResponse.addCookie(cookie);
        return new JwtResponse(loginResult.getAccessToken().toString());
    }
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterUserRequest userRequest){
        var userDto = authenticationService.register(userRequest);
        return ResponseEntity.ok(userDto);

    }
    public JwtResponse refresh(@CookieValue(value="refreshToken") String refreshToken) {
        var accessToken = authenticationService.refreshToken(refreshToken);
        return new  JwtResponse(accessToken.toString());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
