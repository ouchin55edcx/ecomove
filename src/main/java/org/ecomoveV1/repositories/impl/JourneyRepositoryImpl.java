package org.ecomoveV1.repositories.impl;

import org.ecomoveV1.config.DatabaseConnection;
import org.ecomoveV1.models.entities.Journey;
import org.ecomoveV1.models.entities.Ticket;
import org.ecomoveV1.repositories.JourneyRepository;
import org.ecomoveV1.repositories.TicketRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JourneyRepositoryImpl implements JourneyRepository {
    final private Connection connection = DatabaseConnection.getInstance().getConnection();
    private final TicketRepository ticketRepository;
    final String tableName = "journey";

    public JourneyRepositoryImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public void createJourney(Journey journey) {

        String query = "INSERT INTO " + tableName + " (id, start_location, end_location, departure_time, arrival_time) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

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

    @Override
    public List<Journey> displayAllJourneys() {
        List<Journey> journeys = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;

        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);

            while (resultSet.next()) {
                UUID id = (UUID) resultSet.getObject("id");
                String startLocation = resultSet.getString("start_location");
                String endLocation = resultSet.getString("end_location");
                LocalDate departureTime = resultSet.getDate("departure_time").toLocalDate();
                LocalDate arrivalTime = resultSet.getDate("arrival_time").toLocalDate();
                List<Ticket> tickets = ticketRepository.getTicketsByJourneyId(id);

                Journey journey = new Journey(id, startLocation, endLocation, departureTime, arrivalTime, tickets);
                journeys.add(journey);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return journeys;
    }

    @Override
    public List<Journey> searchJourneys(String startLocation, String endLocation, LocalDate date) {
        List<Journey> journeys = new ArrayList<>();
        String query = "SELECT * FROM " + tableName +
                " WHERE start_location = ? AND end_location = ? AND DATE(departure_time) = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, startLocation);
            pstmt.setString(2, endLocation);
            pstmt.setDate(3, java.sql.Date.valueOf(date));

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                UUID id = (UUID) resultSet.getObject("id");
                LocalDate departureTime = resultSet.getDate("departure_time").toLocalDate();
                LocalDate arrivalTime = resultSet.getDate("arrival_time").toLocalDate();
                List<Ticket> tickets = ticketRepository.getTicketsByJourneyId(id);

                Journey journey = new Journey(id, startLocation, endLocation, departureTime, arrivalTime, tickets);
                journeys.add(journey);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return journeys;
    }



}
