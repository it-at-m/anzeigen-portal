package de.muenchen.anzeigenportal.swbrett.settings.model;

import de.muenchen.anzeigenportal.swbrett.files.model.SwbFileTO;
import lombok.Getter;
import lombok.Setter;

/**
 * Transferobject für Einstellungen
 */
@Setter
@Getter
public class SettingTO {

    private long id;

    private SettingName settingName;

    private Integer numberValue;

    private String textValue;

    private Boolean flagValue;

    private SwbFileTO fileValue;

}
