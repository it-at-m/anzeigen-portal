package de.muenchen.anzeigenportal.swbrett.files.service;

import de.muenchen.anzeigenportal.swbrett.files.model.SwbFile;
import de.muenchen.anzeigenportal.swbrett.files.model.SwbFileTO;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class FileMapper {

    /**
     * TO ohne die Datei selbst
     */
    public SwbFileTO toSwbFileTOLight(final SwbFile file) {
        final SwbFileTO fileTO = new SwbFileTO();
        fileTO.setId(file.getId());
        fileTO.setName(file.getName());
        fileTO.setSize(file.getSize());

        return fileTO;
    }

    public SwbFileTO toSwbFileTO(final SwbFile file) {
        final SwbFileTO fileTO = new SwbFileTO();
        fileTO.setId(file.getId());
        fileTO.setName(file.getName());
        fileTO.setSize(file.getSize());
        fileTO.setFileBase64(Base64.getEncoder().encodeToString(file.getFile()));

        return fileTO;
    }

    public SwbFile toSwbFile(final SwbFileTO fileTO) {
        final SwbFile file = new SwbFile();
        file.setId(fileTO.getId());
        file.setName(fileTO.getName());
        file.setSize(fileTO.getSize());
        if (fileTO.getFileBase64() != null) {
            file.setFile(Base64.getDecoder().decode(fileTO.getFileBase64()));
        }

        return file;
    }
}
