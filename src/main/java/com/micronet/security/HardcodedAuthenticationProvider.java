package com.micronet.security;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.provider.AuthenticationProvider;
import jakarta.inject.Singleton;

import java.util.Collections;

@Singleton
public class HardcodedAuthenticationProvider implements AuthenticationProvider<HttpRequest<?>, String, String> {

    // Hardcoded default user — will move to DB later
    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_PASSWORD = "admin123";

    @Override
    public AuthenticationResponse authenticate(
            HttpRequest<?> httpRequest,
            AuthenticationRequest<String, String> authenticationRequest) {

        String username = authenticationRequest.getIdentity();
        String password = authenticationRequest.getSecret();

        if (DEFAULT_USERNAME.equals(username) && DEFAULT_PASSWORD.equals(password)) {
            return AuthenticationResponse.success(username, Collections.singletonList("ROLE_USER"));
        }

        return AuthenticationResponse.failure("Invalid username or password");
    }
}
