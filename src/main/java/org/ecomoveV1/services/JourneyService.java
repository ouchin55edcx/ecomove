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

    public void reserveJourney(String customerEmail, UUID journeyId) {
        // Assuming you have a method to get customer ID from email
        UUID customerId = getCustomerIdFromEmail(customerEmail);
        journeyRepository.reserveJourney(journeyId, customerId);
    }

    private UUID getCustomerIdFromEmail(String email) {
        // Implement this method to retrieve customer ID from email
        // This might involve calling a method from CustomerService
        throw new UnsupportedOperationException("Method not yet implemented");
    }
}