package de.muenchen.anzeigenportal.swbrett.settings.model;

import de.muenchen.anzeigenportal.swbrett.files.model.SwbFileTO;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.Setter;

/**
 * Transferobject für Einstellungen
 */
@Setter
@Getter
@SuppressFBWarnings("EI_EXPOSE_REP")
public class SettingTO {

    private long id;

    private SettingName settingName;

    private Integer numberValue;

    private String textValue;

    private Boolean flagValue;

    private SwbFileTO fileValue;

}
