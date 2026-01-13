package de.muenchen.anzeigenportal.swbrett.ads.model;

import de.muenchen.anzeigenportal.swbrett.files.model.SwbFileTO;
import de.muenchen.anzeigenportal.swbrett.images.model.SwbImageTO;
import de.muenchen.anzeigenportal.swbrett.users.model.SwbUserTO;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Transferobject für Anzeigen (Advertisement)
 */
@Setter
@Getter
@SuppressWarnings({ "PMD.TooManyFields", "PMD.ShortClassName" })
@SuppressFBWarnings("EI_EXPOSE_REP")
public class AdTO {

    private long id;

    private SwbUserTO swbUser;

    private AdCategory adCategory;

    private AdType adType;

    private boolean active = true;

    private String title;

    private String description;

    private Double price;

    private String phone;

    private String email;

    private String link;

    private LocalDateTime creationDateTime;

    private LocalDate expiryDate;

    private LocalDate rentalDate;

    private String imagePreviewBase64;

    private SwbImageTO adImg;

    private List<SwbFileTO> adFiles = new ArrayList<>();

    private int views;

    private AdCondition condition;

    private Address address;

}
