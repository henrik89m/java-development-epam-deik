package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import lombok.RequiredArgsConstructor;

@ShellComponent
@RequiredArgsConstructor
public class UserCommand extends PriviligeCommand{
    private final UserService userService;

    @ShellMethod(key = "describe account", value = "description of account")
    public String describe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() == null) {
            return "No authenticated user found.";
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof User) {
            User currentUser = (User) principal;
            return "Username: " + currentUser.getUsername() + ", Role: " + currentUser.getRole();
        } else if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            StringBuilder roles = new StringBuilder();
            for (GrantedAuthority authority : userDetails.getAuthorities()) {
                roles.append(authority.getAuthority()).append(" ");
            }
            return "Username: " + userDetails.getUsername() + ", Roles: " + roles.toString().trim();
        } else {
            return "No authenticated user found.";
        }
    }

    @ShellMethodAvailability("isSignOut")
    @ShellMethod(key = "create user", value = "Usage: <username> <password>")
    public String createUser(String username, String password){
        try {
            var res = userService.createUser(username,password);
            return "User created successfully";
        }catch (Exception e){
            return e.getMessage();
        }
    }
}
