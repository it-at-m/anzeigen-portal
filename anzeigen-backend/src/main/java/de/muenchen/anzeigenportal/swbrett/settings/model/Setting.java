package de.muenchen.anzeigenportal.swbrett.settings.model;

import de.muenchen.anzeigenportal.swbrett.files.model.SwbFile;
import jakarta.persistence.*;

/**
 * POJO für eine Einstellung im Adminbereich.
 */
@Entity
@Table(name = "t_setting", uniqueConstraints = @UniqueConstraint(columnNames = "setting_name"))
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "setting_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private SettingName settingName;

    @Column(name = "number_value", nullable = true)
    private Integer numberValue;

    @Column(name = "text_value", nullable = true)
    private String textValue;

    @Column(name = "flag_value", nullable = true)
    private Boolean flagValue;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_ad_id", nullable = true) // <= Typeo; sollte wohl "t_swb_file_id" heißen
    private SwbFile fileValue;

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

    public SwbFile getFileValue() {
        return fileValue;
    }

    public void setFileValue(SwbFile fileValue) {
        this.fileValue = fileValue;
    }
}
