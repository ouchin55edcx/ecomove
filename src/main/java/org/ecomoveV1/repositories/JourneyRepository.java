package org.ecomoveV1.repositories;

import org.ecomoveV1.models.entities.Journey;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface JourneyRepository {
    void createJourney(Journey journey);
    List<Journey> displayAllJourneys();
    List<Journey> searchJourneys(String startLocation, String endLocation, LocalDate date);



}
