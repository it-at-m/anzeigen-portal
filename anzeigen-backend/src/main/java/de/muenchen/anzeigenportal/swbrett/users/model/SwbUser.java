package de.muenchen.anzeigenportal.swbrett.users.model;

import jakarta.persistence.*;

/**
 * POJO für Swbrett SwbUser.
 */
@Entity
@Table(name = "t_swb_user", uniqueConstraints=@UniqueConstraint(columnNames="lhm_object_id"))
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLhmObjectId() {
        return this.lhmObjectId;
    }

    public void setLhmObjectId(String lhmObjectId) {
        this.lhmObjectId = lhmObjectId;
    }

    public String getDisplayName() {
    	return this.displayName;
    }

    public void setDisplayName(String displayName) {
    	this.displayName = displayName;
    }
}
