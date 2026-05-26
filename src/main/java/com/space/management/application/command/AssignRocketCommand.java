package com.space.management.application.command;

public record AssignRocketCommand(
    String rocketName, String missionName
) {
}
