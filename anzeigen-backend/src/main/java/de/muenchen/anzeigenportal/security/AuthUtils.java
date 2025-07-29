package de.muenchen.anzeigenportal.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Utilities for authentication data.
 */
@Slf4j
public final class AuthUtils {

    public static final String NAME_UNAUTHENTICATED_USER = "unauthenticated";

    private static final String TOKEN_USER_NAME = "user_name";
    private static final String TOKEN_LHM_OBJECT_ID = "lhmObjectID";
    private static final String TOKEN_AUTHORITIES = "authorities";

    private AuthUtils() {
    }

    /**
     * Extracts the lhmObjectID from the existing Spring Security Context via
     * {@link SecurityContextHolder}.
     *
     * @return the username or an "unauthenticated" if no {@link Authentication} exists
     */
    public static String getLhmObjectID() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            log.debug("User authenticated: {}", jwtAuth.getTokenAttributes().getOrDefault(TOKEN_LHM_OBJECT_ID, null));
            return (String) jwtAuth.getTokenAttributes().getOrDefault(TOKEN_LHM_OBJECT_ID, null);
        } else {
            log.debug("User not authenticated");
            return NAME_UNAUTHENTICATED_USER;
        }
    }

    public static List<AuthoritiesEnum> getRoles() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {

            @SuppressWarnings("unchecked")
            List<String> stringRoles = (List<String>) jwtAuth.getTokenAttributes().getOrDefault(TOKEN_AUTHORITIES, List.of());
            List<AuthoritiesEnum> roles = new ArrayList<>();
            if (stringRoles != null) {
                for (String role : stringRoles) {
                    try {
                        AuthoritiesEnum rolleEnum = AuthoritiesEnum.valueOf(role);
                        roles.add(rolleEnum);
                    } catch (IllegalArgumentException e) {
                        log.warn(String.format("Could not map authority '%s' from sso to application ", role));
                    }
                }
            }
            return roles;
        }
        return new ArrayList<>();
    }

    /**
     * Extracts the user name from the existing Spring Security Context via
     * {@link SecurityContextHolder}.
     *
     * @return the username or an "unauthenticated" if no {@link Authentication} exists
     */
    public static String getUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            final JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
            log.debug("JWT: {}", jwtAuth.getTokenAttributes().toString());
            return (String) jwtAuth.getTokenAttributes().getOrDefault(TOKEN_USER_NAME, null);
        } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
            final UsernamePasswordAuthenticationToken usernameAuth = (UsernamePasswordAuthenticationToken) authentication;
            return usernameAuth.getCredentials().toString();
        } else {
            return NAME_UNAUTHENTICATED_USER;
        }
    }

}
