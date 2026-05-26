package com.space.management.domain;

import com.space.management.application.model.MissionModel;
import java.util.List;

public interface SpaceXQueryRepository {
    List<MissionModel> getMissionsSummary();
}
