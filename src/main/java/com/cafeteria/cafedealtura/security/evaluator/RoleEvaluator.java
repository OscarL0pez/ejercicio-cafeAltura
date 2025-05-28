package com.cafeteria.cafedealtura.security.evaluator;

import com.cafeteria.cafedealtura.security.annotation.RequireRole;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class RoleEvaluator {

    public boolean hasAnyRole(RequireRole requireRole) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> Arrays.asList(requireRole.value())
                        .stream()
                        .anyMatch(role -> authority.equals("ROLE_" + role)));
    }

    public boolean hasAllRoles(RequireRole requireRole) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Collection<String> requiredRoles = Arrays.stream(requireRole.value())
                .map(role -> "ROLE_" + role)
                .collect(Collectors.toList());

        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList())
                .containsAll(requiredRoles);
    }
}