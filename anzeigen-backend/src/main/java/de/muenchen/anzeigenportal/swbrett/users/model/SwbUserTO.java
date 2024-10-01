package de.muenchen.anzeigenportal.swbrett.users.model;

/**
 * Transferobject für Swb User
 */
public class SwbUserTO {

    private Long id;

    /**
     * Identifiziert den Benutzer eindeutig.
     */
    private String lhmObjectId;

    /**
     * Menschenlesbarer, informativer, nicht zwingend eindeutiger Name des Benutzers.
     * Diese Info darf wegen Anonymisierung nur für Admins einsehbar sein!
     */
    private String displayName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLhmObjectId() {
        return this.lhmObjectId;
    }

    public void setLhmObjectId(String lhmObjectId) {
        this.lhmObjectId = lhmObjectId;
    }
}
