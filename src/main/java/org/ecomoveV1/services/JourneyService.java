package org.ecomoveV1.services;

import org.ecomoveV1.models.entities.Journey;
import org.ecomoveV1.repositories.JourneyRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class JourneyService {
    private final JourneyRepository journeyRepository;

    public JourneyService(JourneyRepository journeyRepository) {
        this.journeyRepository = journeyRepository;
    }

    public void createJourney(String startLocation, String endLocation, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {
        UUID id = UUID.randomUUID();
        Journey newJourney = new Journey(id, startLocation, endLocation, departureDateTime, arrivalDateTime, null);
        journeyRepository.createJourney(newJourney);
    }

    public List<Journey> getAllJourneys() {
        return journeyRepository.displayAllJourneys();
    }

    public List<Journey> searchJourneys(String startLocation, String endLocation, LocalDateTime departureDateTime) {
        return journeyRepository.searchJourneys(startLocation, endLocation, departureDateTime);
    }
}