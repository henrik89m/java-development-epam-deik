package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.model.Roles;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.Availability;

import java.util.Collection;

public class PriviligeCommand {

    public Availability isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Availability.unavailable("You are not signed in");
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities.stream().anyMatch(authority ->
                authority.getAuthority().equals(Roles.ADMIN.name()))) {
            return Availability.available();
        } else {
            return Availability.unavailable("Permission denied");
        }
    }

    protected Availability isSignIn() {
        var authentication = getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            return Availability.available();
        }
        return Availability.unavailable("Not signed in");
    }

    protected Availability isSignedOut() {
        var authentication = getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            return Availability.unavailable("Already signed in");
        }
        return Availability.available();
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
