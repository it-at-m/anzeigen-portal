package de.muenchen.anzeigenportal.swbrett.users.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * POJO für Swbrett SwbUser.
 */
@Setter
@Getter
@Entity
@Table(name = "t_swb_user", uniqueConstraints = @UniqueConstraint(columnNames = "lhm_object_id"))
@SuppressWarnings("PMD.DataClass")
public class SwbUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Identifiziert den Benutzer eindeutig in KEYCLOAK.
     */
    @Column(name = "lhm_object_id", nullable = false)
    private String lhmObjectId;

    /**
     * Menschenlesbarer, informativer, nicht zwingend eindeutiger Name des Benutzers.
     */
    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "email", nullable = false)
    private String email;

}
