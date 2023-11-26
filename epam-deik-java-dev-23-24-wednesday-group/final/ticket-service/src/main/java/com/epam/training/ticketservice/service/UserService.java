package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dto.UserDto;
import com.epam.training.ticketservice.model.User;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface UserService {

   Optional<UserDto> createUser(String username, String password);


}
