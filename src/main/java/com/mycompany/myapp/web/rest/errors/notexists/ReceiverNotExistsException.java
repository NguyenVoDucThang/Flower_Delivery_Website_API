package com.mycompany.myapp.web.rest.errors.notexists;

import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.errors.ErrorConstants;

public class ReceiverNotExistsException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public ReceiverNotExistsException() {
        super(ErrorConstants.DEFAULT_TYPE, "Receiver not found", "recevierManagement", "notexists");
    }
}
