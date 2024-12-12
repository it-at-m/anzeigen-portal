package de.muenchen.anzeigenportal.swbrett.users.controller;

import de.muenchen.anzeigenportal.swbrett.users.model.SwbUserTO;
import de.muenchen.anzeigenportal.swbrett.users.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/admin/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public SwbUserTO getUser(@PathVariable("userId") final long userId) {
        return service.getUser(userId);
    }

    /**
     * Put Method to encrypt the email with https.
     *
     * @param lhmObjectId unique description in keycloak for every user
     * @return SwbUserTO with id, if user found. id = null if no user found
     */
    @PutMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    public SwbUserTO findUser(@RequestBody final String lhmObjectId) {
        String sanitizedId = lhmObjectId.replaceAll("[\\r\\n\"]", "");
        final Optional<SwbUserTO> userTO = service.findUser(sanitizedId);
        log.debug("CONTROLLER | findUser was successful: {}", userTO.isPresent());
        return userTO.orElseGet(SwbUserTO::new);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public SwbUserTO createUser(@RequestBody final SwbUserTO userTO) {
        return service.createUser(userTO);
    }
}
