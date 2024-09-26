package de.muenchen.anzeigenportal.swbrett.settings.model;


import de.muenchen.anzeigenportal.swbrett.files.model.SwbFileTO;

/**
 * Transferobject für Einstellungen
 */
public class SettingTO {

    private long id;

    private SettingName settingName;

    private Integer numberValue;

    private String textValue;

    private Boolean flagValue;

    private SwbFileTO fileValue;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SettingName getSettingName() {
        return settingName;
    }

    public void setSettingName(SettingName settingName) {
        this.settingName = settingName;
    }

    public Integer getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(Integer numberValue) {
        this.numberValue = numberValue;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public Boolean getFlagValue() {
        return flagValue;
    }

    public void setFlagValue(Boolean flagValue) {
        this.flagValue = flagValue;
    }

    public SwbFileTO getFileValue() {
        return fileValue;
    }

    public void setFileValue(SwbFileTO fileValue) {
        this.fileValue = fileValue;
    }
}
