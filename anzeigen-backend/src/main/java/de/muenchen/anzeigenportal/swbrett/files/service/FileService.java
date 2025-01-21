package de.muenchen.anzeigenportal.swbrett.files.service;

import de.muenchen.anzeigenportal.swbrett.files.model.SwbFile;
import de.muenchen.anzeigenportal.swbrett.files.model.SwbFileTO;
import de.muenchen.anzeigenportal.swbrett.files.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FileService {

    @Autowired
    private FileRepository repository;

    @Autowired
    private FileMapper mapper;

    public SwbFileTO getFileTO(final long id) {
        final SwbFile file = repository.findBy(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found"));;
        return mapper.toSwbFileTO(file);
    }

    public SwbFile getFile(final long id) {
        return repository.getOne(id);
    }

}
