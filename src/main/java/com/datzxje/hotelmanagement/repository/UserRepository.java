package com.datzxje.hotelmanagement.repository;

import com.datzxje.hotelmanagement.entity.User;
import com.datzxje.hotelmanagement.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUserRole(UserRole userRole);
}
