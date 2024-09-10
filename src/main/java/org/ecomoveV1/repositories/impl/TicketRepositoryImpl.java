package org.ecomoveV1.repositories.impl;

import org.ecomoveV1.config.DatabaseConnection;
import org.ecomoveV1.models.entities.Ticket;
import org.ecomoveV1.models.enums.TicketStatus;
import org.ecomoveV1.models.enums.TransportType;
import org.ecomoveV1.repositories.TicketRepository;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class TicketRepositoryImpl implements TicketRepository {

    private final Connection connection = DatabaseConnection.getInstance().getConnection();
    final String tableName = "ticket";

    @Override
    public void addTicket(Ticket ticket) {
        String query = "INSERT INTO " + tableName + " (id, contract_id, journey_id, transport_type, purchase_price, sale_price, sale_date, status) " +
                "VALUES (?, ?, ?, ?::transport_type, ?, ?, ?, ?::ticket_status)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setObject(1, ticket.getTicketId());
            pstmt.setObject(2, ticket.getContractId());
            pstmt.setObject(3, ticket.getJourneyId());
            pstmt.setString(4, ticket.getTransportType().name());
            pstmt.setBigDecimal(5, ticket.getPurchasePrice());
            pstmt.setBigDecimal(6, ticket.getSalePrice());
            pstmt.setDate(7, java.sql.Date.valueOf(ticket.getSaleDate()));
            pstmt.setString(8, ticket.getTicketStatus().name());
            pstmt.executeUpdate();
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
                        resultSet.getObject("id", UUID.class),
                        resultSet.getObject("contract_id", UUID.class),
                        resultSet.getObject("journey_id", UUID.class),
                        TransportType.valueOf(resultSet.getString("transport_type")),
                        resultSet.getBigDecimal("purchase_price"),
                        resultSet.getBigDecimal("sale_price"),
                        resultSet.getDate("sale_date").toLocalDate(),
                        TicketStatus.valueOf(resultSet.getString("status"))
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


    @Override
    public Ticket getTicketById(UUID id) {
        String query = "SELECT * FROM " + tableName + " WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setObject(1, id);

            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return new Ticket(
                        resultSet.getObject("id", UUID.class),
                        resultSet.getObject("contract_id", UUID.class),
                        resultSet.getObject("journey_id", UUID.class),
                        TransportType.valueOf(resultSet.getString("transport_type")),
                        resultSet.getBigDecimal("purchase_price"),
                        resultSet.getBigDecimal("sale_price"),
                        resultSet.getDate("sale_date").toLocalDate(),
                        TicketStatus.valueOf(resultSet.getString("status"))
                );
            } else {
                throw new RuntimeException("No ticket found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ticket> getTicketsByJourneyId(UUID id) {
        final List<Ticket > tickets = new ArrayList<>();
        final String query = "SELECT * FROM "+tableName+ " WHERE journey_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setObject(1, id);

            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Ticket ticket = new Ticket(
                        resultSet.getObject("id", UUID.class),
                        resultSet.getObject("contract_id", UUID.class),
                        resultSet.getObject("journey_id", UUID.class),
                        TransportType.valueOf(resultSet.getString("transport_type")),
                        resultSet.getBigDecimal("purchase_price"),
                        resultSet.getBigDecimal("sale_price"),
                        resultSet.getDate("sale_date").toLocalDate(),
                        TicketStatus.valueOf(resultSet.getString("status"))
                );
                tickets.add(ticket);
            }
            return tickets;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
