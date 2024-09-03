package org.ecomoveV1.repositories.impl;

import org.ecomoveV1.config.DatabaseConnection;
import org.ecomoveV1.models.entities.PromotionalOffer;
import org.ecomoveV1.models.enums.OfferStatus;
import org.ecomoveV1.models.enums.ReductionType;
import org.ecomoveV1.repositories.PromotionalOfferRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

    public class PromotionalOfferRepositoryImpl implements PromotionalOfferRepository {

    private final Connection connection = DatabaseConnection.getInstance().getConnection();
    final String tableName = "promotional_offer";


    @Override
    public void addPromotion(PromotionalOffer offer) {


    }

        @Override
        public List<PromotionalOffer> getAllPromotionalOffers() {
            List<PromotionalOffer> offers = new ArrayList<>();
            String sql = "SELECT * FROM "+tableName;

            try (PreparedStatement pstmt = connection.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    PromotionalOffer offer = new PromotionalOffer();
                    offer.setId(UUID.fromString(rs.getString("id")));
                    offer.setContractId(UUID.fromString(rs.getString("contract_id")));
                    offer.setOfferName(rs.getString("offer_name"));
                    offer.setDescription(rs.getString("description"));
                    offer.setStartDate(rs.getDate("start_date").toLocalDate());
                    offer.setEndDate(rs.getDate("end_date").toLocalDate());
                    offer.setReductionType(ReductionType.valueOf(rs.getString("reduction_type")));
                    offer.setReduction_value(rs.getBigDecimal("reduction_value"));
                    offer.setConditions(rs.getString("conditions"));
                    offer.setStatus(OfferStatus.valueOf(rs.getString("status")));
                    offers.add(offer);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return offers;
        }


    }
