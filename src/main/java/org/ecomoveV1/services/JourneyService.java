package org.ecomoveV1.services;

import org.ecomoveV1.models.entities.Journey;
import org.ecomoveV1.models.entities.Ticket;
import org.ecomoveV1.repositories.JourneyRepository;

import java.time.LocalDate;
import java.util.UUID;

public class JourneyService {


    private final JourneyRepository journeyRepository;

    public JourneyService(JourneyRepository journeyRepository) {
        this.journeyRepository = journeyRepository;
    }


    public void createJourney(String startLocation, String endLocation, LocalDate departureDate, LocalDate arrivalDate) {
        UUID id = UUID.randomUUID();
        Journey newJourney = new Journey(id, startLocation, endLocation, departureDate, arrivalDate, null);
        journeyRepository.createJourney(newJourney);
    }
}
