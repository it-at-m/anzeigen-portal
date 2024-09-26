package de.muenchen.anzeigenportal.swbrett.ads.model;



import de.muenchen.anzeigenportal.swbrett.files.model.SwbFileTO;
import de.muenchen.anzeigenportal.swbrett.images.model.SwbImageTO;
import de.muenchen.anzeigenportal.swbrett.users.model.SwbUserTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Transferobject für Anzeigen (Advertisement)
 */
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

    private String imagePreviewBase64;

    private SwbImageTO adImg;

    private List<SwbFileTO> adFiles = new ArrayList<>();

    private int views;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SwbUserTO getSwbUser() {
        return swbUser;
    }

    public void setSwbUser(SwbUserTO swbUser) {
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

    public String getImagePreviewBase64() {
        return imagePreviewBase64;
    }

    public void setImagePreviewBase64(String imagePreviewBase64) {
        this.imagePreviewBase64 = imagePreviewBase64;
    }

    public SwbImageTO getAdImg() {
        return this.adImg;
    }

    public void setAdImg(SwbImageTO adImg) {
        this.adImg = adImg;
    }

    public List<SwbFileTO> getAdFiles() {
        return this.adFiles;
    }

    public void setAdFiles(List<SwbFileTO> adFiles) {
        this.adFiles = adFiles;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
