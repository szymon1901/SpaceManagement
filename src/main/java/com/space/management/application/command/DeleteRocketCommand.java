package com.space.management.application.command;

import com.space.management.common.exception.CommandValidationException;

public record DeleteRocketCommand(
    String rocketName
) {

    public DeleteRocketCommand {
        if (rocketName == null || rocketName.isBlank()) {
            throw new CommandValidationException("Rocket name cannot be null or blank");
        }
    }
}
