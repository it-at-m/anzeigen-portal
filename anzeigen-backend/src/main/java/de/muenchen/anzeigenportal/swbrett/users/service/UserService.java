package de.muenchen.anzeigenportal.swbrett.users.service;

import de.muenchen.intranet.sbrett.swbrett.users.model.SwbUser;
import de.muenchen.intranet.sbrett.swbrett.users.model.SwbUserTO;
import de.muenchen.intranet.sbrett.swbrett.users.repository.UserRepository;
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

    public SwbUser saveOrGet(SwbUser user) {
        Optional<SwbUser> existingUser = repository.findByLhmObjectId(user.getLhmObjectId());

        return existingUser.orElse(repository.save(user));
    }

    @PreAuthorize("hasAuthority(T(de.muenchen.intranet.sbrett.security.AuthoritiesEnum).BACKEND_READ_THEENTITY.name())")
    public SwbUserTO getUser(long id) {
        SwbUser user = repository.getOne(id);
        return mapper.toSwbUserTO(user);
    }

    public Optional<SwbUserTO> findUser(String lhmObjectId) {
        Optional<SwbUser> user = repository.findByLhmObjectId(lhmObjectId);

        if (user.isPresent()) {
            return Optional.of(mapper.toSwbUserTO(user.get()));
        } else {
            return Optional.empty();
        }
    }

    public SwbUserTO createUser(SwbUserTO userTO) {
        SwbUser savedUser = repository.save(mapper.toSwbUser(userTO));
        return mapper.toSwbUserTO(savedUser);
    }
}
