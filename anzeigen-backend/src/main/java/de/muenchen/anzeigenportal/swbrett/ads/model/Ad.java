package de.muenchen.anzeigenportal.swbrett.ads.model;



import de.muenchen.anzeigenportal.swbrett.files.model.SwbFile;
import de.muenchen.anzeigenportal.swbrett.images.model.SwbImage;
import de.muenchen.anzeigenportal.swbrett.users.model.SwbUser;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * POJO für Anzeigen (Advertisement)
 */
@Entity
@Table(name = "t_ad")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_swb_user_id", nullable = false)
    private SwbUser swbUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_ad_category_id", nullable = false)
    private AdCategory adCategory;

    @Column(name = "ad_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AdType adType;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @Column(name = "title", nullable = false, length = 120)
    private String title;

    /**
     *  1000 Character für SwbUser Beschreibung und zusätzliche Zeichen für HTML, die
     *  vom Rich Text Editor erzeugt werden.
     */
    @Column(name = "description", nullable = false, length = 2000)
    private String description;

    @Column(name = "price", nullable = true)
    private Double price;

    @Column(name = "phone", nullable = true)
    private String phone;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "link", nullable = true, length = 512)
    private String link;

    @Column(name = "creation_date_time", nullable = false)
    private LocalDateTime creationDateTime;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "image_preview", nullable = true)
    @Lob
    private byte[] imagePreview;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_ad_id")
    private SwbImage imageOriginal; //TODO: was ist hier gewollt?

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="fk_ad_id")
    private List<SwbFile> files = new ArrayList<>(); //TODO: was ist hier gewollt?

    @Column(name = "views", nullable = false)
    private int views = 0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SwbUser getSwbUser() {
        return swbUser;
    }

    public void setSwbUser(SwbUser swbUser) {
        this.swbUser = swbUser;
    }

    public AdCategory getAdCategory() {
        return adCategory;
    }

    public void setAdCategory(AdCategory adCategory) {
        this.adCategory = adCategory;
    }

    public AdType getAdType() {
        return adType;
    }

    public void setAdType(AdType adType) {
        this.adType = adType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public byte[] getImagePreview() {
        return imagePreview;
    }

    public void setImagePreview(byte[] imagePreview) {
        this.imagePreview = imagePreview;
    }

    public SwbImage getImageOriginal() {
        return imageOriginal;
    }

    public void setImageOriginal(SwbImage imageOriginal) {
        this.imageOriginal = imageOriginal;
    }

    public List<SwbFile> getFiles() {
        return files;
    }

    public void setFiles(List<SwbFile> files) {
        this.files = files;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
