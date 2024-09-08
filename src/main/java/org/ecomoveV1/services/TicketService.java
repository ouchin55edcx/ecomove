package org.ecomoveV1.services;

import org.ecomoveV1.models.entities.Ticket;
import org.ecomoveV1.models.enums.TicketStatus;
import org.ecomoveV1.models.enums.TransportType;
import org.ecomoveV1.repositories.TicketRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public void addTicket(UUID contractId, UUID journeyId,  TransportType transportType, BigDecimal purchasePrice,
                          BigDecimal salePrice, LocalDate saleDate, TicketStatus ticketStatus) {
        Ticket newTicket = new Ticket(
                UUID.randomUUID(),
                journeyId,
                contractId,
                transportType,
                purchasePrice,
                salePrice,
                saleDate,
                ticketStatus
        );
        ticketRepository.addTicket(newTicket);
    }


    public List<Ticket> getAlltickets() {
        return ticketRepository.getAllticket();
    }

    public void updateTicket(UUID id, UUID contractId,UUID journeyId, TransportType transportType, BigDecimal purchasePrice,
                             BigDecimal salePrice, LocalDate saleDate, TicketStatus ticketStatus) {
        Ticket updatedTicket = new Ticket(
                id,
                contractId,
                journeyId,
                transportType,
                purchasePrice,
                salePrice,
                saleDate,
                ticketStatus
        );
        ticketRepository.updateTicket(id, updatedTicket);
    }


    public void deleteTicket(UUID id) {
        ticketRepository.deleteTicket(id);
    }

    public Ticket getTicketById(UUID id) {
        return ticketRepository.getTicketById(id);
    }
}
