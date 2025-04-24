package de.muenchen.anzeigenportal.swbrett.subscription.model;

import de.muenchen.anzeigenportal.domain.BaseEntity;
import de.muenchen.anzeigenportal.swbrett.ads.model.AdCategory;
import de.muenchen.anzeigenportal.swbrett.users.model.SwbUser;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "t_subscription")
@SuppressWarnings({ "PMD.NonSerializableClass" })
public class Subscription extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -8087031435105795319L;

    @Column(name = "subscriptionDate")
    private LocalDate subscriptionDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_swb_user_id", nullable = false)
    private SwbUser swbUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_ad_category_id", nullable = false)
    private AdCategory adCategory;
}
