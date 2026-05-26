package com.space.management.application.command;

import com.space.management.common.MissionStatus;

public record ChangeMissionStatusCommand(String missionName, MissionStatus newStatus) {
}
