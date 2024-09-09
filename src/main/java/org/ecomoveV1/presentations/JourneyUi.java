package org.ecomoveV1.presentations;

import org.ecomoveV1.services.JourneyService;

import java.time.LocalDate;
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
}
