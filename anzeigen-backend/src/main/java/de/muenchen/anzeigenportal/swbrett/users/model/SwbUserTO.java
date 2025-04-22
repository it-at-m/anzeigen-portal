package de.muenchen.anzeigenportal.swbrett.users.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Transferobject für Swb User
 */
@Setter
@Getter
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


    private String email;

}
