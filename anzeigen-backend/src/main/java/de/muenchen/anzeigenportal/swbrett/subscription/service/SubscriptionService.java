package de.muenchen.anzeigenportal.swbrett.subscription.service;

import de.muenchen.anzeigenportal.swbrett.ads.model.AdCategory;
import de.muenchen.anzeigenportal.swbrett.ads.service.AdCategoryService;
import de.muenchen.anzeigenportal.swbrett.subscription.model.Subscription;
import de.muenchen.anzeigenportal.swbrett.subscription.model.SubscriptionTO;
import de.muenchen.anzeigenportal.swbrett.subscription.repository.SubscriptionRepository;
import de.muenchen.anzeigenportal.swbrett.users.model.SwbUser;
import de.muenchen.anzeigenportal.swbrett.users.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final UserService userService;

    private final AdCategoryService adCategoryService;

    static private final int MAXIMUM_EXPIRY_DAYS_SUBSCRIPTION = 30;
    // static private final int SOON_EXPIRY_REMINDER_DAYS = 3;

    public SubscriptionTO createSubscription(final Long categoryId) {
        final SwbUser currentUser = userService.getCurrentUser();

        final Optional<Subscription> existingSubscription = subscriptionRepository
                .findBySwbUser_IdAndAdCategory_Id(currentUser.getId(), categoryId);

        if (existingSubscription.isPresent()) {
            return SubscriptionMapper.INSTANCE.toDTO(existingSubscription.get());
        }

        final AdCategory adCategory = adCategoryService.getAdCategory(categoryId);

        final Subscription subscription = new Subscription();
        subscription.setSubscriptionDate(LocalDate.now());
        subscription.setSwbUser(currentUser);
        subscription.setAdCategory(adCategory);

        final Subscription savedSubscription = subscriptionRepository.save(subscription);

        return SubscriptionMapper.INSTANCE.toDTO(savedSubscription);
        // TODO: send E-Mail Notification?
    }

    @Transactional
    public void deleteSubscription(final long subscriptionId) {
        final Integer deleted = subscriptionRepository.deleteBySwbUser_IdAndAdCategory_Id(userService.getCurrentUser().getId(), subscriptionId);
        log.debug(deleted.toString());
        // TODO - fail silently anyway? - yes
    }

    public List<SubscriptionTO> getAllSubscriptionOfCategory(final Long categoryId) {
        return subscriptionRepository.findByAdCategory_Id(categoryId)
                .stream()
                .map(SubscriptionMapper.INSTANCE::toDTO)
                .toList();
    }

    public SubscriptionTO getUserSubscriptionForCategory(final Long categoryId) {
        final SwbUser currentUser = userService.getCurrentUser();

        final Subscription foundSubscription = subscriptionRepository.findBySwbUser_IdAndAdCategory_Id(currentUser.getId(), categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return SubscriptionMapper.INSTANCE.toDTO(foundSubscription);
    }

    public List<SubscriptionTO> getAllSubscriptionOfUser() {
        final SwbUser currentUser = userService.getCurrentUser();
        return subscriptionRepository.findBySwbUser_Id(currentUser.getId())
                .stream()
                .map(SubscriptionMapper.INSTANCE::toDTO)
                .toList();
    }

    // TODO - used later
    public List<SubscriptionTO> getAllSoonExpiringSubscripions() {
        // thought prozess: this will get called daily so we do not want to remind every subscriber daily after time threshold
        // to mitigate this issue we only get the subscriptions of a specific date

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    // TODO - used later
    public void deleteExpiredSubscriptions() {
        final LocalDate expiryDate = LocalDate.now().plusDays(MAXIMUM_EXPIRY_DAYS_SUBSCRIPTION);
        subscriptionRepository.deleteBySubscriptionDateAfter(expiryDate);
    }

}
