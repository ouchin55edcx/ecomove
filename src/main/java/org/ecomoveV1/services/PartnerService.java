package org.ecomoveV1.services;

import org.ecomoveV1.models.entities.Partner;
import org.ecomoveV1.models.enums.PartnerStatus;
import org.ecomoveV1.models.enums.TransportType;
import org.ecomoveV1.repositories.PartnerRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PartnerService {

    final private PartnerRepository partnerRepository;


    public PartnerService(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public List<String> getAllPartnerNames(){
            return partnerRepository.findAllPartnerNames();
    }

    public void addPartner(String companyName, String commercialContact, TransportType transportType,
                              String geographicalZone, String specialConditions, PartnerStatus status){
        UUID id = UUID.randomUUID();
        Date creationDate = new Date();
        Partner newPartner = new Partner(id, companyName, commercialContact, transportType,
                geographicalZone, specialConditions, status, creationDate);
        partnerRepository.addPartner(newPartner);
    }

    public Partner findPartnerByName(String companyName){
        return partnerRepository.findPartnerByName(companyName);
    }

    public void updatePartnerStatus(UUID partnerId , PartnerStatus newStatus){
        partnerRepository.UpdatePartnerStatus(partnerId, newStatus);
    }

    public boolean deletePartner(UUID partnerId){
        return  partnerRepository.deletePartner(partnerId);
    }

    public boolean updatePartner(UUID partnerId, String companyName, String commercialContact,
                                 TransportType transportType, String geographicalZone,
                                 String specialConditions){
        return partnerRepository.updatePartner(partnerId, companyName, commercialContact, transportType, geographicalZone, specialConditions);

    }
}
