package com.datzxje.hotelmanagement.controller.auth;

import com.datzxje.hotelmanagement.dto.AuthenticationResponse;
import com.datzxje.hotelmanagement.dto.LoginDto;
import com.datzxje.hotelmanagement.dto.SignUpDto;
import com.datzxje.hotelmanagement.dto.UserDto;
import com.datzxje.hotelmanagement.entity.User;
import com.datzxje.hotelmanagement.repository.UserRepository;
import com.datzxje.hotelmanagement.services.auth.AuthService;
import com.datzxje.hotelmanagement.services.jwt.UserService;
import com.datzxje.hotelmanagement.util.JWTUtil;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final AuthenticationManager authManager;

    private final UserRepository userRepository;

    private final JWTUtil jwtUtil;

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestBody SignUpDto signUpDto) {
        try {
            UserDto createdUser = authService.createUser(signUpDto);
            return new ResponseEntity<>(createdUser, HttpStatus.OK);
        } catch (EntityExistsException entityExistsException) {
            return new ResponseEntity<>("User already existed", HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot create user", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public AuthenticationResponse createAuthToken(@RequestBody LoginDto loginDto) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email or password", e);
        }

        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(loginDto.getEmail());
        Optional<User> optionalUser = userRepository.findByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        AuthenticationResponse authResponse = new AuthenticationResponse();
        if(optionalUser.isPresent()) {
            authResponse.setJwt(jwt);
            authResponse.setUserRole(optionalUser.get().getUserRole());
            authResponse.setUserId(optionalUser.get().getId());
        }

        return authResponse;
    }

}
