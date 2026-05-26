package com.space.management.common.exception;

public class SpaceXManagementException extends RuntimeException {
    private final ErrorMessage errorMessage;

    protected SpaceXManagementException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

    protected SpaceXManagementException(ErrorMessage errorMessage, String additionalInfo) {
        super(errorMessage.getMessage() + " " + additionalInfo);
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
