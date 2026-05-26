package com.space.management.application.command;

import com.space.management.common.RocketStatus;

public record ChangeRocketStatusCommand(String rocketName, RocketStatus newStatus) {
}
