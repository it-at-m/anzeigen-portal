package de.muenchen.anzeigenportal.swbrett.subscription.service;

import de.muenchen.anzeigenportal.swbrett.subscription.model.Subscription;
import de.muenchen.anzeigenportal.swbrett.subscription.model.SubscriptionTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubscriptionMapper {

    SubscriptionMapper INSTANCE = Mappers.getMapper(SubscriptionMapper.class);

    SubscriptionTO toDTO(Subscription subscription);

    Subscription toEntity(SubscriptionTO subscriptionTO);
}
