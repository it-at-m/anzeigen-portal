package de.muenchen.anzeigenportal.swbrett.ads.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "t_ad_category")
@SuppressWarnings("PMD.DataClass")
public class AdCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Es sollte immer genau eine Standard-Kategorie existieren.
     * Wird eine Kategorie gelöscht, so werden alle Anzeigen der gelöschten Kategorie in die
     * Standard-Kategorie verschoben.
     */
    @Column(name = "standard", nullable = false)
    private boolean standard = false;

}
