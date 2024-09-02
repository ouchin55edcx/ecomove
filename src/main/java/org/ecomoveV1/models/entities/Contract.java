package org.ecomoveV1.models.entities;

import org.ecomoveV1.models.enums.ContractStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class Contract {

    private UUID id;
    private UUID partnerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal specialRate;
    private String agreementConditions;
    private boolean renewable;
    private ContractStatus status;

    public Contract(UUID id, UUID partnerId, LocalDate startDate, LocalDate endDate, BigDecimal specialRate, String agreementConditions, boolean renewable, ContractStatus status) {
        this.id = id;
        this.partnerId = partnerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.specialRate = specialRate;
        this.agreementConditions = agreementConditions;
        this.renewable = renewable;
        this.status = status;
    }

    public Contract() {
    }




    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(UUID partnerId) {
        this.partnerId = partnerId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public BigDecimal getSpecialRate() {
        return specialRate;
    }

    public void setSpecialRate(BigDecimal specialRate) {
        this.specialRate = specialRate;
    }

    public String getAgreementConditions() {
        return agreementConditions;
    }

    public void setAgreementConditions(String agreementConditions) {
        this.agreementConditions = agreementConditions;
    }

    public boolean isRenewable() {
        return renewable;
    }

    public void setRenewable(boolean renewable) {
        this.renewable = renewable;
    }

    public ContractStatus getStatus() {
        return status;
    }

    public void setStatus(ContractStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", partnerId=" + partnerId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", specialRate=" + specialRate +
                ", agreementConditions='" + agreementConditions + '\'' +
                ", renewable=" + renewable +
                ", status=" + status +
                '}';
    }



}
