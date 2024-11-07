package de.muenchen.anzeigenportal.swbrett.ads.controller;

import de.muenchen.anzeigenportal.swbrett.ads.model.AdTO;
import de.muenchen.anzeigenportal.swbrett.ads.model.AdType;
import de.muenchen.anzeigenportal.swbrett.ads.service.AdService;
import de.muenchen.anzeigenportal.swbrett.users.model.SwbUserTO;
import de.muenchen.anzeigenportal.swbrett.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(value = { "/ads", "/swbreads" }) // swbreads as alias, because ad blockers block /ads (Attention! Stupid pun)
@SuppressWarnings("PMD.PreserveStackTrace")
public class AdController {

    @Autowired
    private AdService service;

    @Autowired
    private UserService userService;

    /**
     * Gibt eine nach Suchkriterien gefilterte, sortiere Datenliste zurück mit zusätzlichen Pagination
     * Informationen.
     * Beispieladresse:
     * /api/ads?searchTerm=wohnung&type=OFFER&cagetoryId=1&sortBy=price&order=asc&page=0
     *
     * RequestParam "adId" wurde eingeführt, damit man mit einer Url direkt auf eine bestimmte Anzeige
     * kommen kann.
     * Die Methode #getAd fetched auch eine einzelne Anzeige, jedoch ohne Page Wrapper.
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<AdTO> getAds(@RequestParam(value = "isActive", required = true) final boolean isActive,
            @RequestParam(value = "userId", required = false) final String userId,
            @RequestParam(value = "searchTerm", required = false) final String searchTerm,
            @RequestParam(value = "categoryId", required = false) final Long categoryId,
            @RequestParam(value = "type", required = false) final AdType type,
            @RequestParam(value = "sortBy", required = false) final String sortBy,
            @RequestParam(value = "order", required = false) final String order,
            @RequestParam(value = "page", required = false) final Integer page,
            @RequestParam(value = "adId", required = false) final Long adId) {

        return service.findAds(userId, searchTerm, categoryId, type, sortBy, order, page, adId, isActive);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AdTO getAd(@PathVariable("id") final long id) {
        return service.getAd(id);
    }

    @PutMapping("/incrementView/{id}")
    public void incrementView(@PathVariable("id") final long id) {
        service.incrementView(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public AdTO createAd(@RequestBody final AdTO adTO) {

        // create user if doesn't exist
        if (adTO.getSwbUser().getId() == null) {
            final SwbUserTO newUser = userService.createUser(adTO.getSwbUser());
            adTO.setSwbUser(newUser);
        }

        try {
            return service.createAd(adTO);
        } catch (IOException e) {
            log.debug(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Fehler bei der Komprimierung des Originalfotos.", e);
        } catch (ValidationException e) {
            log.debug(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AdTO updateAd(@PathVariable("id") final long id, @RequestBody final AdTO adTO, final HttpServletRequest request) {
        try {
            return service.updateAd(id, adTO, request);
        } catch (IOException e) {
            log.debug(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Fehler bei der Komprimierung des Originalfotos.", e);
        } catch (ValidationException e) {
            log.debug(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/deactivate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deactivateAd(@PathVariable("id") final long id, final HttpServletRequest request) {
        service.deactivateAd(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAd(@PathVariable("id") final long id, final HttpServletRequest request) {
        service.deleteAd(id, request);
    }
}
