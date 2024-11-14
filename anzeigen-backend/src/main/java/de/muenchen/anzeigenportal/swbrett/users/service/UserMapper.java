package de.muenchen.anzeigenportal.swbrett.users.service;

import de.muenchen.anzeigenportal.swbrett.users.model.SwbUser;
import de.muenchen.anzeigenportal.swbrett.users.model.SwbUserTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public SwbUser toSwbUser(final SwbUserTO userTO) {
        final SwbUser user = new SwbUser();

        if (userTO.getId() != null) {
            user.setId(userTO.getId());
        }

        if (userTO.getLhmObjectId() != null) {
            user.setLhmObjectId(userTO.getLhmObjectId());
        }

        if (userTO.getDisplayName() != null) {
            user.setDisplayName(userTO.getDisplayName());
        }

        return user;
    }

    public SwbUserTO toSwbUserTOAnonym(final SwbUser user) {
        final SwbUserTO userTO = new SwbUserTO();
        userTO.setId(user.getId());
        // employeeMail nur für Admins!

        return userTO;
    }

    public SwbUserTO toSwbUserTO(final SwbUser user) {
        final SwbUserTO userTO = new SwbUserTO();
        userTO.setId(user.getId());
        userTO.setLhmObjectId(user.getLhmObjectId());
        userTO.setDisplayName(user.getDisplayName());

        return userTO;
    }

}
