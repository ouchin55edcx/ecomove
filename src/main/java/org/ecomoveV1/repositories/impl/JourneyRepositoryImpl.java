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


    }
}
