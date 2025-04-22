package de.muenchen.anzeigenportal.swbrett.subscription.service;

import de.muenchen.anzeigenportal.swbrett.ads.model.AdCategory;
import de.muenchen.anzeigenportal.swbrett.ads.service.AdCategoryService;
import de.muenchen.anzeigenportal.swbrett.subscription.model.Subscription;
import de.muenchen.anzeigenportal.swbrett.subscription.repository.SubscriptionRepository;
import de.muenchen.anzeigenportal.swbrett.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final UserService userService;

    private final AdCategoryService adCategoryService;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserService userService, AdCategoryService adCategoryService) {
        this.subscriptionRepository = subscriptionRepository;
        this.userService = userService;
        this.adCategoryService = adCategoryService;
    }

    public void createSubscription(Long categoryId) {
        Subscription subscription = new Subscription();
        subscription.setSubscriptionDate(new Date());
        subscription.setSwbUser(userService.getCurrentUser());
        subscription.setAdCategory(adCategoryService.getAdCategory(categoryId));

        subscriptionRepository.save(subscription);

        // TODO: send E-Mail Notification?
    }

    public void deleteSubscription(Long subscriptionId) {
        final boolean isDeleted = subscriptionRepository.deleteBySwbUser_IdAndAdCategory_Id(userService.getCurrentUser().getId(), subscriptionId);

        // TODO - fail silently anyway?
    }

    public List<Subscription> getAllSubscriptionOfCategory(Long categoryId) {
        return subscriptionRepository.findByAdCategory_Id(categoryId);
    }




}
