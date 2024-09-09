package org.ecomoveV1.models.entities;

import org.ecomoveV1.models.enums.PartnerStatus;
import org.ecomoveV1.models.enums.TransportType;

import java.util.Date;
import java.util.UUID;

public class Partner {

    private UUID id ;
    private String companyName;
    private String commercialContact;
    private TransportType transportType;
    private String geographical_zone;
    private String specialConditions;
    private PartnerStatus status;
    private Date creationDate;



    public Partner(UUID id, String companyName, String commercialContact, TransportType transportType, String geographical_zone, String specialConditions, PartnerStatus status, Date creationDate) {
        this.id = id;
        this.companyName = companyName;
        this.commercialContact = commercialContact;
        this.transportType = transportType;
        this.geographical_zone = geographical_zone;
        this.specialConditions = specialConditions;
        this.status = status;
        this.creationDate = creationDate;
    }


    public Partner() {
    }



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCommercialContact() {
        return commercialContact;
    }

    public void setCommercialContact(String commercialContact) {
        this.commercialContact = commercialContact;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public String getGeographical_zone() {
        return geographical_zone;
    }

    public void setGeographical_zone(String geographical_zone) {
        this.geographical_zone = geographical_zone;
    }

    public String getSpecialConditions() {
        return specialConditions;
    }

    public void setSpecialConditions(String specialConditions) {
        this.specialConditions = specialConditions;
    }

    public PartnerStatus getStatus() {
        return status;
    }

    public void setStatus(PartnerStatus status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Partner{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", commercialContact='" + commercialContact + '\'' +
                ", transportType=" + transportType +
                ", geographical_zone='" + geographical_zone + '\'' +
                ", specialConditions='" + specialConditions + '\'' +
                ", status=" + status +
                ", creationDate=" + creationDate +
                '}';
    }




}
