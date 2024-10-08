package org.ecomoveV1.presentations;

import org.ecomoveV1.models.entities.Partner;
import org.ecomoveV1.models.enums.PartnerStatus;
import org.ecomoveV1.models.enums.TransportType;
import org.ecomoveV1.repositories.PartnerRepository;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class PartnerUi {

    final private PartnerRepository repository;
    final private Scanner scanner = new Scanner(System.in);

    public PartnerUi(PartnerRepository repository) {
        this.repository = repository;
    }

    public void displayAllPartnerNames(){
        List<String> partnerNames = repository.findAllPartnerNames();
        if (partnerNames.isEmpty()){
            System.out.println("No partner found !");
        }else {
            System.out.println("#------------- All Partners -------------#");
            int count = 0;
            for (String name : partnerNames){
                System.out.println("Company Name "+ ++count+name);

            }
        }
    }

    public void addPartner() {
        System.out.println("#------------ Add New Partner : -------------#");

        String companyName = getCompanyNameInput();
        String commercialName = getCommercialNameInput();
        TransportType transportType = getTransportTypeInput();
        String geographicalZone = getGeographicalZoneInput();
        String specialConditions = getSpecialConditionsInput();
        PartnerStatus status = getStatusInput();

        Date creationDate = new Date();
        UUID id = UUID.randomUUID();

        Partner newPartner = new Partner(id, companyName, commercialName, transportType, geographicalZone, specialConditions, status, creationDate);
        repository.addPartner(newPartner);
        System.out.println("New partner added successfully!");
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

    public void displayPartnerByName() {
        System.out.print("Enter partner company name: ");
        String companyName = scanner.nextLine();
        Partner partner = repository.findPartnerByName(companyName);
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

        System.out.print("Enter ID of partner :");
        String partnerIdInput = scanner.nextLine();
        UUID partnerId;
        try {
            partnerId = UUID.fromString(partnerIdInput);

        } catch (IllegalArgumentException e){
            System.out.println("invalid uuid format !");
            return;

        }

        System.out.println("Enter New status ");
        String StatusInput = scanner.nextLine().trim().toUpperCase();

        PartnerStatus newStatus ;
        try {
            newStatus = PartnerStatus.valueOf(StatusInput);

        } catch (IllegalArgumentException e) {
            System.out.println("invalid status !");
            return;
        }

        repository.UpdatePartnerStatus(partnerId, newStatus);

    }

    public void deletePartner(){

        System.out.println("#-------------- Delete Partner  --------------# ");

        System.out.print("Enter ID of partner :");
        String partnerIdInput = scanner.nextLine();
        UUID partnerIdDel;
        try {
            partnerIdDel = UUID.fromString(partnerIdInput);

        } catch (IllegalArgumentException e){
            System.out.println("invalid uuid format !");
            return;

        }

        boolean isDeleted = repository.deletePartner(partnerIdDel);

        if (isDeleted) {
            System.out.println("Partner was deleted .");
        } else {
            System.out.println("Partner ID does not exist.");
        }

    }


    public void  updatePartner(){

        System.out.println("#-------------- Delete Partner  --------------# ");

        System.out.print("Enter ID of partner :");
        String partnerIdInput = scanner.nextLine();
        UUID partnerIdUp;
        try {
            partnerIdUp = UUID.fromString(partnerIdInput);

        } catch (IllegalArgumentException e){
            System.out.println("invalid uuid format !");
            return;

        }

        System.out.print("Enter company name: ");
        String companyName = scanner.nextLine();

        System.out.print("Enter commercial contact: ");
        String commercialContact = scanner.nextLine();

        System.out.print("Enter transport type (e.g., BUS, TRAIN): ");
        String transportTypeInput = scanner.nextLine();
        TransportType transportType;
        try {
            transportType = TransportType.valueOf(transportTypeInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid transport type.");
            return;
        }

        System.out.print("Enter geographical zone: ");
        String geographicalZone = scanner.nextLine();

        System.out.print("Enter special conditions: ");
        String specialConditions = scanner.nextLine();

        boolean success = repository.updatePartner(partnerIdUp, companyName, commercialContact, transportType, geographicalZone, specialConditions);
        if (success) {
            System.out.println("Partner details updated successfully.");
        } else {
            System.out.println("Failed to update partner details.");
        }

    }
}
