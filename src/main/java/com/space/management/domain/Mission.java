package com.space.management.domain;

import com.space.management.common.MissionStatus;
import com.space.management.common.exception.BusinessValidationException;
import java.util.ArrayList;
import java.util.List;

import static com.space.management.common.MissionStatus.ENDED;
import static com.space.management.common.MissionStatus.IN_PROGRESS;
import static com.space.management.common.MissionStatus.PENDING;
import static com.space.management.common.MissionStatus.SCHEDULED;
import static com.space.management.common.RocketStatus.IN_REPAIR;
import static com.space.management.common.exception.ErrorMessage.INVALID_MISSION_STATUS;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

class Mission {
    private final String name;
    private MissionStatus status;
    private final List<String> assignedRocketNames;

    Mission(String name, MissionStatus status, List<String> assignedRocketNames) {
        this.name = name;
        this.status = status;
        this.assignedRocketNames = new ArrayList<>(assignedRocketNames != null ? assignedRocketNames : emptyList());
    }

    static Mission createNew(String name) {
        return new Mission(name, SCHEDULED, new ArrayList<>());
    }

    void updateStatusBasedOnRockets(List<Rocket> rockets) {
        if (this.status == ENDED) {
            return;
        }

        if (this.assignedRocketNames.isEmpty()) {
            this.status = SCHEDULED;
            return;
        }

        boolean hasRocketInRepair = rockets.stream()
            .anyMatch(rocket -> rocket.getStatus() == IN_REPAIR);

        if (hasRocketInRepair) {
            this.status = PENDING;
        } else {
            this.status = IN_PROGRESS;
        }
    }

    void assignRocket(String rocketName) {
        if (this.status == ENDED) {
            throw new BusinessValidationException(INVALID_MISSION_STATUS);
        }

        if (!this.assignedRocketNames.contains(rocketName)) {
            this.assignedRocketNames.add(rocketName);
        }
    }

    public void changeStatus(MissionStatus newStatus) {
        if (newStatus == ENDED) {
            endMission();
            return;
        }

        if (newStatus == SCHEDULED && !assignedRocketNames.isEmpty()) {
            throw new BusinessValidationException(INVALID_MISSION_STATUS, "Cannot change to SCHEDULED when rockets are already assigned");
        }

        this.status = newStatus;
    }

    void endMission() {
        this.status = ENDED;
        this.assignedRocketNames.clear();
    }

    String getName() {
        return name;
    }

    MissionStatus getStatus() {
        return status;
    }

    List<String> getAssignedRocketNames() {
        return unmodifiableList(assignedRocketNames);
    }
}
