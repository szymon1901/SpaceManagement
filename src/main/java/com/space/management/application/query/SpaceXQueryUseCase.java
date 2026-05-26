package com.space.management.application.query;

import com.space.management.application.model.MissionModel;
import java.util.List;

public interface SpaceXQueryUseCase {
    List<MissionModel> getMissionsSummary();
}
