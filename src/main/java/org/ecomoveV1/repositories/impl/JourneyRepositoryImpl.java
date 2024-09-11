package org.ecomoveV1.repositories.impl;

import org.ecomoveV1.config.DatabaseConnection;
import org.ecomoveV1.models.entities.Journey;
import org.ecomoveV1.models.entities.Ticket;
import org.ecomoveV1.repositories.JourneyRepository;
import org.ecomoveV1.repositories.TicketRepository;

import java.sql.*;
import java.time.LocalDate;
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
    public List<Journey> searchJourneys(String startLocation, String endLocation, LocalDate departureDate) {
        List<Journey> journeys = new ArrayList<>();

        String directQuery = "SELECT * FROM " + tableName +
                " WHERE LOWER(start_location) LIKE LOWER(?) AND LOWER(end_location) LIKE LOWER(?) AND DATE(departure_time) = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(directQuery)) {
            pstmt.setString(1, "%" + startLocation + "%");
            pstmt.setString(2, "%" + endLocation + "%");
            pstmt.setDate(3, java.sql.Date.valueOf(departureDate));
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                Journey journey = mapJourney(resultSet);
                journeys.add(journey);
            }

            if (journeys.isEmpty()) {
                journeys = searchIndirectRoutes(startLocation, endLocation, departureDate);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error searching for journeys", e);
        }

        return journeys;
    }

    private Journey mapJourney(ResultSet resultSet) throws SQLException {
        UUID id = (UUID) resultSet.getObject("id");
        String start = resultSet.getString("start_location");
        String end = resultSet.getString("end_location");
        LocalDate departure = resultSet.getTimestamp("departure_time").toLocalDateTime().toLocalDate();
        LocalDate arrival = resultSet.getTimestamp("arrival_time").toLocalDateTime().toLocalDate();
        List<Ticket> tickets = ticketRepository.getTicketsByJourneyId(id);
        return new Journey(id, start, end, departure, arrival, tickets);
    }

    private List<Journey> searchIndirectRoutes(String startLocation, String endLocation, LocalDate departureDate) {
        List<Journey> possibleRoutes = new ArrayList<>();

        String query = "SELECT * FROM " + tableName + " WHERE LOWER(start_location) LIKE LOWER(?) AND DATE(departure_time) = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, "%" + startLocation + "%");
            pstmt.setDate(2, java.sql.Date.valueOf(departureDate));
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                Journey firstLeg = mapJourney(resultSet);

                if (firstLeg.getEndLocation().equalsIgnoreCase(endLocation)) {
                    possibleRoutes.add(firstLeg);
                } else {
                    List<Journey> secondLegs = searchIndirectRoutes(firstLeg.getEndLocation(), endLocation, firstLeg.getArrival_time());
                    for (Journey secondLeg : secondLegs) {
                        possibleRoutes.add(new Journey(
                                UUID.randomUUID(),
                                firstLeg.getStartLocation(),
                                secondLeg.getEndLocation(),
                                firstLeg.getDepartureTime(),
                                secondLeg.getArrival_time(),
                                combinedTickets(firstLeg, secondLeg)
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error searching for indirect routes", e);
        }

        return possibleRoutes;
    }

    private List<Ticket> combinedTickets(Journey firstLeg, Journey secondLeg) {
        List<Ticket> combinedTickets = new ArrayList<>(firstLeg.getTickets());
        combinedTickets.addAll(secondLeg.getTickets());
        return combinedTickets;
    }



}
