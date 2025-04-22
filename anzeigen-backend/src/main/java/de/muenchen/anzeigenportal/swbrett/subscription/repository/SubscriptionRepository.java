package de.muenchen.anzeigenportal.swbrett.subscription.repository;

import de.muenchen.anzeigenportal.swbrett.subscription.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    List<Subscription> findBySwbUser_Id(Long swbUser_id);

    List<Subscription> findByAdCategory_Id(Long adCategory_id);

}
