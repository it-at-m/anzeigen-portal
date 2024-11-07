package de.muenchen.anzeigenportal.swbrett.ads.model;

import de.muenchen.anzeigenportal.swbrett.files.model.SwbFile;
import de.muenchen.anzeigenportal.swbrett.images.model.SwbImage;
import de.muenchen.anzeigenportal.swbrett.users.model.SwbUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * POJO für Anzeigen (Advertisement)
 */
@Getter
@Entity
@Table(name = "t_ad")
@SuppressWarnings("PMD.DataClass")
public class Ad {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_swb_user_id", nullable = false)
    private SwbUser swbUser;

    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_ad_category_id", nullable = false)
    private AdCategory adCategory;

    @Setter
    @Column(name = "ad_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AdType adType;

    @Setter
    @Column(name = "active", nullable = false)
    private boolean active = true;

    @Setter
    @Column(name = "title", nullable = false, length = 120)
    private String title;

    /**
     * 1000 Character für SwbUser Beschreibung und zusätzliche Zeichen für HTML, die
     * vom Rich Text Editor erzeugt werden.
     */
    @Setter
    @Column(name = "description", nullable = false, length = 2000)
    private String description;

    @Setter
    @Column(name = "price", nullable = true)
    private Double price;

    @Setter
    @Column(name = "phone", nullable = true)
    private String phone;

    @Setter
    @Column(name = "email", nullable = true)
    private String email;

    @Setter
    @Column(name = "link", nullable = true, length = 512)
    private String link;

    @Setter
    @Column(name = "creation_date_time", nullable = false)
    private LocalDateTime creationDateTime;

    @Setter
    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "image_preview", nullable = true)
    @Lob
    private byte[] imagePreview;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_ad_id")
    private SwbImage imageOriginal; //TODO: was ist hier gewollt?

    @Setter
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_ad_id")
    private List<SwbFile> files = new ArrayList<>(); //TODO: was ist hier gewollt?

    @Setter
    @Column(name = "views", nullable = false)
    private int views = 0;

    public void setImagePreview(byte[] imagePreview) {
        this.imagePreview = Arrays.copyOf(imagePreview, imagePreview.length);
    }

}
