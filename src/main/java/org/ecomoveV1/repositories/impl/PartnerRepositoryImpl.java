package org.ecomoveV1.repositories.impl;

import org.ecomoveV1.config.DatabaseConnection;
import org.ecomoveV1.models.entities.Partner;
import org.ecomoveV1.models.enums.PartnerStatus;
import org.ecomoveV1.models.enums.TransportType;
import org.ecomoveV1.repositories.PartnerRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Override
    public Partner findPartnerByName(String companyName) {
        String query = "SElECT * FROM "+tableName+" WHERE company_name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setString(1,companyName);
            ResultSet resultSet = pstmt.executeQuery();
            if(resultSet.next()){
                return new Partner(
                    UUID.fromString(resultSet.getString("id")),
                    resultSet.getString("company_name"),
                    resultSet.getString("commercial_contact"),  // Changed from commercial_contact
                    TransportType.valueOf(resultSet.getString("transport_type")),
                    resultSet.getString("geographical_zone"),
                    resultSet.getString("special_conditions"),
                    PartnerStatus.valueOf(resultSet.getString("status")),
                    resultSet.getDate("creation_date")
                );
            }

        } catch (SQLException e) {
             throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void UpdatePartnerStatus(UUID partnerId, PartnerStatus newStatus) {

        String query = "UPDATE "+tableName+" SET status = ? WHERE id = id::uuid";

        try (PreparedStatement pstmt = connection.prepareStatement(query)){

            pstmt.setString(1, newStatus.name());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deletePartner(UUID partnerId) {

        String query = "DELETE FROM "+tableName+" WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)){

            pstmt.setObject(1, partnerId);
            int rowsCount =  pstmt.executeUpdate();
            return rowsCount >0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updatePartner(UUID partnerId, String companyName, String commercialContact, TransportType transportType, String geographicalZone, String specialConditions) {

        String query = "UPDATE " + tableName + " SET company_name = ?, commercial_contact = ?, transport_type = ?, geographical_zone = ?, special_conditions = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, companyName);
            pstmt.setString(2, commercialContact);
            pstmt.setString(3, transportType.name());
            pstmt.setString(4, geographicalZone);
            pstmt.setString(5, specialConditions);
            pstmt.setObject(6, partnerId); // Set partnerId as the last parameter

            pstmt.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException("Failed to update partner details", e);
        }
        return false;
    }


}
