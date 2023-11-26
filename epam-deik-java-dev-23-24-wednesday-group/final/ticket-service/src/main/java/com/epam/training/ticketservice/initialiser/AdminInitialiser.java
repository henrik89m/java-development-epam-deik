package com.epam.training.ticketservice.initialiser;

import com.epam.training.ticketservice.model.Roles;
import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class AdminInitialiser {

    private final UserRepository userRepository;

    @PostConstruct
    private void createAdmin(){
        var admin = userRepository.findByUsername("admin");
        if (admin.isEmpty()) userRepository.save(new User("admin", "admin", Roles.ADMIN));
    }
}
