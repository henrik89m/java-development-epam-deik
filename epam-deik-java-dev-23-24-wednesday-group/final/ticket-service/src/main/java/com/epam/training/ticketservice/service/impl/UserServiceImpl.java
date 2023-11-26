package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.dto.UserDto;
import com.epam.training.ticketservice.model.Roles;
import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public Optional<UserDto> createUser(String username, String password) {
        Objects.requireNonNull(username, "username cannot be null");
        Objects.requireNonNull(password, "password cannot be null");

        if (userRepository.existsByUsername(username)){
            throw new IllegalArgumentException("User already exist");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setRole(Roles.USER);

        User savedUser = userRepository.save(user);
        UserDto userDto = new UserDto(savedUser.getUsername(), savedUser.getRole());

        return Optional.of(userDto);
    }
}
