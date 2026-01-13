package de.muenchen.anzeigenportal.swbrett.ads.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Embeddable
public class Address {

    @Setter
    @Column(name = "street")
    private String street;

    @Setter
    @Column(name = "postal_code")
    private String postalCode;

}
