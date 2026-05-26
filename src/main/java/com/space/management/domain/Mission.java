package com.space.management.domain;

import com.space.management.common.MissionStatus;
import com.space.management.common.exception.BusinessValidationException;
import java.util.ArrayList;
import java.util.List;

import static com.space.management.common.exception.ErrorMessage.INVALID_MISSION_STATUS;
import static java.util.Collections.unmodifiableList;

class Mission {
    private final String name;
    private MissionStatus status;
    private final List<String> assignedRocketNames;

    Mission(String name, MissionStatus status, List<String> assignedRocketNames) {
        this.name = name;
        this.status = status;
        this.assignedRocketNames = new ArrayList<>(assignedRocketNames != null ? assignedRocketNames : List.of());
    }

    static Mission createNew(String name) {
        return new Mission(name, MissionStatus.SCHEDULED, new ArrayList<>()); // [cite: 20] initial status
    }

    void assignRocket(String rocketName) {
        if (this.status == MissionStatus.ENDED) {
            throw new BusinessValidationException(INVALID_MISSION_STATUS);
        }

        if (!this.assignedRocketNames.contains(rocketName)) {
            this.assignedRocketNames.add(rocketName);
        }
    }

    void changeStatus(MissionStatus newStatus) {
        this.status = newStatus;
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
