package org.ecomoveV1.presentations;

import org.ecomoveV1.models.entities.PromotionalOffer;
import org.ecomoveV1.models.enums.OfferStatus;
import org.ecomoveV1.models.enums.ReductionType;
import org.ecomoveV1.repositories.PromotionalOfferRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.UUID;

public class PromotionUi {

    final private PromotionalOfferRepository repository ;
    final private Scanner scanner = new Scanner(System.in);


    public PromotionUi(PromotionalOfferRepository repository) {
        this.repository = repository;

    }

    public void addPromotion(){
        System.out.println("#------------ Add New Promotion : -------------#");

        UUID id = UUID.randomUUID();
        UUID contractId = getContractIdInput();
        String offerName = getOfferNameInput();
        String description = getDescriptionInput();
        LocalDate startDate = getStartDateInput();
        LocalDate endDate = getEndDateInput(startDate);
        ReductionType  reductionType = getReductionTypeInput();
        BigDecimal reductionValue = getReductionValueInput(reductionType);
        String conditions = getConditionsInput();
        OfferStatus offerStatus = getOfferStatusInput();


        PromotionalOffer newPromotionalOffer = new PromotionalOffer(
                UUID.randomUUID(),
                contractId,
                offerName,
                description,
                startDate,
                endDate,
                reductionType,
                reductionValue,
                conditions,
                offerStatus
        );

        repository.addPromotion(newPromotionalOffer);
        System.out.println("New PromotionalOffer added successfully!");

    }
    private UUID getContractIdInput() {
        while (true) {
            System.out.print("Enter Contract ID: ");
            String input = scanner.nextLine().trim();
            try {
                return UUID.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID format. Please try again.");
            }
        }
    }

    private String getOfferNameInput() {
        while (true) {
            System.out.print("Enter Offer Name: ");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Offer name cannot be empty. Please try again.");
        }
    }

    private String getDescriptionInput() {
        System.out.print("Enter Description: ");
        return scanner.nextLine().trim();
    }

    private LocalDate getStartDateInput() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            System.out.print("Enter Start Date (YYYY-MM-DD): ");
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
    }

    private LocalDate getEndDateInput(LocalDate startDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            System.out.print("Enter End Date (YYYY-MM-DD): ");
            String input = scanner.nextLine().trim();
            try {
                LocalDate endDate = LocalDate.parse(input, formatter);
                if (endDate.isBefore(startDate)) {
                    System.out.println("End date must be after start date. Please try again.");
                } else {
                    return endDate;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
    }

    private ReductionType getReductionTypeInput() {
        while (true) {
            System.out.print("Enter Reduction Type (PERCENTAGE or FIXED_AMOUNT): ");
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                return ReductionType.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid reduction type. Please enter PERCENTAGE or FIXED_AMOUNT.");
            }
        }
    }

    private BigDecimal getReductionValueInput(ReductionType reductionType) {
        while (true) {
            System.out.print("Enter Reduction Value: ");
            String input = scanner.nextLine().trim();
            try {
                BigDecimal value = new BigDecimal(input);
                if (value.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("Reduction value must be positive. Please try again.");
                } else if (reductionType == ReductionType.PERCENTAGE && value.compareTo(new BigDecimal("100")) > 0) {
                    System.out.println("Percentage reduction cannot exceed 100%. Please try again.");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please enter a valid number.");
            }
        }
    }

    private String getConditionsInput() {
        System.out.print("Enter Conditions (optional): ");
        return scanner.nextLine().trim();
    }

    private OfferStatus getOfferStatusInput() {
        while (true) {
            System.out.print("Enter Offer Status (ACTIVE or INACTIVE): ");
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                return OfferStatus.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid offer status. Please enter ACTIVE or INACTIVE.");
            }
        }
    }


}
