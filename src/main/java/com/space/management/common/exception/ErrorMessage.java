package com.space.management.common.exception;

public enum ErrorMessage {
    ROCKET_ALREADY_EXISTS("Rocket with this name already exists."),
    MISSION_ALREADY_EXISTS("Mission with this name already exists."),
    ROCKET_NOT_FOUND("Rocket with the given name does not exist."),
    MISSION_NOT_FOUND("Mission with the given name does not exist."),
    ROCKET_ALREADY_ASSIGNED("Rocket is already assigned to a mission."),
    INVALID_ROCKET_STATUS("Invalid rocket status transition."),
    INVALID_MISSION_STATUS("Invalid mission status transition.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
