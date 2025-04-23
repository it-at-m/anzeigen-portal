package de.muenchen.anzeigenportal.swbrett.subscription.controller;

import de.muenchen.anzeigenportal.swbrett.subscription.model.Subscription;
import de.muenchen.anzeigenportal.swbrett.subscription.model.SubscriptionTO;
import de.muenchen.anzeigenportal.swbrett.subscription.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SubscriptionTO> getUserSubscriptions() {
        return subscriptionService.getAllSubscriptionOfUser();
    }

    @GetMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public SubscriptionTO isUserSubscribedToCategory(@PathVariable long categoryId) {
        return subscriptionService.getUserSubscriptionForCategory(categoryId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubscriptionTO createUserSubscription(@RequestBody final long categoryId) {
        return subscriptionService.createSubscription(categoryId);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserSubscription(@PathVariable final long categoryId) {
        subscriptionService.deleteSubscription(categoryId);
    }


}
