package com.mycompany.myapp.web.rest.errors;

public class NameAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public NameAlreadyUsedException() {
        super(ErrorConstants.DEFAULT_TYPE, "Name is already in use!", "productManagement", "nameexists");
    }
}
