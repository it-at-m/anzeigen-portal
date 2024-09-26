package de.muenchen.anzeigenportal.swbrett.ads.model;

import jakarta.persistence.*;

@Entity
@Table(name = "t_ad_category")
public class AdCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Es sollte immer genau eine Standard-Kategorie existieren.
     * Wird eine Kategorie gelöscht, so werden alle Anzeigen der gelöschten Kategorie in die Standard-Kategorie verschoben.
     */
    @Column(name = "standard", nullable = false)
    private boolean standard = false;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStandard() {
        return standard;
    }

    public void setStandard(boolean standard) {
        this.standard = standard;
    }
}
