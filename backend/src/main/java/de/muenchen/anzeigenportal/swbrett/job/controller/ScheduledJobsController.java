package de.muenchen.anzeigenportal.swbrett.job.controller;

import de.muenchen.anzeigenportal.swbrett.job.service.ScheduledJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScheduledJobsController {

    private final ScheduledJobService scheduledJobService;

    @PreAuthorize("hasAuthority(T(de.muenchen.anzeigenportal.security.AuthoritiesEnum).fachadmin.name())")
    @PostMapping("/deactivateAds")
    @ResponseStatus(HttpStatus.OK)
    public void runDeactiveAds() {
        scheduledJobService.deactivateExpiredAds();
    }

    @PreAuthorize("hasAuthority(T(de.muenchen.anzeigenportal.security.AuthoritiesEnum).fachadmin.name())")
    @PostMapping("/deleteAds")
    @ResponseStatus(HttpStatus.OK)
    public void runDeleteAds() {
        scheduledJobService.deleteDeactivatedAdsAfterDateRange();
    }

}
