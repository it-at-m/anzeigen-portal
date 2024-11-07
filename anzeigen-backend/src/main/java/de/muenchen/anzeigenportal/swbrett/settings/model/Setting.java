package de.muenchen.anzeigenportal.swbrett.settings.model;

import de.muenchen.anzeigenportal.swbrett.files.model.SwbFile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * POJO für eine Einstellung im Adminbereich.
 */
@Setter
@Getter
@Entity
@Table(name = "t_setting", uniqueConstraints = @UniqueConstraint(columnNames = "setting_name"))
@SuppressWarnings("PMD.DataClass")
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

}
