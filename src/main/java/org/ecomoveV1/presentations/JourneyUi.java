package org.ecomoveV1.presentations;

import org.ecomoveV1.models.entities.Customer;
import org.ecomoveV1.models.entities.Journey;
import org.ecomoveV1.models.entities.Ticket;
import org.ecomoveV1.services.CustomerService;
import org.ecomoveV1.services.JourneyService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class JourneyUi {

    private final JourneyService journeyService;
    private final CustomerService customerService;
    final private Scanner scanner = new Scanner(System.in);
    final private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private Customer loggedInCustomer;



    public JourneyUi(JourneyService journeyService, CustomerService customerService) {
        this.journeyService = journeyService;
        this.customerService = customerService;
    }

    public void setLoggedInCustomer(Customer customer) {
        this.loggedInCustomer = customer;
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
        System.out.println("┌────────────────────────────────────────┐");
        System.out.printf("│ Journey ID: %-28s │%n", journey.getId());
        System.out.println("├────────────────────────────────────────┤");
        System.out.printf("│ From: %-35s │%n", journey.getStartLocation());
        System.out.printf("│ To: %-37s │%n", journey.getEndLocation());
        System.out.printf("│ Departure: %-30s │%n", journey.getDepartureTime().format(dateTimeFormatter));
        System.out.printf("│ Arrival: %-32s │%n", journey.getArrivalTime().format(dateTimeFormatter));
        System.out.println("├────────────────────────────────────────┤");
        System.out.println("│ Available Tickets:                     │");

        List<Ticket> tickets = journey.getTickets();
        if (tickets == null || tickets.isEmpty()) {
            System.out.println("│ No tickets available for this journey  │");
        } else {
            for (Ticket ticket : tickets) {
                System.out.println("│ ────────────────────────────────────── │");
                System.out.printf("│ Transporteur: %-26s │%n", ticket.getTransportType());

                LocalDateTime departureTime = journey.getDepartureTime();
                LocalDateTime arrivalTime = journey.getArrivalTime();
                Duration duration = Duration.between(departureTime, arrivalTime);
                long hours = duration.toHours();
                long minutes = duration.toMinutesPart();

                System.out.printf("│ Horaire: %-32s │%n", departureTime.format(dateTimeFormatter));
                System.out.printf("│ Durée: %d hours %d minutes            │%n", hours, minutes);
                System.out.printf("│ Prix: $%-33.2f │%n", ticket.getSalePrice());
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


    public void reserveJourney() {

        if (loggedInCustomer == null) {
            System.out.println("You need to be logged in to search for journeys.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║           Reserve Journey              ║");
        System.out.println("╚════════════════════════════════════════╝");

        System.out.print("Enter customer email: ");
        String customerEmail = scanner.nextLine();

        Optional<Customer> customerOptional = customerService.findCustomerByEmail(customerEmail);
        if (customerOptional.isEmpty()) {
            System.out.println("Customer not found. Please register first.");
            return;
        }

        System.out.print("Enter journey ID: ");
        UUID journeyId = UUID.fromString(scanner.nextLine());

        try {
            journeyService.reserveJourney(customerEmail, journeyId);
            System.out.println("Journey reserved successfully!");
        } catch (Exception e) {
            System.out.println("Error reserving journey: " + e.getMessage());
        }
    }
}