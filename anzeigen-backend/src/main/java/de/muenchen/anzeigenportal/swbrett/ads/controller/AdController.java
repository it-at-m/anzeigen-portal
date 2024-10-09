package de.muenchen.anzeigenportal.swbrett.ads.controller;

import de.muenchen.anzeigenportal.swbrett.ads.model.AdTO;
import de.muenchen.anzeigenportal.swbrett.ads.model.AdType;
import de.muenchen.anzeigenportal.swbrett.ads.service.AdService;
import de.muenchen.anzeigenportal.swbrett.users.model.SwbUserTO;
import de.muenchen.anzeigenportal.swbrett.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping(value = { "/ads", "/swbreads" }) // swbreads as alias, because ad blockers block /ads (Attention! Stupid pun)
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
    public Page<AdTO> getAds(@RequestParam(value = "isActive", required = true) boolean isActive,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "searchTerm", required = false) String searchTerm,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "type", required = false) AdType type,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "adId", required = false) Long adId) {

        return service.findAds(userId, searchTerm, categoryId, type, sortBy, order, page, adId, isActive);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AdTO getAd(@PathVariable("id") long id) {
        return service.getAd(id);
    }

    @PutMapping("/incrementView/{id}")
    public void incrementView(@PathVariable("id") long id) {
        service.incrementView(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public AdTO createAd(@RequestBody AdTO adTO) {

        // create user if doesn't exist
        if (adTO.getSwbUser().getId() == null) {
            SwbUserTO newUser = userService.createUser(adTO.getSwbUser());
            adTO.setSwbUser(newUser);
        }

        try {
            return service.createAd(adTO);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Fehler bei der Komprimierung des Originalfotos.", e);
        } catch (ValidationException e) {
            System.out.println(e); // TODO Logger
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AdTO updateAd(@PathVariable("id") long id, @RequestBody AdTO adTO, HttpServletRequest request) {
        try {
            return service.updateAd(id, adTO, request);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Fehler bei der Komprimierung des Originalfotos.", e);
        } catch (ValidationException e) {
            System.out.println(e); // TODO Logger
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/deactivate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deactivateAd(@PathVariable("id") long id, HttpServletRequest request) {
        service.deactivateAd(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAd(@PathVariable("id") long id, HttpServletRequest request) {
        service.deleteAd(id, request);
    }
}
