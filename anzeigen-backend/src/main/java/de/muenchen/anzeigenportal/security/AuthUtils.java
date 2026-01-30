package de.muenchen.anzeigenportal.security;

import de.muenchen.anzeigenportal.configuration.SSOProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Utilities for authentication data.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public final class AuthUtils {

    private final SSOProperties ssoProperties;

    private static final String NAME_UNAUTHENTICATED_USER = "unauthenticated";

    private static final String TOKEN_USER_NAME = "user_name";
    private static final String TOKEN_LHM_OBJECT_ID = "lhmObjectID";

    /**
     * Extracts the lhmObjectID from the existing Spring Security Context via
     * {@link SecurityContextHolder}.
     *
     * @return the username or an "unauthenticated" if no {@link Authentication} exists
     */
    public String getLhmObjectID() {
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
     * Extracts the roles, which are provided by the authorization-mapper {@link SecurityContextHolder}.
     *
     * @return the list of mapped roles
     */
    public List<AuthoritiesEnum> getRoles() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof JwtAuthenticationToken jwtAuth)) {
            return List.of();
        }

        Map<String, Object> claims = jwtAuth.getTokenAttributes();

        Object resourceAccessObj = claims.get("resource_access");
        if (!(resourceAccessObj instanceof Map<?, ?> resourceAccess)) {
            log.debug("Resource access not set");
            return List.of();
        }

        Object clientObj = resourceAccess.get(ssoProperties.client());
        if (!(clientObj instanceof Map<?, ?> clientMap)) {
            log.debug("Client object not set");
            return List.of();
        }

        Object rolesObj = clientMap.get("roles");
        if (!(rolesObj instanceof List<?> rawRoles)) {
            log.debug("Roles not set");
            return List.of();
        }

        List<AuthoritiesEnum> result = new ArrayList<>();

        for (Object r : rawRoles) {
            if (r instanceof String role) {
                try {
                    result.add(AuthoritiesEnum.valueOf(role));
                } catch (IllegalArgumentException e) {
                    log.warn("Could not map authority '{}' from sso", role);
                }
            }
        }

        return result;
    }


    /**
     * Extracts the user name from the existing Spring Security Context via
     * {@link SecurityContextHolder}.
     *
     * @return the username or an "unauthenticated" if no {@link Authentication} exists
     */
    public String getUsername() {
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
