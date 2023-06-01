package com.stannard.liam.auth;


import com.stannard.liam.config.JwtService;
import com.stannard.liam.user.Role;
import com.stannard.liam.user.User;
import com.stannard.liam.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService
{
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest registerRequest)
    {
        User user = new User(
                registerRequest.getUsername(),
                Role.ROLE_USER,
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword()));
        System.out.print("USER IS: " + user.toString());

        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);


    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword()));

        UserDetails user = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow();

        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }
}
