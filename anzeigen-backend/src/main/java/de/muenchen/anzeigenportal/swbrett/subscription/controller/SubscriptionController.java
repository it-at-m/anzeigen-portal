package de.muenchen.anzeigenportal.swbrett.subscription.controller;

import de.muenchen.anzeigenportal.swbrett.subscription.model.SubscriptionTO;
import de.muenchen.anzeigenportal.swbrett.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<SubscriptionTO> getUserSubscriptions() {
        return subscriptionService.getAllSubscriptionOfUser();
    }

    @GetMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public SubscriptionTO getUserSubscribedToCategory(@PathVariable final Long categoryId) {
        return subscriptionService.getUserSubscriptionForCategory(categoryId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubscriptionTO createUserSubscription(@RequestBody final Long categoryId) {
        return subscriptionService.createSubscription(categoryId);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserSubscription(@PathVariable final Long categoryId) {
        subscriptionService.deleteSubscription(categoryId);
    }

}
