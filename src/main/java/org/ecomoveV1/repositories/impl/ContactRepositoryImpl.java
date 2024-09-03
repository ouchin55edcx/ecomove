package org.ecomoveV1.repositories.impl;

import org.ecomoveV1.config.DatabaseConnection;
import org.ecomoveV1.models.entities.Contract;
import org.ecomoveV1.models.enums.ContractStatus;
import org.ecomoveV1.repositories.ContactRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Override
    public List<String> findAllContractsWithCompanyName() {
        List<String> contractDetails = new ArrayList<>();
        String query = "SELECT c.id, c.partner_id, c.start_date, c.end_date, c.special_rate, " +
                "c.agreement_conditions, c.renewable, c.status, p.company_name " +
                "FROM Contract c " +
                "JOIN Partner p ON c.partner_id = p.id";

        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                String contractInfo = String.format(
                        "Contract ID: %s, Partner ID: %s, Start Date: %s, End Date: %s, Special Rate: %s, " +
                                "Agreement Conditions: %s, Renewable: %b, Status: %s, Company Name: %s",
                        resultSet.getString("id"),
                        resultSet.getString("partner_id"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"),
                        resultSet.getBigDecimal("special_rate"),
                        resultSet.getString("agreement_conditions"),
                        resultSet.getBoolean("renewable"),
                        resultSet.getString("status"),
                        resultSet.getString("company_name")
                );
                contractDetails.add(contractInfo);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching contract details with company names", e);
        }
        return contractDetails;
    }

    public List<Contract> findContractsByPartnerId(UUID partnerId) {
        List<Contract> contracts = new ArrayList<>();
        String query = "SELECT * FROM Contract WHERE partner_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setObject(1, partnerId);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                Contract contract = new Contract();
                contract.setId(UUID.fromString(resultSet.getString("id")));
                contract.setPartnerId(UUID.fromString(resultSet.getString("partner_id")));
                contract.setStartDate(resultSet.getDate("start_date").toLocalDate());
                contract.setEndDate(resultSet.getDate("end_date").toLocalDate());
                contract.setSpecialRate(resultSet.getBigDecimal("special_rate"));
                contract.setAgreementConditions(resultSet.getString("agreement_conditions"));
                contract.setRenewable(resultSet.getBoolean("renewable"));
                contract.setStatus(ContractStatus.valueOf(resultSet.getString("status")));

                contracts.add(contract);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching contracts by partner ID", e);
        }

        return contracts;
    }

    @Override
    public Contract getContractById(UUID contractId) {
        String query = "SELECT * FROM Contract WHERE id = ?";
        Contract contract = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setObject(1, contractId);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                contract = new Contract();
                contract.setId(UUID.fromString(resultSet.getString("id")));
                contract.setPartnerId(UUID.fromString(resultSet.getString("partner_id")));
                contract.setStartDate(resultSet.getDate("start_date").toLocalDate());
                contract.setEndDate(resultSet.getDate("end_date").toLocalDate());
                contract.setSpecialRate(resultSet.getBigDecimal("special_rate"));
                contract.setAgreementConditions(resultSet.getString("agreement_conditions"));
                contract.setRenewable(resultSet.getBoolean("renewable"));
                contract.setStatus(ContractStatus.valueOf(resultSet.getString("status")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching contract by ID", e);
        }

        return contract;
    }

    @Override
    public boolean updateContractById(UUID contractId, Contract updatedContract) {
        String query = "UPDATE Contract SET partner_id = ?, start_date = ?, end_date = ?, special_rate = ?, agreement_conditions = ?, renewable = ?, status = ?::contract_status WHERE id = ?";
        boolean updated = false;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setObject(1, updatedContract.getPartnerId());
            pstmt.setDate(2, Date.valueOf(updatedContract.getStartDate()));
            pstmt.setDate(3, Date.valueOf(updatedContract.getEndDate()));
            pstmt.setBigDecimal(4, updatedContract.getSpecialRate());
            pstmt.setString(5, updatedContract.getAgreementConditions());
            pstmt.setBoolean(6, updatedContract.isRenewable());
            pstmt.setString(7, updatedContract.getStatus().name()); // No change needed here
            pstmt.setObject(8, contractId);

            int affectedRows = pstmt.executeUpdate();
            updated = (affectedRows > 0);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating contract by ID", e);
        }

        return updated;
    }

    @Override
    public boolean deleteContractById(UUID contractId) {
        String query = "DELETE FROM Contract WHERE id = ?";
        boolean deleted = false;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setObject(1, contractId);

            int affectedRows = pstmt.executeUpdate();
            deleted = (affectedRows > 0);
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting contract by ID", e);
        }

        return deleted;
    }



}
