package de.muenchen.anzeigenportal.swbrett.images.controller;

import java.io.IOException;
import java.util.Base64;

import de.muenchen.anzeigenportal.swbrett.images.model.SwbImageSanitize;
import de.muenchen.anzeigenportal.swbrett.images.model.SwbImageTO;
import de.muenchen.anzeigenportal.swbrett.images.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SwbImageTO getImage(@PathVariable("id") final long id) {
        return this.service.getImageTO(id);
    }

    /**
     * @see ImageService#sanitizeImage(byte[])
     */
    @PostMapping(value = "/sanitize", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String sanitizeImage(@RequestBody final SwbImageSanitize imageSanitize) {

        byte[] ba = Base64.getDecoder().decode(imageSanitize.imageBase64());

        try {
            ba = this.service.sanitizeImage(ba);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not sanitize image", e);
        }

        return Base64.getEncoder().encodeToString(ba);
    }
}
