package de.muenchen.anzeigenportal.swbrett.subscription.model;

import de.muenchen.anzeigenportal.domain.BaseEntity;
import de.muenchen.anzeigenportal.swbrett.ads.model.AdCategory;
import de.muenchen.anzeigenportal.swbrett.users.model.SwbUser;
import de.muenchen.anzeigenportal.swbrett.users.model.SwbUserTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.UUID;

/*
 * TransferObject of Subscriptions
 */
public record SubscriptionTO(
         UUID id,
         LocalDate subscriptionDate,
         SwbUserTO swbUser,
         AdCategory adCategory
) {}
