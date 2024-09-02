package org.ecomoveV1.repositories;

import org.ecomoveV1.models.entities.Contract;

import java.util.List;

public interface ContactRepository {
    void addContract(Contract contract);
    List<String> findAllContractsWithCompanyName();

}
