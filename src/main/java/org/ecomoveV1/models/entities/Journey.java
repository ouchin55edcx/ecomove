package org.ecomoveV1.models.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Journey {
    private UUID id ;
    private String startLocation;
    private String endLocation ;
    private LocalDate departureTime ;
    private LocalDate arrival_time ;
    private List<Ticket> tickets ;

    public Journey(UUID id, String startLocation, String endLocation, LocalDate departureTime, LocalDate arrival_time, List<Ticket> tickets) {
        this.id = id;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.departureTime = departureTime;
        this.arrival_time = arrival_time;
        this.tickets = tickets;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public LocalDate getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDate departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDate getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(LocalDate arrival_time) {
        this.arrival_time = arrival_time;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }


    @Override
    public String toString() {
        return "Journey{" +
                "id=" + id +
                ", startLocation='" + startLocation + '\'' +
                ", endLocation='" + endLocation + '\'' +
                ", departureTime=" + departureTime +
                ", arrival_time=" + arrival_time +
                ", tickets=" + tickets +
                '}';
    }






}
