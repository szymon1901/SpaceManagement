package com.space.management.domain;

import com.space.management.application.command.AddRocketCommand;
import com.space.management.common.exception.BusinessValidationException;
import com.space.management.repository.RocketEntity;

import static com.space.management.common.exception.ErrorMessage.MISSION_ALREADY_EXISTS;
import static com.space.management.common.exception.ErrorMessage.ROCKET_ALREADY_EXISTS;
import static com.space.management.common.exception.ErrorMessage.ROCKET_NOT_FOUND;
import static com.space.management.domain.Rocket.createNew;

class AddRocketUseCase {
    private final RocketCommandRepository rocketRepository;
    private final SpaceXDomainMapper mapper;

    public AddRocketUseCase(RocketCommandRepository rocketRepository, SpaceXDomainMapper mapper) {
        this.rocketRepository = rocketRepository;
        this.mapper = mapper;
    }

    void execute(AddRocketCommand command) {
        ensureRocketDoesNotExist(command.rocketName());

        Rocket newRocket = createNew(command.rocketName());

        saveRocketState(newRocket);
    }

    private void ensureRocketDoesNotExist(String rocketName) {
        rocketRepository.findByName(rocketName).ifPresent(rocket -> {
            throw new BusinessValidationException(ROCKET_ALREADY_EXISTS);
        });
    }

    private void saveRocketState(Rocket rocket) {
        RocketEntity updatedRocketEntity = mapper.toEntity(rocket);
        rocketRepository.save(updatedRocketEntity);
    }
}
