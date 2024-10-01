package de.muenchen.anzeigenportal.swbrett.files.controller;

import de.muenchen.anzeigenportal.swbrett.files.model.SwbFileTO;
import de.muenchen.anzeigenportal.swbrett.files.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SwbFileTO getFile(@PathVariable("id") long id) {
        return service.getFileTO(id);
    }
}
