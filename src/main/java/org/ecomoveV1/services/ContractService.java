package org.ecomoveV1.services;

import org.ecomoveV1.models.entities.Contract;
import org.ecomoveV1.models.enums.ContractStatus;
import org.ecomoveV1.repositories.ContactRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ContractService {

    final private ContactRepository contactRepository;

    public ContractService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public void addContract(UUID partnerId, LocalDate startDate, LocalDate endDate, BigDecimal specialRate, String agreementConditions, boolean renewable){
        UUID id = UUID.randomUUID();
        ContractStatus status = ContractStatus.ACTIVE;
        Contract newContract = new Contract(id, partnerId, startDate, endDate, specialRate, agreementConditions, renewable, status);
        contactRepository.addContract(newContract);
    }

    public List<String> getAllContractsWithCompanyName(){
        return contactRepository.findAllContractsWithCompanyName();
    }

    public List<Contract> findContractsByPartnerId(UUID partnerId) {
        return contactRepository.findContractsByPartnerId(partnerId);
    }

    public Contract getContractById(UUID contractId) {
        return  contactRepository.getContractById(contractId);
    }

    public boolean updateContractById(UUID contractId, UUID partnerId, LocalDate startDate, LocalDate endDate,
                                  BigDecimal specialRate, String agreementConditions, boolean renewable, ContractStatus status) {
        Contract updatedContract = new Contract(contractId, partnerId, startDate, endDate, specialRate, agreementConditions, renewable, status);
        return contactRepository.updateContractById(contractId, updatedContract);
    }

    public boolean deleteContractById(UUID contractId) {
        return contactRepository.deleteContractById(contractId);
    }


}
