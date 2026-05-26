package com.space.management.application.command;

import com.space.management.common.exception.CommandValidationException;

public record DeleteMissionCommand(
    String missionName
) {

    public DeleteMissionCommand {
        if (missionName == null || missionName.isBlank()) {
            throw new CommandValidationException("Mission name cannot be null or blank");
        }
    }
}
