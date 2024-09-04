package org.ecomoveV1.repositories;

import org.ecomoveV1.models.entities.Ticket;

import java.util.List;
import java.util.UUID;

public interface TicketRepository {
    void addTicket(Ticket ticket);
    List<Ticket> getAllticket();
    void updateTicket(UUID id, Ticket updatedTicket);
    void deleteTicket(UUID id);

}
