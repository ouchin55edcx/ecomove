package org.ecomoveV1.repositories.impl;

import org.ecomoveV1.config.DatabaseConnection;
import org.ecomoveV1.models.entities.Journey;
import org.ecomoveV1.repositories.JourneyRepository;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class JourneyRepositoryImpl implements JourneyRepository {
    final private Connection connection = DatabaseConnection.getInstance().getConnection();
    final String tableName = "journey";


    @Override
    public void createJourney(Journey journey) {

        String query = "INSERT INTO " + tableName + " (id, start_location, end_location, departure_time, arrival_time) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)){

            pstmt.setObject(1, journey.getId());
            pstmt.setString(2, journey.getStartLocation());
            pstmt.setString(3, journey.getEndLocation());
            pstmt.setDate(4, java.sql.Date.valueOf(journey.getDepartureTime()));
            pstmt.setDate(5, java.sql.Date.valueOf(journey.getArrival_time()));

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
