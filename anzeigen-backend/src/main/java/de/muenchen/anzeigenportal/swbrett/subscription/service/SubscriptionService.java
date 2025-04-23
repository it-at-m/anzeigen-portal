package de.muenchen.anzeigenportal.swbrett.subscription.service;

import de.muenchen.anzeigenportal.swbrett.ads.service.AdCategoryService;
import de.muenchen.anzeigenportal.swbrett.subscription.model.Subscription;
import de.muenchen.anzeigenportal.swbrett.subscription.repository.SubscriptionRepository;
import de.muenchen.anzeigenportal.swbrett.users.model.SwbUser;
import de.muenchen.anzeigenportal.swbrett.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final UserService userService;

    private final AdCategoryService adCategoryService;

    static private final int MAXIMUM_EXPIRY_DAYS_SUBSCRIPTION = 30;
    static private final int SOON_EXPIRY_REMINDER_DAYS = 3;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserService userService, AdCategoryService adCategoryService) {
        this.subscriptionRepository = subscriptionRepository;
        this.userService = userService;
        this.adCategoryService = adCategoryService;
    }

    public Subscription createSubscription(Long categoryId) {
        final SwbUser currentUser = userService.getCurrentUser();

        final Optional<Subscription> existingSubscription = subscriptionRepository
                .findBySwbUser_IdAndAdCategory_Id(currentUser.getId(), categoryId);

        if (existingSubscription.isPresent()) {
            return existingSubscription.get();
        }

        Subscription subscription = new Subscription();
        subscription.setSubscriptionDate(LocalDate.now());
        subscription.setSwbUser(currentUser);
        subscription.setAdCategory(adCategoryService.getAdCategory(categoryId));

        return subscriptionRepository.save(subscription);

        // TODO: send E-Mail Notification?
    }

    public void deleteSubscription(Long subscriptionId) {
        final boolean isDeleted = subscriptionRepository.deleteBySwbUser_IdAndAdCategory_Id(userService.getCurrentUser().getId(), subscriptionId);

        // TODO - fail silently anyway? - yes
    }

    public List<Subscription> getAllSubscriptionOfCategory(Long categoryId) {
        return subscriptionRepository.findByAdCategory_Id(categoryId);
    }

    public Subscription getUserSubscriptionForCategory(Long categoryId) {
        SwbUser currentUser = userService.getCurrentUser();
        return subscriptionRepository.findBySwbUser_IdAndAdCategory_Id(currentUser.getId(), categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Subscription> getAllSubscriptionOfUser() {
        final SwbUser currentUser = userService.getCurrentUser();
        return subscriptionRepository.findBySwbUser_Id(currentUser.getId());
    }

    // TODO - used later
    public List<Subscription> getAllSoonExpiringSubscripions() {
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
