package com.space.management.application.model;

import com.space.management.common.RocketStatus;

public record RocketModel(
    String name,
    RocketStatus status
) {
}
