package org.ecomoveV1.presentations;

import org.ecomoveV1.models.entities.PromotionalOffer;
import org.ecomoveV1.models.enums.OfferStatus;
import org.ecomoveV1.models.enums.ReductionType;
import org.ecomoveV1.repositories.PromotionalOfferRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
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



    public void listAllPromotionalOffers() {
        List<PromotionalOffer> offers = repository.getAllPromotionalOffers();
        if (offers.isEmpty()) {
            System.out.println("No promotional offers found.");
        } else {
            System.out.println("\nAll Promotional Offers:");
            for (PromotionalOffer offer : offers) {
                System.out.println(offer);
            }
        }
    }


    public void displayPromotionById() {
        System.out.print("Enter the promotion ID: ");
        String idString = scanner.nextLine().trim();

        try {
            UUID id = UUID.fromString(idString);
            PromotionalOffer offer = repository.getPromotionalOfferById(id);

            if (offer != null) {
                System.out.println("\nPromotion Details:");
                System.out.println("ID: " + offer.getId());
                System.out.println("Contract ID: " + offer.getContractId());
                System.out.println("Offer Name: " + offer.getOfferName());
                System.out.println("Description: " + offer.getDescription());
                System.out.println("Start Date: " + offer.getStartDate());
                System.out.println("End Date: " + offer.getEndDate());
                System.out.println("Reduction Type: " + offer.getReductionType());
                System.out.println("Reduction Value: " + offer.getReduction_value());
                System.out.println("Conditions: " + offer.getConditions());
                System.out.println("Status: " + offer.getStatus());
            } else {
                System.out.println("No promotion found with ID: " + idString);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID format. Please try again.");
        }
    }

    public void updatePromotionalOffer() {
        System.out.print("Enter the ID of the promotional offer to update: ");
        String idString = scanner.nextLine().trim();

        try {
            UUID id = UUID.fromString(idString);
            PromotionalOffer offer = repository.getPromotionalOfferById(id);

            if (offer != null) {
                System.out.println("Enter new details (press Enter to keep current value):");

                System.out.print("Offer Name [" + offer.getOfferName() + "]: ");
                String offerName = scanner.nextLine();
                if (!offerName.isEmpty()) offer.setOfferName(offerName);

                System.out.print("Description [" + offer.getDescription() + "]: ");
                String description = scanner.nextLine();
                if (!description.isEmpty()) offer.setDescription(description);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                System.out.print("Start Date [" + offer.getStartDate() + "]: ");
                String startDateStr = scanner.nextLine();
                if (!startDateStr.isEmpty()) {
                    try {
                        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
                        offer.setStartDate(startDate);
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Keeping the current value.");
                    }
                }

                System.out.print("End Date [" + offer.getEndDate() + "]: ");
                String endDateStr = scanner.nextLine();
                if (!endDateStr.isEmpty()) {
                    try {
                        LocalDate endDate = LocalDate.parse(endDateStr, formatter);
                        offer.setEndDate(endDate);
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Keeping the current value.");
                    }
                }

                System.out.print("Reduction Type [" + offer.getReductionType() + "]: ");
                String reductionTypeStr = scanner.nextLine();
                if (!reductionTypeStr.isEmpty()) {
                    try {
                        ReductionType reductionType = ReductionType.valueOf(reductionTypeStr.toUpperCase());
                        offer.setReductionType(reductionType);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid reduction type. Keeping the current value.");
                    }
                }

                System.out.print("Reduction Value [" + offer.getReduction_value() + "]: ");
                String reductionValueStr = scanner.nextLine();
                if (!reductionValueStr.isEmpty()) {
                    try {
                        BigDecimal reductionValue = new BigDecimal(reductionValueStr);
                        offer.setReduction_value(reductionValue);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format. Keeping the current value.");
                    }
                }

                System.out.print("Conditions [" + offer.getConditions() + "]: ");
                String conditions = scanner.nextLine();
                if (!conditions.isEmpty()) offer.setConditions(conditions);

                System.out.print("Status [" + offer.getStatus() + "]: ");
                String statusStr = scanner.nextLine();
                if (!statusStr.isEmpty()) {
                    try {
                        OfferStatus status = OfferStatus.valueOf(statusStr.toUpperCase());
                        offer.setStatus(status);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid status. Keeping the current value.");
                    }
                }

                repository.updatePromotionalOffer(offer);
                System.out.println("Promotional offer updated successfully.");
            } else {
                System.out.println("No promotion found with ID: " + idString);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID format. Please try again.");
        }
    }

    public void deletePromotionalOffer() {
        System.out.print("Enter the ID of the promotional offer to delete: ");
        String idString = scanner.nextLine().trim();

        try {
            UUID id = UUID.fromString(idString);
            PromotionalOffer offer = repository.getPromotionalOfferById(id);

            if (offer != null) {
                System.out.println("Are you sure you want to delete this offer? (y/n)");
                System.out.println("Offer Name: " + offer.getOfferName());
                System.out.println("Description: " + offer.getDescription());

                String confirmation = scanner.nextLine().trim().toLowerCase();
                if (confirmation.equals("y")) {
                    repository.deletePromotionalOffer(id);
                    System.out.println("Promotional offer deleted successfully.");
                } else {
                    System.out.println("Deletion cancelled.");
                }
            } else {
                System.out.println("No promotion found with ID: " + idString);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID format. Please try again.");
        }
    }
}



