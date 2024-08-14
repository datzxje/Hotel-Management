package com.datzxje.hotelmanagement.services.auth;

import com.datzxje.hotelmanagement.dto.SignUpDto;
import com.datzxje.hotelmanagement.dto.UserDto;

public interface AuthService {
    UserDto createUser(SignUpDto signUpDto);
}
