package de.muenchen.anzeigenportal.swbrett.users.service;

import de.muenchen.anzeigenportal.security.AuthUtils;
import de.muenchen.anzeigenportal.security.AuthoritiesEnum;
import de.muenchen.anzeigenportal.swbrett.users.model.SwbUser;
import de.muenchen.anzeigenportal.swbrett.users.model.SwbUserTO;
import de.muenchen.anzeigenportal.swbrett.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    private final AuthUtils authUtils;

    public SwbUser saveOrGet(final SwbUser user) {
        final Optional<SwbUser> existingUser = repository.findByLhmObjectId(user.getLhmObjectId());

        return existingUser.orElse(repository.save(user));
    }

    @PreAuthorize("hasAuthority(T(de.muenchen.anzeigenportal.security.AuthoritiesEnum).fachadmin.name())")
    public SwbUserTO getUser(final long id) {
        final SwbUser user = repository.getOne(id);
        return mapper.toSwbUserTO(user);
    }

    public Optional<SwbUserTO> findUser(final String lhmObjectId) {
        final Optional<SwbUser> user = repository.findByLhmObjectId(lhmObjectId);

        return user.map(mapper::toSwbUserTO);
    }

    public SwbUserTO createUser(final SwbUserTO userTO) {
        final SwbUser savedUser = repository.save(mapper.toSwbUser(userTO));
        return mapper.toSwbUserTO(savedUser);
    }

    public boolean isCurrentUser(final Long currentUserId) {
        final SwbUserTO currentUser = this.findUser(authUtils.getLhmObjectID())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthenticated"));

        log.debug("Current User: {}", currentUser.getId());

        return currentUser.getId().equals(currentUserId);
    }

    public boolean currentUserIsAdmin() {
        return authUtils.getRoles().contains(AuthoritiesEnum.fachadmin);
    }
}
