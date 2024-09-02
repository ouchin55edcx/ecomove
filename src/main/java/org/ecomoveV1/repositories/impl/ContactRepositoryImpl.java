package org.ecomoveV1.repositories.impl;

import org.ecomoveV1.config.DatabaseConnection;
import org.ecomoveV1.models.entities.Contract;
import org.ecomoveV1.repositories.ContactRepository;

import java.sql.*;

public class ContactRepositoryImpl implements ContactRepository {

    private final Connection connection = DatabaseConnection.getInstance().getConnection();
    final String tableName = "contract";


    @Override
    public void addContract(Contract contract) {
        String query = "INSERT INTO " + tableName + " (id, partner_id, start_date, end_date, special_rate, agreement_conditions, renewable, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?::contract_status)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setObject(1, contract.getId());
            pstmt.setObject(2, contract.getPartnerId());
            pstmt.setDate(3, java.sql.Date.valueOf(contract.getStartDate()));
            pstmt.setDate(4, java.sql.Date.valueOf(contract.getEndDate()));
            pstmt.setBigDecimal(5, contract.getSpecialRate());
            pstmt.setString(6, contract.getAgreementConditions());
            pstmt.setBoolean(7, contract.isRenewable());
            pstmt.setObject(8, contract.getStatus().name(), Types.OTHER);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
