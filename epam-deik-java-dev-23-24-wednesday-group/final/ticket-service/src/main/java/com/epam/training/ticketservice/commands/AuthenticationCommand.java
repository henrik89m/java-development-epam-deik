package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.dto.UserDto;
import com.epam.training.ticketservice.model.Roles;
import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.repository.UserRepository;
import com.epam.training.ticketservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class AuthenticationCommand extends PriviligeCommand {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @ShellMethod(key = "sign up", value = "Usage: <username> <password>")
    @ShellMethodAvailability("isSignOut")
    public String signup(String username, String password){
        try{
            authenticationService.signup(username, password);
            return "Sign up was successful";
        }catch (AuthenticationException e){
            return e.getMessage();
        }
    }

    @ShellMethod(key = "sign in privileged", value = "Usage: <username> <password>")
    @ShellMethodAvailability("isSignedOut")
    public String loginAdmin(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (isUserAdmin(user, password)) {
                authenticateUser(user);
                return "Successfully signed in as admin";
            } else {
                return "Invalid credentials or not an admin user";
            }
        } else {
            return "User not found";
        }
    }

    @ShellMethodAvailability("isSignedOut")
    @ShellMethod(key = "sign in", value = "Usage: <username> <password>")
    public String login(String username, String password) {
       Optional<UserDto> userAccount = authenticationService.signIn(username, password, false);

        return userAccount.isPresent() ? "Login success" : "Login failed";

    }

    @ShellMethodAvailability("isSignIn")
    @ShellMethod(key = "sign out", value = "sign out of the administrator account")
    public String logout(){
        try {
            authenticationService.logout();
            return "Sign out was successful";
        }catch (AuthenticationException e){
            return e.getMessage();
        }
    }

    private boolean isUserAdmin(User user, String password) {
        return user.getPassword().equals(password) && user.getRole() == Roles.ADMIN;
    }

    private void authenticateUser(User user) {
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(Roles.ADMIN.name()));
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
