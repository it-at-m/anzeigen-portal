package de.muenchen.anzeigenportal.swbrett.settings.controller;

import de.muenchen.anzeigenportal.swbrett.settings.model.SettingName;
import de.muenchen.anzeigenportal.swbrett.settings.model.SettingTO;
import de.muenchen.anzeigenportal.swbrett.settings.service.SettingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/settings")
public class SettingController {

    @Autowired
    private SettingService service;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<SettingTO> getSettings() {
        return service.getAllSettings();
    }

    @GetMapping("/{settingName}")
    @ResponseStatus(HttpStatus.OK)
    public SettingTO getSetting(@PathVariable(value = "settingName") SettingName settingName) {
        return service.getSetting(settingName);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<SettingTO> updateSettings(@RequestBody List<SettingTO> settings, HttpServletRequest request) {
        return service.saveSettings(settings);
    }
}
