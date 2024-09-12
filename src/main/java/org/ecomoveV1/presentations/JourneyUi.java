package org.ecomoveV1.presentations;

import org.ecomoveV1.models.entities.Journey;
import org.ecomoveV1.models.entities.Ticket;
import org.ecomoveV1.services.JourneyService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class JourneyUi {

    private final JourneyService journeyService;
    final private Scanner scanner = new Scanner(System.in);

    public JourneyUi(JourneyService journeyService) {
        this.journeyService = journeyService;
    }

    public void createJourney(){
        System.out.println("#------------ Add New Journey : -------------#");

        System.out.print("Enter start location: ");
        String startLocation = scanner.nextLine();

        System.out.print("Enter end location: ");
        String endLocation = scanner.nextLine();

        System.out.print("Enter departure date (yyyy-MM-dd): ");
        LocalDate departureDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter arrival date (yyyy-MM-dd): ");
        LocalDate arrivalDate = LocalDate.parse(scanner.nextLine());

        journeyService.createJourney(startLocation, endLocation, departureDate, arrivalDate);

        System.out.println("Journey created successfully!");
    }


    public void displayAllJourneys() {
        System.out.println("#------------ All Journeys : -------------#");

        List<Journey> journeys = journeyService.getAllJourneys();

        if (journeys.isEmpty()) {
            System.out.println("No journeys found.");
        } else {
            for (Journey journey : journeys) {
                System.out.println("Journey ID: " + journey.getId());
                System.out.println("Start Location: " + journey.getStartLocation());
                System.out.println("End Location: " + journey.getEndLocation());
                System.out.println("Departure Time: " + journey.getDepartureTime());
                System.out.println("Arrival Time: " + journey.getArrival_time());

                List<Ticket> tickets = journey.getTickets();
                if (tickets == null || tickets.isEmpty()) {
                    System.out.println("No tickets found for this journey.");
                } else {
                    System.out.println("Tickets:");
                    for (Ticket ticket : tickets) {
                        System.out.println("- Ticket ID: " + ticket.getTicketId());
                        System.out.println("  Ticket Type: " + ticket.getTransportType());
                        System.out.println("  Price: " + ticket.getPurchasePrice());
                    }
                }
                System.out.println("---");
            }
        }
    }


}
