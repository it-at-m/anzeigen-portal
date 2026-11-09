package de.muenchen.anzeigenportal.swbrett.users.repository;

import de.muenchen.anzeigenportal.swbrett.users.model.SwbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SwbUser, Long> {

    Optional<SwbUser> findByLhmObjectId(String lhmObjectId);
}
