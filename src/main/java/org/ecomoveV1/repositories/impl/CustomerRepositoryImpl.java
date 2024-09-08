package org.ecomoveV1.repositories.impl;

import org.ecomoveV1.config.DatabaseConnection;
import org.ecomoveV1.models.entities.Customer;
import org.ecomoveV1.repositories.CustomerRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerRepositoryImpl implements CustomerRepository {

    private final Connection connection = DatabaseConnection.getInstance().getConnection();
    final String tableName = "customer";


    @Override
    public void addCustomer(Customer customer) {
        String query = "INSERT INTO "+tableName+"(id, first_name, last_name, email, phone_number) VALUES(?,?,?,?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)){
            pstmt.setObject(1, customer.getId());
            pstmt.setString(2, customer.getFirstName());
            pstmt.setString(3, customer.getLastName());
            pstmt.setString(4, customer.getEmail());
            pstmt.setString(5, customer.getPhoneNumber());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
