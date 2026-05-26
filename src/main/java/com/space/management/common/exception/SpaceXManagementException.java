package com.space.management.common.exception;

class SpaceXManagementException extends RuntimeException {
    private final ErrorMessage errorMessage;

    protected SpaceXManagementException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

    protected SpaceXManagementException(ErrorMessage errorMessage, String additionalInfo) {
        super(errorMessage.getMessage() + " " + additionalInfo);
        this.errorMessage = errorMessage;
    }

    protected SpaceXManagementException(String message) {
        super(message);
        this.errorMessage = null;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
