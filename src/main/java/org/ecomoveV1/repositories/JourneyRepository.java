package org.ecomoveV1.repositories;

import org.ecomoveV1.models.entities.Journey;

import java.util.List;

public interface JourneyRepository {
    void createJourney(Journey journey);
    List<Journey> displayAllJourneys();
}
