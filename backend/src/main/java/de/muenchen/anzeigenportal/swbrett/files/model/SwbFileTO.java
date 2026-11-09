package de.muenchen.anzeigenportal.swbrett.files.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Transferobject für Dateien.
 */
@Setter
@Getter
public class SwbFileTO {

    private long id;

    private String name;

    private int size;

    private String fileBase64;

}
