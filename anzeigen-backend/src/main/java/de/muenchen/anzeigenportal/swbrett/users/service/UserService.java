package de.muenchen.anzeigenportal.swbrett.users.service;

import de.muenchen.anzeigenportal.swbrett.users.model.SwbUser;
import de.muenchen.anzeigenportal.swbrett.users.model.SwbUserTO;
import de.muenchen.anzeigenportal.swbrett.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserMapper mapper;

    public SwbUser saveOrGet(final SwbUser user) {
        final Optional<SwbUser> existingUser = repository.findByLhmObjectId(user.getLhmObjectId());

        return existingUser.orElse(repository.save(user));
    }

    @PreAuthorize("hasAuthority(T(de.muenchen.intranet.sbrett.security.AuthoritiesEnum).BACKEND_READ_THEENTITY.name())")
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
}
