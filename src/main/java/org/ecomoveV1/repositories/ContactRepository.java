package org.ecomoveV1.repositories;

import org.ecomoveV1.models.entities.Contract;

import java.util.List;
import java.util.UUID;

public interface ContactRepository {
    void addContract(Contract contract);
    List<String> findAllContractsWithCompanyName();
    List<Contract> findContractsByPartnerId(UUID partnerId);
    Contract getContractById(UUID contractId);



}
