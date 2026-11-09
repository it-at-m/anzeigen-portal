package de.muenchen.anzeigenportal.swbrett.settings.service;

import de.muenchen.anzeigenportal.swbrett.settings.model.Setting;
import de.muenchen.anzeigenportal.swbrett.settings.model.SettingName;
import de.muenchen.anzeigenportal.swbrett.settings.model.SettingTO;
import de.muenchen.anzeigenportal.swbrett.settings.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SettingService {

    @Autowired
    private SettingRepository repository;

    @Autowired
    private SettingMapper mapper;

    @Transactional
    public List<SettingTO> getAllSettings() {
        final List<Setting> all = repository.findAll();
        return all.stream().map(mapper::toSettingTO).collect(Collectors.toList());
    }

    @Transactional
    public SettingTO getSetting(final SettingName name) {
        final Setting setting = repository.findBySettingName(name);
        return mapper.toSettingTO(setting);
    }

    public void createSetting(final SettingTO settingTO) {
        final Setting setting = mapper.toSetting(settingTO);
        final Setting savedSetting = repository.save(setting);
        mapper.toSettingTO(savedSetting);
    }

    @PreAuthorize("hasAuthority(T(de.muenchen.anzeigenportal.security.AuthoritiesEnum).fachadmin.name())")
    public List<SettingTO> saveSettings(final List<SettingTO> settingTOs) {
        final List<Setting> settings = settingTOs.stream().map(mapper::toSetting).collect(Collectors.toList());
        final List<Setting> savedSettings = repository.saveAll(settings);
        return savedSettings.stream().map(mapper::toSettingTO).collect(Collectors.toList());
    }
}
