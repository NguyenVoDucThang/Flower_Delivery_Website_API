package com.mycompany.myapp.web.rest.errors.notexists;

import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.errors.ErrorConstants;

public class DeliveryNotExistsException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public DeliveryNotExistsException() {
        super(ErrorConstants.DEFAULT_TYPE, "Delivery not found", "deliveryManagement", "notexists");
    }
}
