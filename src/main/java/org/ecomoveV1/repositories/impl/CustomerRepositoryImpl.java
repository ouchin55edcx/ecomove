package org.ecomoveV1.repositories.impl;

import org.ecomoveV1.config.DatabaseConnection;
import org.ecomoveV1.models.entities.Customer;
import org.ecomoveV1.repositories.CustomerRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public Optional<Customer> findCustomerByEmail(String email) {
        String query = "SELECT * FROM " + tableName + " WHERE email = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(createCustomerFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Customer> findCustomerByName(String firstName, String lastName) {
        String query = "SELECT * FROM " + tableName + " WHERE first_name = ? AND last_name = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(createCustomerFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    private Customer createCustomerFromResultSet(ResultSet rs) throws SQLException {
        return new Customer(
                UUID.fromString(rs.getString("id")),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("phone_number")
        );
    }
}
