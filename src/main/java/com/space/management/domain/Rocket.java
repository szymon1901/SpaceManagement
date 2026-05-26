package com.space.management.domain;

import com.space.management.common.RocketStatus;
import com.space.management.common.exception.BusinessValidationException;

import static com.space.management.common.RocketStatus.IN_SPACE;
import static com.space.management.common.RocketStatus.ON_GROUND;
import static com.space.management.common.exception.ErrorMessage.ROCKET_ALREADY_ASSIGNED;

public class Rocket {
    private final String name;
    private RocketStatus status;
    private String assignedMissionName;

    public Rocket(String name, RocketStatus status, String assignedMissionName) {
        this.name = name;
        this.status = status;
        this.assignedMissionName = assignedMissionName;
    }

    public static Rocket createNew(String name) {
        return new Rocket(name, ON_GROUND, null);
    }

    public void assignToMission(String missionName) {
        if (this.assignedMissionName != null && !this.assignedMissionName.equals(missionName)) {
            throw new BusinessValidationException(ROCKET_ALREADY_ASSIGNED);
        }
        this.assignedMissionName = missionName;
        this.status = IN_SPACE;
    }

    public void changeStatus(RocketStatus newStatus) {
        this.status = newStatus;
    }

    public String getName() {
        return name;
    }

    public RocketStatus getStatus() {
        return status;
    }

    public String getAssignedMissionName() {
        return assignedMissionName;
    }
}
