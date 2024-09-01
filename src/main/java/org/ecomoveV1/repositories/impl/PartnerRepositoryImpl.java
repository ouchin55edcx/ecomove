package org.ecomoveV1.repositories.impl;

import org.ecomoveV1.config.DatabaseConnection;
import org.ecomoveV1.repositories.PartnerRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
}
