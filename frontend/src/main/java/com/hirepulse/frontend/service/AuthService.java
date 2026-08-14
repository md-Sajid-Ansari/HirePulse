package com.hirepulse.frontend.service;

import com.hirepulse.frontend.model.User;
import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService implements UserDetailsService {

    private final Map<String, User> userMap = new ConcurrentHashMap<>();
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final AuthenticationContext authenticationContext;

    public AuthService(AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;

        // Seed demo accounts
        registerUser(new User(
                "candidate",
                "password123",
                "Alex Rivera",
                "alex.rivera@example.com",
                User.Role.CANDIDATE,
                "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&w=200&q=80"
        ));

        registerUser(new User(
                "employer",
                "password123",
                "Sarah Connor",
                "sarah.c@techcorp.io",
                User.Role.EMPLOYER,
                "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?auto=format&fit=crop&w=200&q=80"
        ));

        registerUser(new User(
                "admin",
                "password123",
                "System Admin",
                "admin@hirepulse.io",
                User.Role.ADMIN,
                "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=200&q=80"
        ));
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public boolean registerUser(User user) {
        if (userMap.containsKey(user.getUsername().toLowerCase())) {
            return false;
        }
        // Encode password if not already encoded
        if (!user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userMap.put(user.getUsername().toLowerCase(), user);
        return true;
    }

    public Optional<User> findByUsername(String username) {
        if (username == null) return Optional.empty();
        return Optional.ofNullable(userMap.get(username.toLowerCase()));
    }

    public Optional<User> getAuthenticatedUser() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class)
                .flatMap(u -> findByUsername(u.getUsername()));
    }

    public void logout() {
        authenticationContext.logout();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
