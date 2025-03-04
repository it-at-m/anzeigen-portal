package de.muenchen.anzeigenportal.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

/**
 * Utilities for authentication data.
 */
@Slf4j
public final class AuthUtils {

    public static final String NAME_UNAUTHENTICATED_USER = "unauthenticated";

    private static final String TOKEN_USER_NAME = "user_name";
    private static final String TOKEN_LHM_OBJECT_ID = "lhmObjectID";

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
