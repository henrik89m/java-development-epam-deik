package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dto.UserDto;
import com.epam.training.ticketservice.model.User;

import java.util.Optional;

public interface AuthenticationService {

    void signup(String username, String password);

    Optional<UserDto> signIn(String username, String password, boolean privileged);

    void logout();


}
