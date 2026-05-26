package com.space.management.application.model;

import com.space.management.common.MissionStatus;
import java.util.List;

public record MissionModel(
    String name,
    MissionStatus status,
    List<RocketModel> rockets
) {
}
