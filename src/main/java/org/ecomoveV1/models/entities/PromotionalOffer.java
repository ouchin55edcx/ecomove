package org.ecomoveV1.models.entities;

import org.ecomoveV1.models.enums.OfferStatus;
import org.ecomoveV1.models.enums.ReductionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class PromotionalOffer {
    private UUID id ;
    private String offerName;
    private String description;
    private LocalDate startDate ;
    private  LocalDate endDate;
    private ReductionType reductionType;
    private BigDecimal reduction_value;
    private String conditions;
    private OfferStatus status ;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getContractId() {
        return contractId;
    }

    public void setContractId(UUID contractId) {
        this.contractId = contractId;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public ReductionType getReductionType() {
        return reductionType;
    }

    public void setReductionType(ReductionType reductionType) {
        this.reductionType = reductionType;
    }

    public BigDecimal getReduction_value() {
        return reduction_value;
    }

    public void setReduction_value(BigDecimal reduction_value) {
        this.reduction_value = reduction_value;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public OfferStatus getStatus() {
        return status;
    }

    public void setStatus(OfferStatus status) {
        this.status = status;
    }


    public PromotionalOffer(UUID id, UUID contractId, String offerName, String description, LocalDate startDate, LocalDate endDate, ReductionType reductionType, BigDecimal reduction_value, String conditions, OfferStatus status) {
        this.id = id;
        this.contractId = contractId;
        this.offerName = offerName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reductionType = reductionType;
        this.reduction_value = reduction_value;
        this.conditions = conditions;
        this.status = status;
    }

    public PromotionalOffer() {
    }

    private UUID contractId;

    @Override
    public String toString() {
        return "PromotionalOffer{" +
                "id=" + id +
                ", contractId=" + contractId +
                ", offerName='" + offerName + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", reductionType=" + reductionType +
                ", reduction_value=" + reduction_value +
                ", conditions='" + conditions + '\'' +
                ", status=" + status +
                '}';
    }


}

