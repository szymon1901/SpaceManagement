package com.space.management.application.command;

import com.space.management.common.MissionStatus;
import com.space.management.common.exception.CommandValidationException;

public record ChangeMissionStatusCommand(
    String missionName,
    MissionStatus newStatus
) {
    public ChangeMissionStatusCommand {
        if (newStatus == null) {
            throw new CommandValidationException("Mission status cannot be null");
        }

        if (missionName == null || missionName.isBlank()) {
            throw new CommandValidationException("Mission name cannot be null or blank");
        }
    }
}

