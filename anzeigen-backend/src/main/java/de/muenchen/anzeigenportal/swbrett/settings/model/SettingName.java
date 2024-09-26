package de.muenchen.anzeigenportal.swbrett.settings.model;

public enum SettingName {

	/**
     * Tagesmeldung; wird oben auf der Hauptseite angezeigt. Kann HTML-Markup enthalten. (TEXT)
	 */
	MOTD,

    /**
     * Maximale Dateigröße des Fotos in MB (NUMBER)
     */
    MAX_SWB_IMAGE_SIZE,

    /**
     * Maximale Dateigröße einer Datei in MB (NUMBER)
     */
    MAX_SWB_FILE_SIZE,

    /**
     * Maximale Anzahl der Dateianhänge (NUMBER)
     */
    MAX_SWB_FILES_LENGTH,

    /**
     * Maximale Laufzeit in Wochen (NUMBER)
     */
    MAX_EXPIRY_DATE_RANGE,

    /**
     * AGB als PDF-Datei (FILE)
     */
    AGB_FILE,

    /**
     * Datenschutzhinweise als PDF-Datei (FILE)
     */
    DATENSCHUTZHINWEISE_FILE,

    /**
     * Maximale Anzahl der Anzeigen pro Seite (NUMBER)
     */
    MAX_PAGE_SIZE,

    /**
     * Standard Sortierung (TEXT, z.B. "creationDateTime")
     */
    DEFAULT_SORTING,

    /**
     * Standard Ordnung (TEXT, z.B. "desc")
     */
    DEFAULT_ORDERING,

    /**
     * Maximale Archivierungszeit einer Anzeige in Wochen (NUMBER)
     */
    MAX_ARCHIVE_DATE_RANGE,
}
