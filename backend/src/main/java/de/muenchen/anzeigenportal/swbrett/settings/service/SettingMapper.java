package de.muenchen.anzeigenportal.swbrett.settings.service;

import de.muenchen.anzeigenportal.swbrett.files.service.FileMapper;
import de.muenchen.anzeigenportal.swbrett.settings.model.Setting;
import de.muenchen.anzeigenportal.swbrett.settings.model.SettingTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettingMapper {

    @Autowired
    private FileMapper fileMapper;

    public SettingTO toSettingTO(final Setting setting) {
        final SettingTO settingTO = new SettingTO();
        settingTO.setId(setting.getId());
        settingTO.setSettingName(setting.getSettingName());

        if (setting.getNumberValue() != null) {
            settingTO.setNumberValue(setting.getNumberValue());
        }
        if (setting.getTextValue() != null) {
            settingTO.setTextValue(setting.getTextValue());
        }
        if (setting.getFlagValue() != null) {
            settingTO.setFlagValue(setting.getFlagValue());
        }
        if (setting.getFileValue() != null) {
            settingTO.setFileValue(fileMapper.toSwbFileTO(setting.getFileValue()));
        }

        return settingTO;
    }

    public Setting toSetting(final SettingTO settingTO) {
        final Setting setting = new Setting();
        setting.setId(settingTO.getId());
        setting.setSettingName(settingTO.getSettingName());

        if (settingTO.getNumberValue() != null) {
            setting.setNumberValue(settingTO.getNumberValue());
        }
        if (settingTO.getTextValue() != null) {
            setting.setTextValue(settingTO.getTextValue());
        }
        if (settingTO.getFlagValue() != null) {
            setting.setFlagValue(settingTO.getFlagValue());
        }
        if (settingTO.getFileValue() != null) {
            setting.setFileValue(fileMapper.toSwbFile(settingTO.getFileValue()));
        }

        return setting;
    }
}
