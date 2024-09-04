package org.ecomoveV1.repositories;

import org.ecomoveV1.models.entities.Ticket;

import java.util.List;

public interface TicketRepository {
    void addTicket(Ticket ticket);
List<Ticket> getAllticket();
}
