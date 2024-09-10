package org.ecomoveV1.repositories.impl;

import org.ecomoveV1.config.DatabaseConnection;
import org.ecomoveV1.models.entities.PromotionalOffer;
import org.ecomoveV1.models.entities.Ticket;
import org.ecomoveV1.models.enums.OfferStatus;
import org.ecomoveV1.models.enums.ReductionType;
import org.ecomoveV1.models.enums.TicketStatus;
import org.ecomoveV1.models.enums.TransportType;
import org.ecomoveV1.repositories.PromotionalOfferRepository;
import org.ecomoveV1.repositories.TicketRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketRepositoryImpl implements TicketRepository {

    private final Connection connection = DatabaseConnection.getInstance().getConnection();
    final String tableName = "ticket";





    public void addTicket(Ticket ticket) {
        // Check for active promotion
        PromotionalOffer offer = getActiveOfferByContractId(ticket.getContractId());

        if (offer != null) {
            // Apply promotion to sale price
            BigDecimal discountedPrice = applyPromotion(ticket.getSalePrice(), offer);
            ticket.setSalePrice(discountedPrice);
        }

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
            throw new RuntimeException("Error adding ticket", e);
        }
    }

    private BigDecimal applyPromotion(BigDecimal originalPrice, PromotionalOffer offer) {
        if (offer.getReductionType() == ReductionType.PERCENTAGE) {
            BigDecimal discountFactor = BigDecimal.ONE.subtract(offer.getReduction_value().divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP));
            return originalPrice.multiply(discountFactor).setScale(2, RoundingMode.HALF_UP);
        } else if (offer.getReductionType() == ReductionType.FIXED_AMOUNT) {
            return originalPrice.subtract(offer.getReduction_value()).max(BigDecimal.ZERO);
        }
        return originalPrice;
    }

    private PromotionalOffer getActiveOfferByContractId(UUID contractId) {
        String query = "SELECT * FROM promotional_offer WHERE contract_id = ? AND status = 'ACTIVE' " +
                "AND CURRENT_DATE BETWEEN start_date AND end_date";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setObject(1, contractId);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    return new PromotionalOffer(
                            UUID.fromString(resultSet.getString("id")),
                            UUID.fromString(resultSet.getString("contract_id")),
                            resultSet.getString("offer_name"),
                            resultSet.getString("description"),
                            resultSet.getDate("start_date").toLocalDate(),
                            resultSet.getDate("end_date").toLocalDate(),
                            ReductionType.valueOf(resultSet.getString("reduction_type")),
                            resultSet.getBigDecimal("reduction_value"),
                            resultSet.getString("conditions"),
                            OfferStatus.valueOf(resultSet.getString("status"))
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving promotional offer", e);
        }
        return null;
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
