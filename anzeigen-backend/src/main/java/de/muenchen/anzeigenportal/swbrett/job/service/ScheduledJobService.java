package de.muenchen.anzeigenportal.swbrett.job.service;

import de.muenchen.anzeigenportal.swbrett.ads.model.Ad;
import de.muenchen.anzeigenportal.swbrett.ads.repository.AdRepository;
import de.muenchen.anzeigenportal.swbrett.settings.model.SettingName;
import de.muenchen.anzeigenportal.swbrett.settings.service.SettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduledJobService {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduledJobService.class);

    private final static String EVERY_DAY_1_AM = "0 0 1 * * *"; // second minute hour day-of-month month day-of-week
    private final static String EVERY_DAY_2_AM = "0 0 2 * * *";

    @Autowired
    private SettingService settingService;

    @Autowired
    private AdRepository adRepository;

    // Nur zum Testen: Alle 20 Sekunden (0:00, 0:20, 0:40):
    //@Scheduled(cron = "0/20 * * * * *")

    @Scheduled(cron = EVERY_DAY_1_AM)
    public void deactivateExpiredAds() {

        final LocalDate limit;

        if (false) {

            // Nur zum Testen: *Alle* aktiven Anzeigen deaktivieren.
            limit = LocalDate.of(2099, 1, 1);
        } else {
            limit = LocalDate.now();
        }

        LOG.debug("Deactivate active ads that expired before " + limit);

        final List<Ad> expiredAds = adRepository.findByExpiryDateBeforeAndActive(limit, true);

        expiredAds.stream().forEach(ad -> {
            LOG.debug("Deactivating ad " + ad.getId() + " (expired " + ad.getExpiryDate() + ")");
            ad.setActive(false);
            adRepository.save(ad);
        });
    }

    // Nur zum Testen: Alle 20 Sekunden (0:10, 0:30, 0:50):
    //@Scheduled(cron = "10/20 * * * * *")

    @Scheduled(cron = EVERY_DAY_2_AM)
    public void deleteDeactivatedAdsAfterDateRange() {

        final LocalDate limit;
        if (false) {

            // Nur zum Testen: *Alle* inaktiven Anzeigen löschen.
            limit = LocalDate.of(2099, 1, 1);
        } else {
            final Integer maxArchiveDateRange = settingService.getSetting(SettingName.MAX_ARCHIVE_DATE_RANGE).getNumberValue();
            limit = LocalDate.now().minusWeeks(maxArchiveDateRange);
        }

        LOG.debug("Delete inactive ads that expired before " + limit);

        final List<Ad> deactivatedAdsAfterDateRange = adRepository.findByExpiryDateBeforeAndActive(limit, false);

        deactivatedAdsAfterDateRange.stream().forEach(ad -> {
            LOG.debug("Deleting ad " + ad.getId() + " (expired " + ad.getExpiryDate() + ")");
            adRepository.delete(ad);
        });
    }
}
