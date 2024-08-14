package com.datzxje.hotelmanagement.services.auth;

import com.datzxje.hotelmanagement.dto.SignUpDto;
import com.datzxje.hotelmanagement.dto.UserDto;
import com.datzxje.hotelmanagement.entity.User;
import com.datzxje.hotelmanagement.enums.UserRole;
import com.datzxje.hotelmanagement.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @PostConstruct
    public void createAdminAccount() {
        Optional<User> adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
        if (adminAccount.isEmpty()) {
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("Admin");
            user.setUserRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
            System.out.println("Admin account created");
        }
        else System.out.println("Admin account already exists");
    }

    public UserDto createUser(SignUpDto signUpDto) {
        if (userRepository.findByEmail(signUpDto.getEmail()).isPresent()) {
            throw new EntityExistsException("User already exists with email: " + signUpDto.getEmail());
        }

        User user = new User();
        user.setEmail(signUpDto.getEmail());
        user.setName(signUpDto.getName());
        user.setUserRole(UserRole.CUSTOMER);
        user.setPassword(new BCryptPasswordEncoder().encode(signUpDto.getPassword()));
        User savedUser = userRepository.save(user);
        return savedUser.getUserDto();
    }
}
