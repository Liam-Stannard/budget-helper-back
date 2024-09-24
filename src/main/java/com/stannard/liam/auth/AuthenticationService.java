package com.stannard.liam.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stannard.liam.config.JwtService;
import com.stannard.liam.token.Token;
import com.stannard.liam.token.TokenRepository;
import com.stannard.liam.user.Role;
import com.stannard.liam.user.User;
import com.stannard.liam.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  @Autowired
  private final UserRepository userRepository;
  @Autowired
  private final PasswordEncoder passwordEncoder;
  @Autowired
  private final JwtService jwtService;
  @Autowired
  private final AuthenticationManager authenticationManager;

  @Autowired
  private final TokenRepository tokenRepository;

  public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
      JwtService jwtService, AuthenticationManager authenticationManager,
      TokenRepository tokenRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
    this.tokenRepository = tokenRepository;
  }

  public AuthenticationResponse register(RegisterRequest registerRequest) {
    User user = User.builder().username(registerRequest.getUsername()).role(Role.ROLE_USER)
        .email(registerRequest.getEmail())
        .password(passwordEncoder.encode(registerRequest.getPassword())).build();

    userRepository.save(user);

    String accessToken = jwtService.generateToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, accessToken);

    return new AuthenticationResponse(accessToken, refreshToken);


  }

  public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
            authenticationRequest.getPassword()));

    UserDetails user = userRepository.findByUsername(authenticationRequest.getUsername())
        .orElseThrow();

    String accessToken = jwtService.generateToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);
    return new AuthenticationResponse(accessToken, refreshToken);
  }

  public void saveUserToken(User user, String jwt) {
    Token token = Token.builder().token(jwt).user(user).build();
    tokenRepository.save(token);
  }

  public void revokeAllUserTokens(User user) {
    List<Token> tokens = tokenRepository.findAllValidTokenByUser(user.getId());

    if (tokens.isEmpty()) {
      return;
    }
    tokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });

    tokenRepository.saveAll(tokens);
  }

  public void refreshToken(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);

    userEmail = jwtService.extractUsername(refreshToken);

    if (userEmail != null) {
      User user = this.userRepository.findUserByEmail(userEmail).orElseThrow();

      if (jwtService.isTokenValid(refreshToken, user)) {
        String accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        var authResponse = new AuthenticationResponse(accessToken, refreshToken);

        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
