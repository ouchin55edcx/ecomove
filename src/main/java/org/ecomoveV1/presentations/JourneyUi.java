package org.ecomoveV1.presentations;

import org.ecomoveV1.models.entities.Journey;
import org.ecomoveV1.models.entities.Ticket;
import org.ecomoveV1.services.JourneyService;

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

        System.out.print("Enter departure date and time (yyyy-MM-dd HH:mm): ");
        LocalDateTime departureDateTime = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        System.out.print("Enter arrival date and time (yyyy-MM-dd HH:mm): ");
        LocalDateTime arrivalDateTime = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        journeyService.createJourney(startLocation, endLocation, departureDateTime, arrivalDateTime);

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
                System.out.println("Arrival Time: " + journey.getArrivalTime());

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

    public void searchJourneys() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║           Search Journeys              ║");
        System.out.println("╚════════════════════════════════════════╝");

        System.out.print("Enter start location: ");
        String startLocation = scanner.nextLine();

        System.out.print("Enter end location: ");
        String endLocation = scanner.nextLine();

        System.out.print("Enter departure date and time (yyyy-MM-dd HH:mm): ");
        LocalDateTime departureDateTime = LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        List<Journey> journeys = journeyService.searchJourneys(startLocation, endLocation, departureDateTime);

        if (journeys.isEmpty()) {
            System.out.println("\n┌────────────────────────────────────────┐");
            System.out.println("│   No journeys found matching criteria  │");
            System.out.println("└────────────────────────────────────────┘");
        } else {
            System.out.println("\n┌────────────────────────────────────────┐");
            System.out.println("│        Found the following journeys    │");
            System.out.println("└────────────────────────────────────────┘");

            for (Journey journey : journeys) {
                printJourneyDetails(journey);
            }
        }
    }

    private void printJourneyDetails(Journey journey) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        System.out.println("┌────────────────────────────────────────┐");
        System.out.printf("│ Journey ID: %-28s │\n", journey.getId());
        System.out.println("├────────────────────────────────────────┤");
        System.out.printf("│ From: %-35s │\n", journey.getStartLocation());
        System.out.printf("│ To: %-37s │\n", journey.getEndLocation());
        System.out.printf("│ Departure: %-30s │\n", journey.getDepartureTime().format(dateTimeFormatter));
        System.out.printf("│ Arrival: %-32s │\n", journey.getArrivalTime().format(dateTimeFormatter));
        System.out.println("├────────────────────────────────────────┤");
        System.out.println("│ Available Tickets:                     │");

        List<Ticket> tickets = journey.getTickets();
        if (tickets == null || tickets.isEmpty()) {
            System.out.println("│ No tickets available for this journey  │");
        } else {
            for (Ticket ticket : tickets) {
                System.out.println("│ ────────────────────────────────────── │");
                System.out.printf("│ Ticket ID: %-29s │\n", ticket.getTicketId());
                System.out.printf("│ Transport: %-29s │\n", ticket.getTransportType());
                System.out.printf("│ Purchase Price: $%-23.2f │\n", ticket.getPurchasePrice());
                System.out.printf("│ Sale Price: $%-26.2f │\n", ticket.getSalePrice());
                System.out.printf("│ Status: %-32s │\n", ticket.getTicketStatus());
                System.out.printf("│ Sale Date: %-29s │\n", formatDateTime(ticket.getSaleDate(), dateTimeFormatter));
            }
        }
        System.out.println("└────────────────────────────────────────┘");
    }

    private String formatDateTime(Object dateTime, DateTimeFormatter dateTimeFormatter) {
        if (dateTime instanceof LocalDateTime) {
            return ((LocalDateTime) dateTime).format(dateTimeFormatter);
        } else {
            return "N/A";
        }
    }
}