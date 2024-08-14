package com.datzxje.hotelmanagement.dto;

import com.datzxje.hotelmanagement.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {

    private String jwt;

    private Long userId;

    private UserRole userRole;
}
