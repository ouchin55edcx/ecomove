package org.ecomoveV1.presentations;

import org.ecomoveV1.models.entities.Contract;
import org.ecomoveV1.models.enums.ContractStatus;
import org.ecomoveV1.repositories.ContactRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.UUID;

public class ContractUi {

    final private ContactRepository repository;
    final private Scanner scanner = new Scanner(System.in);

    public ContractUi(ContactRepository repository) {
        this.repository = repository;
    }

    public void addContract() {
        System.out.println("#------------ Add New Contract : -------------#");

        UUID id = UUID.randomUUID();
        UUID partnerId = getPartnerIdInput();
        LocalDate startDate = getDateInput("Enter start date (YYYY-MM-DD): ");
        LocalDate endDate = getEndDateInput(startDate);
        BigDecimal specialRate = getSpecialRateInput();
        String agreementConditions = getAgreementConditionsInput();
        boolean renewable = getRenewableInput();

        ContractStatus status = ContractStatus.ACTIVE;

        Contract newContract = new Contract(id, partnerId, startDate, endDate, specialRate, agreementConditions, renewable, status);
        repository.addContract(newContract);
        System.out.println("Contract added successfully!");
    }

    private UUID getPartnerIdInput() {
        while (true) {
            try {
                System.out.print("Enter partner ID: ");
                return UUID.fromString(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID format. Please try again.");
            }
        }
    }

    private LocalDate getDateInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
    }

    private LocalDate getEndDateInput(LocalDate startDate) {
        while (true) {
            LocalDate endDate = getDateInput("Enter end date (YYYY-MM-DD): ");
            if (endDate.isAfter(startDate)) {
                return endDate;
            } else {
                System.out.println("End date must be after start date. Please try again.");
            }
        }
    }

    private BigDecimal getSpecialRateInput() {
        while (true) {
            try {
                System.out.print("Enter special rate (e.g., 1000.00): ");
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please try again.");
            }
        }
    }

    private String getAgreementConditionsInput() {
        System.out.print("Enter agreement conditions: ");
        return scanner.nextLine();
    }

    private boolean getRenewableInput() {
        while (true) {
            System.out.print("Is the contract renewable? (true/false): ");
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("true") || input.equals("false")) {
                return Boolean.parseBoolean(input);
            } else {
                System.out.println("Please enter either 'true' or 'false'.");
            }
        }
    }
}