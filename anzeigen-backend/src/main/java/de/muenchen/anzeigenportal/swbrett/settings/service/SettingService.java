package de.muenchen.anzeigenportal.swbrett.settings.service;

import de.muenchen.anzeigenportal.swbrett.settings.model.Setting;
import de.muenchen.anzeigenportal.swbrett.settings.model.SettingName;
import de.muenchen.anzeigenportal.swbrett.settings.model.SettingTO;
import de.muenchen.anzeigenportal.swbrett.settings.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SettingService {

    @Autowired
    private SettingRepository repository;

    @Autowired
    private SettingMapper mapper;

    public List<SettingTO> getAllSettings() {
        List<Setting> all = repository.findAll();
        return all.stream().map(mapper::toSettingTO).collect(Collectors.toList());
    }

    public SettingTO getSetting(SettingName name) {
        Setting setting = repository.findBySettingName(name);
        return mapper.toSettingTO(setting);
    }

    public SettingTO createSetting(SettingTO settingTO) {
        Setting setting = mapper.toSetting(settingTO);
        Setting savedSetting = repository.save(setting);
        return mapper.toSettingTO(savedSetting);
    }

    @PreAuthorize("hasAuthority(T(de.muenchen.intranet.sbrett.security.AuthoritiesEnum).BACKEND_WRITE_THEENTITY.name())")
    public List<SettingTO> saveSettings(List<SettingTO> settingTOs) {
        List<Setting> settings = settingTOs.stream().map(mapper::toSetting).collect(Collectors.toList());
        List<Setting> savedSettings = repository.saveAll(settings);
        return savedSettings.stream().map(mapper::toSettingTO).collect(Collectors.toList());
    }
}
