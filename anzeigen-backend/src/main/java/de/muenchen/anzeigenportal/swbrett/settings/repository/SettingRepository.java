package de.muenchen.anzeigenportal.swbrett.settings.repository;

import de.muenchen.intranet.sbrett.swbrett.settings.model.Setting;
import de.muenchen.intranet.sbrett.swbrett.settings.model.SettingName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long> {

    Setting findBySettingName(SettingName settingName);
}
