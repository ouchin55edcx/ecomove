package org.ecomoveV1.repositories.impl;

import org.ecomoveV1.config.DatabaseConnection;
import org.ecomoveV1.models.entities.Partner;
import org.ecomoveV1.repositories.PartnerRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartnerRepositoryImpl implements PartnerRepository {
    private final Connection connection = DatabaseConnection.getInstance().getConnection();
    final String tableName = "partner";


    @Override
    public List<String> findAllPartnerNames() {
        List<String> partnerNames = new ArrayList<>();
        String query = "SELECT company_name FROM "+tableName;

        try (Statement stmt = connection.createStatement()){
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()){
                partnerNames.add(resultSet.getString("company_name"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return partnerNames;
    }

    @Override
        public void addPartner(Partner partner) {
        String query = "INSERT INTO " + tableName + " (id, company_name, commercial_contact, transport_type, geographical_zone, special_conditions, status, creation_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = connection.prepareStatement(query)){
                pstmt.setObject(1, partner.getId());
                pstmt.setString(2, partner.getCompanyName());
                pstmt.setString(3, partner.getCommercialContact());
                pstmt.setString(4, partner.getTransportType().name()); // Enum name as a string
                pstmt.setString(5, partner.getGeographical_zone());
                pstmt.setString(6, partner.getSpecialConditions());
                pstmt.setString(7, partner.getStatus().name()); // Enum name as a string
                pstmt.setDate(8, new java.sql.Date(partner.getCreationDate().getTime()));
                pstmt.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
}
