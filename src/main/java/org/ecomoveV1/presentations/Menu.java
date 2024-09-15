    package org.ecomoveV1.presentations;

    public class Menu {

        public void displayMainMenu() {
            System.out.println("\n--- Management System ----");
            System.out.println("1. Partner Management");
            System.out.println("2. Contract Management");
            System.out.println("3. Promotion Management");
            System.out.println("4. Ticket Management");
            System.out.println("5. Client Management");
            System.out.println("6. Journey Management");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
        }

        public void displayPartnerMenu() {
            System.out.println("\n--- Partner Management ----");
            System.out.println("1. Display all partner names");
            System.out.println("2. Add a partner");
            System.out.println("3. Display partner details by name");
            System.out.println("4. Update Partner Status");
            System.out.println("5. Delete partner");
            System.out.println("6. Update partner");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
        }

        public void displayContractMenu() {
            System.out.println("\n--- Contract Management ----");
            System.out.println("1. Add Contract");
            System.out.println("2. Display all contracts");
            System.out.println("3. Display Contract by partner Id");
            System.out.println("4. Get contract details by id");
            System.out.println("5. Update contract");
            System.out.println("6. Delete contract by id");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
        }

        public void displayPromotionMenu() {
            System.out.println("\n--- Promotion Management ----");
            System.out.println("1. Add promotion");
            System.out.println("2. List all promotion Offers");
            System.out.println("3. Display promotion offer by id");
            System.out.println("4. Update promotionOffer");
            System.out.println("5. Delete a promotion offer");
            System.out.println("6. Display promotion By contract id");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
        }

        public void displayTicketMenu() {
            System.out.println("\n--- Ticket Management ----");
            System.out.println("1. Add a ticket");
            System.out.println("2. List all tickets");
            System.out.println("3. Update ticket");
            System.out.println("4. Delete ticket");
            System.out.println("5. View ticket by id");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
        }

        public void displayClientMenu(){
            System.out.println("\n--- Customer Management ----");
            System.out.println("1. Register a client");
            System.out.println("2. Search for a client");
            System.out.println("3. Search for tickets and book");
            System.out.println("4. Manage reservations");
            System.out.println("0. Back to Main Menu");
        }


        public void displayJourneyMenu(){
            System.out.println("\n--- Journey Management ----");
            System.out.println("1. Add a journey");
            System.out.println("2. Display all journeys");
            System.out.println("3. Search a journey");
            System.out.println("4. Reserve a journey");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");
        }
    }