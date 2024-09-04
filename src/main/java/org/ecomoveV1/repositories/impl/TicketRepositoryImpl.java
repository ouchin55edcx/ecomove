package org.ecomoveV1.repositories.impl;

import org.ecomoveV1.config.DatabaseConnection;
import org.ecomoveV1.models.entities.Ticket;
import org.ecomoveV1.models.enums.TicketStatus;
import org.ecomoveV1.models.enums.TransportType;
import org.ecomoveV1.repositories.TicketRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketRepositoryImpl implements TicketRepository {

    private final Connection connection = DatabaseConnection.getInstance().getConnection();
    final String tableName = "ticket";

    @Override
    public void addTicket(Ticket ticket) {

        String query = "INSERT INTO "+tableName+" (id, contract_id, transport_type, purchase_price, sale_price, sale_date, status) " +
                "VALUES (?, ?, ?::transport_type, ?, ?, ?, ?::ticket_status)";

        boolean added = false;
        try(PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setObject(1, ticket.getTicketId());
            pstmt.setObject(2, ticket.getContractId());
            pstmt.setString(3, ticket.getTransportType().name());
            pstmt.setBigDecimal(4, ticket.getPurchasePrice());
            pstmt.setBigDecimal(5, ticket.getSalePrice());
            pstmt.setDate(6, java.sql.Date.valueOf(ticket.getSaleDate()));
            pstmt.setString(7, ticket.getTicketStatus().name());

            int affectedRows = pstmt.executeUpdate();
            added = (affectedRows > 0);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ticket> getAllticket() {
        List<Ticket> tickets = new ArrayList<>();

        String query = "SELECT * FROM " + tableName;
        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                Ticket ticket = new Ticket(
                        resultSet.getObject("id", UUID.class), // Assuming id is a UUID
                        resultSet.getObject("contract_id", UUID.class), // Assuming contract_id is a UUID
                        TransportType.valueOf(resultSet.getString("transport_type")), // Assuming TransportType is an enum
                        resultSet.getBigDecimal("purchase_price"),
                        resultSet.getBigDecimal("sale_price"),
                        resultSet.getDate("sale_date").toLocalDate(), // Convert java.sql.Date to java.time.LocalDate
                        TicketStatus.valueOf(resultSet.getString("status")) // Assuming TicketStatus is an enum
                );
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tickets;
    }

    @Override
    public void updateTicket(UUID id, Ticket updatedTicket) {
        String query = "UPDATE " + tableName + " SET contract_id = ?, transport_type = ?::transport_type, purchase_price = ?, sale_price = ?, sale_date = ?, status = ?::ticket_status WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setObject(1, updatedTicket.getContractId());
            pstmt.setString(2, updatedTicket.getTransportType().name());
            pstmt.setBigDecimal(3, updatedTicket.getPurchasePrice());
            pstmt.setBigDecimal(4, updatedTicket.getSalePrice());
            pstmt.setDate(5, java.sql.Date.valueOf(updatedTicket.getSaleDate()));
            pstmt.setString(6, updatedTicket.getTicketStatus().name());
            pstmt.setObject(7, id);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("No ticket found with ID: " + id);
            }
            System.out.println("Ticket updated successfully!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteTicket(UUID id) {
        String query = "DELETE FROM " + tableName + " WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setObject(1, id);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("No ticket found with ID: " + id);
            }
            System.out.println("Ticket deleted successfully!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
