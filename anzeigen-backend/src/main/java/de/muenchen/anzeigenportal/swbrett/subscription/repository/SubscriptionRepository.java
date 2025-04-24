package de.muenchen.anzeigenportal.swbrett.subscription.repository;

import de.muenchen.anzeigenportal.swbrett.subscription.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings({ "PMD.MethodNamingConventions", "PMD.FormalParameterNamingConventions" })
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    List<Subscription> findBySwbUser_Id(Long swbUser_id);

    List<Subscription> findByAdCategory_Id(Long adCategory_id);

    Optional<Subscription> findBySwbUser_IdAndAdCategory_Id(Long swbUser_id, Long adCategory_id);

    Integer deleteBySwbUser_IdAndAdCategory_Id(Long swbUser_id, long adCategory_id);

    boolean existsBySwbUser_IdAndAdCategory_Id(Long swbUser_id, Long adCategory_id);

    void deleteBySubscriptionDateAfter(LocalDate subscriptionDate);
}
