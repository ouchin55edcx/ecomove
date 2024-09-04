package org.ecomoveV1.presentations;

import org.ecomoveV1.models.entities.Ticket;
import org.ecomoveV1.models.enums.TicketStatus;
import org.ecomoveV1.models.enums.TransportType;
import org.ecomoveV1.repositories.TicketRepository;
import org.ecomoveV1.repositories.impl.TicketRepositoryImpl;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class TicketUi {

    final private TicketRepository repository ;
    final  private Scanner scanner = new Scanner(System.in);

    public TicketUi(TicketRepository repository) {
        this.repository = repository;
    }


    public void addTicket(){
        System.out.println("#------------ Add New Ticket : -------------#");

        UUID id = UUID.randomUUID();
        UUID contractId = getContcractIdInput();
        TransportType transportType = getTransportTypeInput();
        BigDecimal purchasePrice = getPurchasePrice();
        BigDecimal salePrice = getSalePrice();
        LocalDate saleDate = getSaleDate();
        TicketStatus ticketStatus = getTicketStatusInput();

        Ticket newTicket = new Ticket(
                UUID.randomUUID(),
                contractId,
                transportType,
                purchasePrice,
                salePrice,
                saleDate,
                ticketStatus

        );

        repository.addTicket(newTicket);
        System.out.println(" New Ticket added successfully!");

    }

    private UUID getContcractIdInput() {
        while (true) {
            System.out.print("Enter Contract ID: ");
            String input = scanner.nextLine().trim();
            try {
                return UUID.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID format. Please try again.");
            }
        }
    }

    private TransportType getTransportTypeInput(){
        System.out.println("Enter Transport type (BUS, PLANE ...)");
        String input = scanner.nextLine().trim().toUpperCase();
        try {
            return TransportType.valueOf(input);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    private BigDecimal getPurchasePrice(){
        while (true){
            System.out.println("Enter the purchase Price :");
            String input = scanner.nextLine().trim();
            try {
                return new BigDecimal(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid number format. Please enter a valid number.");

            }

        }

    }

    private BigDecimal getSalePrice(){
        while (true) {
            System.out.println("Enter Sale Price :");
            String input = scanner.nextLine().trim();
            try {
                return new BigDecimal(input);
            }catch (IllegalArgumentException e){
                System.out.println("Invalid price please try again ");
            }
        }
    }

    private LocalDate getSaleDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while(true){
            System.out.println("Enter Sale date (YYYY-MM-DD)");
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input, formatter);
            }catch (IllegalArgumentException e){
                System.out.println("invalid date format, try again ");
            }
            System.out.println("invalid date format ");
        }
    }

    private TicketStatus getTicketStatusInput(){
        while(true){
            System.out.println("enter the ticket status :");
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                return TicketStatus.valueOf(input);
            }catch (IllegalArgumentException e){
                System.out.println("Invalid status , try again ");
            }
        }
    }

    public void ListAllTicket(){
        List<Ticket> tickets = repository.getAllticket();
        if (tickets.isEmpty()){
            System.out.println("No ticket found ");
        }else {
            System.out.println("All ticket ");
            for (Ticket ticket : tickets){
                System.out.println(ticket);
            }
        }
    }


    public void updateTicket() {
        System.out.println("#------------ Update Ticket : -------------#");

        UUID id = getTicketIdInput();
        UUID contractId = getContcractIdInput();
        TransportType transportType = getTransportTypeInput();
        BigDecimal purchasePrice = getPurchasePrice();
        BigDecimal salePrice = getSalePrice();
        LocalDate saleDate = getSaleDate();
        TicketStatus ticketStatus = getTicketStatusInput();

        Ticket updatedTicket = new Ticket(
                id,
                contractId,
                transportType,
                purchasePrice,
                salePrice,
                saleDate,
                ticketStatus
        );

        repository.updateTicket(id, updatedTicket);
        System.out.println("Ticket updated successfully!");
    }

    private UUID getTicketIdInput() {
        while (true) {
            System.out.print("Enter Ticket ID: ");
            String input = scanner.nextLine().trim();
            try {
                return UUID.fromString(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid UUID format. Please try again.");
            }
        }
    }


    public void deleteTicket() {
        System.out.println("#------------ Delete Ticket : -------------#");

        UUID id = getTicketIdInput();

        try {
            repository.deleteTicket(id);
            System.out.println("Ticket deleted successfully!");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewTicketById() {
        System.out.println("#------------ View Ticket by ID : -------------#");

        UUID id = getTicketIdInput();

        try {
            Ticket ticket = repository.getTicketById(id);
            System.out.println("Ticket Details:");
            System.out.println("ID: " + ticket.getTicketId());
            System.out.println("Contract ID: " + ticket.getContractId());
            System.out.println("Transport Type: " + ticket.getTransportType());
            System.out.println("Purchase Price: " + ticket.getPurchasePrice());
            System.out.println("Sale Price: " + ticket.getSalePrice());
            System.out.println("Sale Date: " + ticket.getSaleDate());
            System.out.println("Status: " + ticket.getTicketStatus());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }







}
