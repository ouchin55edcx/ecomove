package org.ecomoveV1.models.entities;

import org.ecomoveV1.models.enums.TicketStatus;
import org.ecomoveV1.models.enums.TransportType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Ticket {

    private UUID ticketId ;
    private UUID contractId ;
    private UUID journeyId ;
    private TransportType transportType ;
    private BigDecimal purchasePrice;
    private BigDecimal salePrice ;
    private LocalDate saleDate ;
    private TicketStatus ticketStatus ;

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", contractId=" + contractId +
                ", journeyId=" + journeyId +
                ", transportType=" + transportType +
                ", purchasePrice=" + purchasePrice +
                ", salePrice=" + salePrice +
                ", saleDate=" + saleDate +
                ", ticketStatus=" + ticketStatus +
                '}';
    }



    public UUID getTicketId() {
        return ticketId;
    }

    public void setTicketId(UUID ticketId) {
        this.ticketId = ticketId;
    }

    public UUID getContractId() {
        return contractId;
    }

    public void setContractId(UUID contractId) {
        this.contractId = contractId;
    }

    public UUID getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(UUID journeyId) {
        this.journeyId = journeyId;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }



    public Ticket(UUID ticketId, UUID contractId, UUID journeyId, TransportType transportType, BigDecimal purchasePrice, BigDecimal salePrice, LocalDate saleDate, TicketStatus ticketStatus) {
        this.ticketId = ticketId;
        this.contractId = contractId;
        this.journeyId = journeyId;
        this.transportType = transportType;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.saleDate = saleDate;
        this.ticketStatus = ticketStatus;
    }







}
