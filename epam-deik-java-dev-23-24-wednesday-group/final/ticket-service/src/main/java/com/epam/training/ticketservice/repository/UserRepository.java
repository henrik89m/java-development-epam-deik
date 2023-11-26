package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.dto.UserDto;
import com.epam.training.ticketservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<UserDto> findByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);
}
