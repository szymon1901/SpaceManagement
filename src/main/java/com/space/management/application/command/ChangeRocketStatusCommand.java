package com.space.management.application.command;

import com.space.management.common.RocketStatus;
import com.space.management.common.exception.CommandValidationException;

public record ChangeRocketStatusCommand(
    String rocketName,
    RocketStatus newStatus
) {

    public ChangeRocketStatusCommand {
        if (newStatus == null) {
            throw new CommandValidationException("Rocket status cannot be null");
        }

        if (rocketName == null || rocketName.isBlank()) {
            throw new CommandValidationException("Rocket name cannot be null or blank");
        }
    }
}
