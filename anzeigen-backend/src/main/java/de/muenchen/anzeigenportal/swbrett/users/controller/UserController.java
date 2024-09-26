package de.muenchen.anzeigenportal.swbrett.users.controller;

import de.muenchen.anzeigenportal.swbrett.users.model.SwbUserTO;
import de.muenchen.anzeigenportal.swbrett.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/admin/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public SwbUserTO getUser(@PathVariable(value = "userId") long userId) {
        return service.getUser(userId);
    }

    /**
     * Put Method to encrypt the email with https.
     * @param email
     * @return SwbUserTO with id, if user found. id = null if no user found
     */
    @PutMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    public SwbUserTO findUser(@RequestBody String email) {
        Optional<SwbUserTO> userTO = service.findUser(email);
        if (userTO.isPresent()) {
            return userTO.get();
        } else {
            return new SwbUserTO();
        }
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    public SwbUserTO createUser(@RequestBody SwbUserTO userTO) {
        return service.createUser(userTO);
    }
}
