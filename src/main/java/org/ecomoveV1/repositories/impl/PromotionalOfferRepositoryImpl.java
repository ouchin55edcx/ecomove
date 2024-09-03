package org.ecomoveV1.repositories.impl;

import org.ecomoveV1.config.DatabaseConnection;
import org.ecomoveV1.models.entities.PromotionalOffer;
import org.ecomoveV1.repositories.PromotionalOfferRepository;

import java.sql.*;
import java.util.UUID;

public class PromotionalOfferRepositoryImpl implements PromotionalOfferRepository {

    private final Connection connection = DatabaseConnection.getInstance().getConnection();
    final String tableName = "promotional_offer";


    @Override
    public void addPromotion(PromotionalOffer offer) {
        String query = "INSERT INTO "+tableName+" (id, contract_id, offer_name, description, start_date, end_date, reduction_type, reduction_value, conditions, status) VALUES (?, ?, ?, ?, ?, ?, ?::reduction_type, ?, ?, ?::offer_status)";
        boolean added = false;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setObject(1, offer.getId());
            pstmt.setObject(2, offer.getContractId());
            pstmt.setString(3, offer.getOfferName());
            pstmt.setString(4, offer.getDescription());
            pstmt.setDate(5, java.sql.Date.valueOf(offer.getStartDate()));
            pstmt.setDate(6, java.sql.Date.valueOf(offer.getEndDate()));
            pstmt.setString(7, offer.getReductionType().name());
            pstmt.setBigDecimal(8, offer.getReduction_value());
            pstmt.setString(9, offer.getConditions());
            pstmt.setString(10, offer.getStatus().name());

            int affectedRows = pstmt.executeUpdate();
            added = (affectedRows > 0);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }




}
