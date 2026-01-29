package de.muenchen.anzeigenportal.swbrett.job.service;

import de.muenchen.anzeigenportal.swbrett.ads.model.Ad;
import de.muenchen.anzeigenportal.swbrett.ads.repository.AdRepository;
import de.muenchen.anzeigenportal.swbrett.settings.model.SettingName;
import de.muenchen.anzeigenportal.swbrett.settings.service.SettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScheduledJobService {

    @Autowired
    private SettingService settingService;

    @Autowired
    private AdRepository adRepository;

    @Scheduled(cron = "${scheduled.deactivateAds}")
    public void deactivateExpiredAds() {

        final LocalDate limit;

        limit = LocalDate.now();

        log.debug("Deactivate active ads that expired before {}", limit);

        final List<Ad> expiredAds = adRepository.findByExpiryDateBeforeAndActive(limit, true);

        expiredAds.forEach(ad -> {
            log.debug("Deactivating ad {} (expired {})", ad.getId(), ad.getExpiryDate());
            ad.setActive(false);
            adRepository.save(ad);
        });
    }

    @Scheduled(cron = "${scheduled.deleteDeactivatedAds}")
    public void deleteDeactivatedAdsAfterDateRange() {

        final LocalDate limit;
        final Integer maxArchiveDateRange = settingService.getSetting(SettingName.MAX_ARCHIVE_DATE_RANGE).getNumberValue();
        limit = LocalDate.now().minusWeeks(maxArchiveDateRange);

        log.debug("Delete inactive ads that expired before {}", limit);

        final List<Ad> deactivatedAdsAfterDateRange = adRepository.findByExpiryDateBeforeAndActive(limit, false);

        deactivatedAdsAfterDateRange.stream().forEach(ad -> {
            log.debug("Deleting ad {} (expired {})", ad.getId(), ad.getExpiryDate());
            adRepository.delete(ad);
        });
    }
}
