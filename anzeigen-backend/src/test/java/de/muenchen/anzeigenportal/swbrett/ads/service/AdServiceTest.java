package de.muenchen.anzeigenportal.swbrett.ads.service;

import de.muenchen.anzeigenportal.swbrett.settings.model.SettingName;
import de.muenchen.anzeigenportal.swbrett.settings.model.SettingTO;
import de.muenchen.anzeigenportal.swbrett.settings.service.SettingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdServiceTest {


    @Mock
    private SettingService settingService;

    @InjectMocks
    private AdService adService;

    @BeforeEach
    void setUp() {
        // Mocking the getSetting method
        SettingTO setting = new SettingTO();
        setting.setTextValue("domain1.com,domain2.com,domain.com");
        when(settingService.getSetting(SettingName.DISALLOWED_EMAIL_DOMAINS)).thenReturn(setting);
    }

    @Test
    void testEmailDisallowed() {
        // Test cases where the email should be disallowed
        assertTrue(adService.isEMailDomainDisallowed("example@domain1.com"));
        assertTrue(adService.isEMailDomainDisallowed("example@domain2.com"));
        assertTrue(adService.isEMailDomainDisallowed("example@domain.com"));

        // Test cases where the email should be allowed
        assertFalse(adService.isEMailDomainDisallowed("example@otherdomain.com"));
        assertFalse(adService.isEMailDomainDisallowed("example@anotherdomain.com"));
    }
}
