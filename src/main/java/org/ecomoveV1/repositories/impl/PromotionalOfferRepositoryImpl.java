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
import java.util.Comparator;
import java.util.List;
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

        @Override
        public PromotionalOffer getPromotionalOfferById(UUID id) {
            String sql = "SELECT * FROM "+tableName+" WHERE id = ?";

            try (PreparedStatement pstmt = connection.prepareStatement(sql))
                  {

                pstmt.setObject(1, id);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
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
                        return offer;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null; // Return null if no offer is found with the given id
        }

        @Override
        public void updatePromotionalOffer(PromotionalOffer offer) {
            String sql = "UPDATE "+tableName+" SET contract_id = ?, offer_name = ?, description = ?, " +
                    "start_date = ?, end_date = ?, reduction_type = ?::reduction_type, reduction_value = ?, " +
                    "conditions = ?, status = ?::offer_status WHERE id = ?";

            try (PreparedStatement pstmt = connection.prepareStatement(sql))
                 {

                pstmt.setObject(1, offer.getContractId());
                pstmt.setString(2, offer.getOfferName());
                pstmt.setString(3, offer.getDescription());
                pstmt.setDate(4, Date.valueOf(offer.getStartDate()));
                pstmt.setDate(5, Date.valueOf(offer.getEndDate()));
                pstmt.setString(6, offer.getReductionType().name());
                pstmt.setBigDecimal(7, offer.getReduction_value());
                pstmt.setString(8, offer.getConditions());
                pstmt.setString(9, offer.getStatus().name());
                pstmt.setObject(10, offer.getId());

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Updating promotional offer failed, no rows affected.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void deletePromotionalOffer(UUID id) {
            String sql = "DELETE FROM "+tableName+" WHERE id = ?";

            try (PreparedStatement pstmt = connection.prepareStatement(sql))
            {

                pstmt.setObject(1, id);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Deleting promotional offer failed, no rows affected.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
