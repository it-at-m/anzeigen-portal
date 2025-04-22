package de.muenchen.anzeigenportal.swbrett.subscription.model;

import de.muenchen.anzeigenportal.domain.BaseEntity;
import de.muenchen.anzeigenportal.swbrett.ads.model.AdCategory;
import de.muenchen.anzeigenportal.swbrett.users.model.SwbUser;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class Subscription extends BaseEntity {

    @Column(name = "subscriptionDate")
    private Date subscriptionDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_swb_user_id", nullable = false)
    private SwbUser swbUser;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_ad_category_id", nullable = false)
    private AdCategory adCategory;
}
