package com.epam.training.ticketservice.component;

import com.epam.training.ticketservice.model.Roles;
import com.epam.training.ticketservice.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@NoArgsConstructor
public class SecurityDetails implements UserDetailsService {

    private UserRepository repository;

    @Autowired
    public SecurityDetails(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.epam.training.ticketservice.model.User> optionalUser = repository.findByUsername(username);
        if (optionalUser.isEmpty()){
            throw  new UsernameNotFoundException("User Does not exist");
        }
        var user = optionalUser.get();
        var builder = User.withUsername(username).password(user.getPassword());

        if (user.getRole() == Roles.ADMIN){
            builder.roles("USER", "ADMIN");
        } else if (user.getRole() == Roles.USER) {
            builder.roles("USER");
        }
        return builder.build();
    }
}
