package com.space.management.application.command;

import com.space.management.common.exception.CommandValidationException;

public record AddRocketCommand(
    String rocketName
) {

    public AddRocketCommand {
        if (rocketName == null || rocketName.isBlank()) {
            throw new CommandValidationException("Rocket name cannot be null or blank");
        }
    }
}
