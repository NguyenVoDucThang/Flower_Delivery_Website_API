package com.mycompany.myapp.web.rest.errors.notexists;

import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.errors.ErrorConstants;

public class UserNotExistsException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public UserNotExistsException() {
        super(ErrorConstants.DEFAULT_TYPE, "User not found", "userManagement", "notexists");
    }
}
