package de.muenchen.anzeigenportal.security;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Each possible authority in this project is represented by an enum.
 * The enums are used within the {@link PagingAndSortingRepository}
 * in the annotation e.g. {@link PreAuthorize}.
 */
public enum AuthoritiesEnum {
    @SuppressWarnings("PMD.FieldNamingConventions") anwender,

    @SuppressWarnings("PMD.FieldNamingConventions") fachadmin,
    // add your authorities here and also add these new authorities to sso-authorisation.json.

}
