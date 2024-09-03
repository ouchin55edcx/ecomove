package org.ecomoveV1.presentations;

import org.ecomoveV1.models.entities.Contract;
import org.ecomoveV1.models.enums.ContractStatus;
import org.ecomoveV1.repositories.ContactRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
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

    public void displayContractDetails() {
        List<String> contractDetails = repository.findAllContractsWithCompanyName();

        if (contractDetails.isEmpty()) {
            System.out.println("No contracts found!");
        } else {
            System.out.println("#------------- All Contracts -------------#");

            int count = 0;
            for (String contractDetail : contractDetails) {
                System.out.println("Contract " + (++count) + ":");
                String[] details = contractDetail.split(",");

                for (String detail : details) {
                    System.out.println(detail.trim());
                }

                System.out.println("#----------------------------------------#");
            }
        }
    }


    public void displayContractsByPartnerId() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Partner ID: ");
        String partnerIdInput = scanner.nextLine();

        try {
            UUID partnerId = UUID.fromString(partnerIdInput);
            List<Contract> contracts = repository.findContractsByPartnerId(partnerId); // Changed to Contract

            if (contracts.isEmpty()) {
                System.out.println("No contracts found for this partner ID.");
            } else {
                System.out.println("#------------- Contracts for Partner ID: " + partnerId + " -------------#");

                for (Contract contract : contracts) {
                    System.out.println("Contract ID: " + contract.getId());
                    System.out.println("Start Date: " + contract.getStartDate());
                    System.out.println("End Date: " + contract.getEndDate());
                    System.out.println("Special Rate: " + contract.getSpecialRate());
                    System.out.println("Agreement Conditions: " + contract.getAgreementConditions());
                    System.out.println("Renewable: " + (contract.isRenewable() ? "Yes" : "No"));
                    System.out.println("Status: " + contract.getStatus());
                    System.out.println("#---------------------------------------------#");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Partner ID format.");
        }
    }


    public void displayContractById() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Contract ID: ");
        String contractIdInput = scanner.nextLine();

        try {
            UUID contractId = UUID.fromString(contractIdInput);
            Contract contract = repository.getContractById(contractId);

            if (contract == null) {
                System.out.println("No contract found with this ID.");
            } else {
                System.out.println("#------------- Contract ID: " + contract.getId() + " -------------#");
                System.out.println("Partner ID: " + contract.getPartnerId());
                System.out.println("Start Date: " + contract.getStartDate());
                System.out.println("End Date: " + contract.getEndDate());
                System.out.println("Special Rate: " + contract.getSpecialRate());
                System.out.println("Agreement Conditions: " + contract.getAgreementConditions());
                System.out.println("Renewable: " + (contract.isRenewable() ? "Yes" : "No"));
                System.out.println("Status: " + contract.getStatus());
                System.out.println("#---------------------------------------------#");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Contract ID format.");
        }
    }


    public void updateContractById() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Contract ID: ");
        String contractIdInput = scanner.nextLine();

        try {
            UUID contractId = UUID.fromString(contractIdInput);

            // Fetch existing contract to pre-fill the fields
            Contract existingContract = repository.getContractById(contractId);
            if (existingContract == null) {
                System.out.println("No contract found with this ID.");
                return;
            }

            // Display current contract values and ask for new values
            System.out.println("Current Partner ID: " + existingContract.getPartnerId());
            System.out.print("Enter new Partner ID (or press Enter to keep current): ");
            String partnerIdInput = scanner.nextLine();
            UUID newPartnerId = partnerIdInput.isEmpty() ? existingContract.getPartnerId() : UUID.fromString(partnerIdInput);

            System.out.println("Current Start Date: " + existingContract.getStartDate());
            System.out.print("Enter new Start Date (YYYY-MM-DD) (or press Enter to keep current): ");
            String startDateInput = scanner.nextLine();
            LocalDate newStartDate = startDateInput.isEmpty() ? existingContract.getStartDate() : LocalDate.parse(startDateInput);

            System.out.println("Current End Date: " + existingContract.getEndDate());
            System.out.print("Enter new End Date (YYYY-MM-DD) (or press Enter to keep current): ");
            String endDateInput = scanner.nextLine();
            LocalDate newEndDate = endDateInput.isEmpty() ? existingContract.getEndDate() : LocalDate.parse(endDateInput);

            System.out.println("Current Special Rate: " + existingContract.getSpecialRate());
            System.out.print("Enter new Special Rate (or press Enter to keep current): ");
            String specialRateInput = scanner.nextLine();
            BigDecimal newSpecialRate = specialRateInput.isEmpty() ? existingContract.getSpecialRate() : new BigDecimal(specialRateInput);

            System.out.println("Current Agreement Conditions: " + existingContract.getAgreementConditions());
            System.out.print("Enter new Agreement Conditions (or press Enter to keep current): ");
            String agreementConditionsInput = scanner.nextLine();
            String newAgreementConditions = agreementConditionsInput.isEmpty() ? existingContract.getAgreementConditions() : agreementConditionsInput;

            System.out.println("Current Renewable: " + (existingContract.isRenewable() ? "Yes" : "No"));
            System.out.print("Is it Renewable? (Yes/No) (or press Enter to keep current): ");
            String renewableInput = scanner.nextLine();
            boolean newRenewable = renewableInput.isEmpty() ? existingContract.isRenewable() : renewableInput.equalsIgnoreCase("Yes");

            System.out.println("Current Status: " + existingContract.getStatus());
            System.out.print("Enter new Status (ACTIVE/TERMINATED/SUSPENDED) (or press Enter to keep current): ");
            String statusInput = scanner.nextLine();
            ContractStatus newStatus = statusInput.isEmpty() ? existingContract.getStatus() : ContractStatus.valueOf(statusInput);

            // Create the updated contract object
            Contract updatedContract = new Contract();
            updatedContract.setPartnerId(newPartnerId);
            updatedContract.setStartDate(newStartDate);
            updatedContract.setEndDate(newEndDate);
            updatedContract.setSpecialRate(newSpecialRate);
            updatedContract.setAgreementConditions(newAgreementConditions);
            updatedContract.setRenewable(newRenewable);
            updatedContract.setStatus(newStatus);

            // Update the contract in the database
            boolean success = repository.updateContractById(contractId, updatedContract);
            if (success) {
                System.out.println("Contract updated successfully.");
            } else {
                System.out.println("Contract update failed.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Contract ID format.");
        }
    }

    public void deleteContractById() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Contract ID to delete: ");
        String contractIdInput = scanner.nextLine();

        try {
            UUID contractId = UUID.fromString(contractIdInput);

            System.out.print("Are you sure you want to delete this contract? (yes/no): ");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {
                boolean success = repository.deleteContractById(contractId);

                if (success) {
                    System.out.println("Contract deleted successfully.");
                } else {
                    System.out.println("Contract not found or could not be deleted.");
                }
            } else {
                System.out.println("Contract deletion canceled.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Contract ID format.");
        }
    }








}