package com.space.management.application.command;

import java.util.List;

public record AssignMultipleRocketsCommand(
    String missionName,
    List<String> rocketNames
) {

    public AssignMultipleRocketsCommand {
        if (missionName == null || missionName.isBlank()) {
            throw new IllegalArgumentException("Mission name cannot be null or blank");
        }

        if (rocketNames == null || rocketNames.isEmpty()) {
            throw new IllegalArgumentException("Rocket names list cannot be null or empty");
        }

        for (String rocketName : rocketNames) {
            if (rocketName == null || rocketName.isBlank()) {
                throw new IllegalArgumentException("Rocket name inside the list cannot be null or blank");
            }
        }
    }
}
