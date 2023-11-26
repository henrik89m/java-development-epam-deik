package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.dto.UserDto;
import com.epam.training.ticketservice.model.Roles;
import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.service.AuthenticationService;
import com.epam.training.ticketservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final UserService userService;

    @Override
    public void signup(String username, String password) {
        try {
            userService.createUser(username, password);
        } catch (AuthenticationException e) {
            throw new AuthenticationException("Sign up failed") {
            };
        }
    }

    @Override
    public Optional<UserDto> signIn(String username, String password, boolean privileged) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDto userDto = new UserDto(username, Roles.USER);
            return Optional.of(userDto);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new AuthenticationException("Login failed due to some error: " + e.getMessage()) {
            };
        }
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();

    }

}
