package com.space.management.common.exception;

public class BusinessValidationException extends SpaceXManagementException{
    public BusinessValidationException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public BusinessValidationException(ErrorMessage errorMessage, String customDetails) {
        super(errorMessage, customDetails);
    }
}
