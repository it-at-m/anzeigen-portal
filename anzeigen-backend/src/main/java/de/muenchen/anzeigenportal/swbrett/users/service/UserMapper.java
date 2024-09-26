package de.muenchen.anzeigenportal.swbrett.users.service;

import de.muenchen.anzeigenportal.swbrett.users.model.SwbUser;
import de.muenchen.anzeigenportal.swbrett.users.model.SwbUserTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public SwbUser toSwbUser(SwbUserTO userTO) {
        SwbUser user = new SwbUser();

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

    public SwbUserTO toSwbUserTOAnonym(SwbUser user) {
        SwbUserTO userTO = new SwbUserTO();
        userTO.setId(user.getId());
        // employeeMail nur für Admins!

        return userTO;
    }

    public SwbUserTO toSwbUserTO(SwbUser user) {
        SwbUserTO userTO = new SwbUserTO();
        userTO.setId(user.getId());
        userTO.setLhmObjectId(user.getLhmObjectId());
        userTO.setDisplayName(user.getDisplayName());

        return userTO;
    }

}
