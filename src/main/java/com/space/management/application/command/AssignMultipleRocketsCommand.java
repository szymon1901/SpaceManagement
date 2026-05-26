package com.space.management.application.command;

import java.util.List;

public record AssignMultipleRocketsCommand(
    String missionName, List<String> rocketNames
) {
}
