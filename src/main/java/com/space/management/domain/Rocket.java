package com.space.management.domain;

import com.space.management.common.RocketStatus;
import com.space.management.common.exception.BusinessValidationException;

import static com.space.management.common.RocketStatus.IN_REPAIR;
import static com.space.management.common.RocketStatus.IN_SPACE;
import static com.space.management.common.RocketStatus.ON_GROUND;
import static com.space.management.common.exception.ErrorMessage.INVALID_MISSION_STATUS;
import static com.space.management.common.exception.ErrorMessage.INVALID_ROCKET_STATUS;
import static com.space.management.common.exception.ErrorMessage.ROCKET_ALREADY_ASSIGNED;

class Rocket {
    private final String name;
    private RocketStatus status;
    private String assignedMissionName;

    Rocket(String name, RocketStatus status, String assignedMissionName) {
        this.name = name;
        this.status = status;
        this.assignedMissionName = assignedMissionName;
    }

    static Rocket createNew(String name) {
        return new Rocket(name, ON_GROUND, null);
    }

    void assignToMission(String missionName) {
        if (this.assignedMissionName != null && !this.assignedMissionName.equals(missionName)) {
            throw new BusinessValidationException(ROCKET_ALREADY_ASSIGNED);
        }
        this.assignedMissionName = missionName;
        this.status = IN_SPACE;
    }

    void releaseFromMission() {
        this.assignedMissionName = null;
        this.status = ON_GROUND;
    }

    void verifyCanBeDeleted() {
        if (assignedMissionName != null && !assignedMissionName.isBlank()) {
            throw new BusinessValidationException(
                INVALID_MISSION_STATUS,
                "Cannot delete rocket: " + name + " because it is assigned to mission: " + assignedMissionName
            );
        }
    }

    public void changeStatus(RocketStatus newStatus) {
        if (newStatus == ON_GROUND && this.assignedMissionName != null) {
            throw new BusinessValidationException(INVALID_ROCKET_STATUS, "Rocket cannot be ON_GROUND while assigned to a mission");
        }
        if ((newStatus == IN_SPACE || newStatus == IN_REPAIR) && this.assignedMissionName == null) {
            throw new BusinessValidationException(INVALID_ROCKET_STATUS, "Rocket must be assigned to a mission to be IN_SPACE or IN_REPAIR");
        }
        this.status = newStatus;
    }

    String getName() {
        return name;
    }

    RocketStatus getStatus() {
        return status;
    }

    String getAssignedMissionName() {
        return assignedMissionName;
    }
}
