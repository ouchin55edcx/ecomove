package org.ecomoveV1.presentations;

import org.ecomoveV1.models.entities.Partner;
import org.ecomoveV1.models.enums.PartnerStatus;
import org.ecomoveV1.models.enums.TransportType;
import org.ecomoveV1.repositories.PartnerRepository;
import org.ecomoveV1.services.PartnerService;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class PartnerUi {

    final private PartnerService partnerService ;
    final private Scanner scanner = new Scanner(System.in);

    public PartnerUi(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    //method refactored
    public void displayAllPartnerNames() {
        List<String> partnerNames = partnerService.getAllPartnerNames();
        if (partnerNames.isEmpty()) {
            System.out.println("No partner found!");
        } else {
            System.out.println("#------------- All Partners -------------#");
            int count = 0;
            for (String name : partnerNames) {
                System.out.println("Company Name " + (++count) + ": " + name);
            }
        }
    }

    //method refactored
    public void addPartner() {
        System.out.println("#------------ Add New Partner : -------------#");

        String companyName = getCompanyNameInput();
        String commercialName = getCommercialNameInput();
        TransportType transportType = getTransportTypeInput();
        String geographicalZone = getGeographicalZoneInput();
        String specialConditions = getSpecialConditionsInput();
        PartnerStatus status = getStatusInput();

        partnerService.addPartner(companyName, commercialName, transportType,
                geographicalZone, specialConditions, status);
        System.out.println("New partner added successfully!");
    }


    public void displayPartnerByName() {
        System.out.print("Enter partner company name: ");
        String companyName = scanner.nextLine();
        Partner partner = partnerService.findPartnerByName(companyName);
        if (partner != null) {
            System.out.println("Partner found:");
            System.out.println("ID: " + partner.getId());
            System.out.println("Company Name: " + partner.getCompanyName());
            System.out.println("Commercial Name: " + partner.getCommercialContact());
            System.out.println("Transport Type: " + partner.getTransportType());
            System.out.println("Geographical Zone: " + partner.getGeographical_zone());
            System.out.println("Special Conditions: " + partner.getSpecialConditions());
            System.out.println("Status: " + partner.getStatus());
            System.out.println("Creation Date: " + partner.getCreationDate());
        } else {
            System.out.println("No partner found with company name: " + companyName);
        }
    }


    public void UpdatePartnerStatus(){

        System.out.println("#-------------- Update Partner Status --------------# ");

        UUID partnerId = getPartnerIdInput();
        PartnerStatus newStatus = getPartnerStatusInput();

        partnerService.updatePartnerStatus(partnerId, newStatus);
        System.out.println("Partner status updated successfully.");

    }



    public void deletePartner(){

        System.out.println("#-------------- Delete Partner  --------------# ");

        UUID partnerIdDel = getPartnerIdInput();

        boolean isDeleted = partnerService.deletePartner(partnerIdDel);

        if (isDeleted) {
            System.out.println("Partner was deleted .");
        } else {
            System.out.println("Partner ID does not exist.");
        }

    }


    public void updatePartner() {
        System.out.println("#-------------- Update Partner  --------------# ");

        UUID partnerIdUp = getPartnerIdInput();

        System.out.print("Enter company name: ");
        String companyName = scanner.nextLine();

        System.out.print("Enter commercial contact: ");
        String commercialContact = scanner.nextLine();

        TransportType transportType = getTransportTypeInput();
        if (transportType == null) return;

        System.out.print("Enter geographical zone: ");
        String geographicalZone = scanner.nextLine();

        System.out.print("Enter special conditions: ");
        String specialConditions = scanner.nextLine();

        try {
            partnerService.updatePartner(partnerIdUp, companyName, commercialContact,
                    transportType, geographicalZone, specialConditions);
            System.out.println("updated");
            } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    private UUID getPartnerIdInput(){
        while (true){
            System.out.println("Enter ID of partner: ");
            String partnerIdInput = scanner.nextLine();
            try {
                return UUID.fromString(partnerIdInput);
            }catch (IllegalArgumentException e){
                System.out.println("Invalid UUID format!");
            }
        }
    }

    private PartnerStatus getPartnerStatusInput(){
        while (true){
            System.out.println("Enter New status : ");
            String statusInput = scanner.nextLine().trim().toUpperCase();
            try {
                return PartnerStatus.valueOf(statusInput);
            }catch (IllegalArgumentException e){
                System.out.println("Invalid status! ");
            }
        }
    }

    private String getCompanyNameInput() {
        while (true) {
            System.out.print("Enter Company Name: ");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Company name cannot be empty. Please try again.");
        }
    }

    private String getCommercialNameInput() {
        while (true) {
            System.out.print("Enter Commercial Contact: ");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Commercial contact cannot be empty. Please try again.");
        }
    }

    private TransportType getTransportTypeInput() {
        while (true) {
            System.out.print("Enter Transport Type (e.g., 'PLANE', 'TRAIN', 'BUS'): ");
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                return TransportType.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid transport type. Please try again.");
            }
        }
    }

    private String getGeographicalZoneInput() {
        while (true) {
            System.out.print("Enter Geographical Zone: ");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Geographical zone cannot be empty. Please try again.");
        }
    }

    private String getSpecialConditionsInput() {
        System.out.print("Enter Special Conditions (or press Enter if none): ");
        return scanner.nextLine().trim();
    }

    private PartnerStatus getStatusInput() {
        while (true) {
            System.out.print("Enter Status ('ACTIVE' or 'INACTIVE') or press Enter for default (ACTIVE): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.isEmpty()) {
                return PartnerStatus.ACTIVE;
            }
            try {
                return PartnerStatus.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status. Please enter 'ACTIVE' or 'INACTIVE'.");
            }
        }
    }


}
