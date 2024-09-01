package org.ecomoveV1.presentations;

import org.ecomoveV1.repositories.PartnerRepository;

import java.util.List;

public class PartnerUi {

    final private PartnerRepository repository;

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
}
