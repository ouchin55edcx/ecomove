package org.ecomoveV1.repositories;

import org.ecomoveV1.models.entities.Journey;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface JourneyRepository {
    void createJourney(Journey journey);
    List<Journey> displayAllJourneys();
    List<Journey> searchJourneys(String startLocation, String endLocation, LocalDateTime departureDateTime);
    void reserveJourney(UUID journeyId, UUID customerId);

}