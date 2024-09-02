package org.ecomoveV1.repositories;

import org.ecomoveV1.models.entities.Partner;
import org.ecomoveV1.models.enums.PartnerStatus;

import java.util.List;
import java.util.UUID;

public interface PartnerRepository {
    List<String> findAllPartnerNames();
    void addPartner(Partner partner);
    Partner findPartnerByName(String companyName);
    void UpdatePartnerStatus(UUID partnerId, PartnerStatus newStatus);
}
