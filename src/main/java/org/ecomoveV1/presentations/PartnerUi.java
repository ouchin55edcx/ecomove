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

    public  void addPartner(){

        System.out.println("#------------ Add New Partner : -------------#");

        System.out.println("Enter Company Name : ");
        String companyName = scanner.nextLine();

        System.out.print("Enter Commercial Contact: ");
        String commercialName = scanner.nextLine();

        System.out.print("Enter Transport Type (e.g., 'PLANE', 'TRAIN', 'BUS'): ");
        String transportTypeInput = scanner.nextLine().trim().toUpperCase();
        TransportType transportType = TransportType.valueOf(transportTypeInput);

        System.out.print("Enter Geographical Zone: ");
        String geographicalZone = scanner.nextLine();

        System.out.print("Enter Special Conditions: ");
        String specialConditions = scanner.nextLine();

        System.out.print("Enter Status (ex: 'ACTIVE', 'INACTIVE') or press Enter for default (ACTIVE): ");
        String statusInput = scanner.nextLine().trim().toUpperCase();

        PartnerStatus status;

        if(statusInput.isEmpty()){
            status = PartnerStatus.ACTIVE;
        }else{
            status = PartnerStatus.valueOf(statusInput);
        }

        Date creationDate = new Date();
        UUID id =  UUID.randomUUID();

        Partner newPartner = new Partner(id, companyName, commercialName, transportType, geographicalZone, specialConditions, status, creationDate);
        repository.addPartner(newPartner);
        System.out.println("New partner added successfully!");



    }
}
