package org.ecomoveV1;

import org.ecomoveV1.presentations.*;
import org.ecomoveV1.repositories.*;
import org.ecomoveV1.repositories.impl.*;
import org.ecomoveV1.services.ContractService;
import org.ecomoveV1.services.PartnerService;
import org.ecomoveV1.services.PromotionService;

import java.util.Scanner;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);
    private static final PartnerRepository partnerRepository = new PartnerRepositoryImpl();
    private static final ContactRepository contactRepository = new ContactRepositoryImpl();
    private static final PromotionalOfferRepository promotionalOfferRepository = new PromotionalOfferRepositoryImpl();
    private static final TicketRepository repositoryTck = new TicketRepositoryImpl();

    public static void main(String[] args) {
        final Menu menu = new Menu();

        PartnerService partnerService = new PartnerService(partnerRepository);
        ContractService contractService = new ContractService(contactRepository);
        PromotionService promotionService = new PromotionService(promotionalOfferRepository);
        
        PartnerUi partnerUi = new PartnerUi(partnerService);
        ContractUi contractUi = new ContractUi(contractService);
        PromotionUi promotionUi = new PromotionUi(promotionService);


        final TicketUi ticketUi = new TicketUi(repositoryTck);
        boolean running = true;

        while (running) {
            menu.displayMainMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    handlePartnerMenu(menu, partnerUi);
                    break;
                case 2:
                    handleContractMenu(menu, contractUi);
                    break;
                case 3:
                    handlePromotionMenu(menu, promotionUi);
                    break;
                case 4:
                    handleTicketMenu(menu, ticketUi);
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting! Goodbye.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handlePartnerMenu(Menu menu, PartnerUi partnerUi) {
        boolean inPartnerMenu = true;
        while (inPartnerMenu) {
            menu.displayPartnerMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    partnerUi.displayAllPartnerNames();
                    break;
                case 2:
                    partnerUi.addPartner();
                    break;
                case 3:
                    partnerUi.displayPartnerByName();
                    break;
                case 4:
                   partnerUi.UpdatePartnerStatus();
                    break;
                case 5:
                    partnerUi.deletePartner();
                    break;
                case 6:
                    partnerUi.updatePartner();
                    break;
                case 0:
                    inPartnerMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleContractMenu(Menu menu, ContractUi contractUi) {
        boolean inContractMenu = true;
        while (inContractMenu) {
            menu.displayContractMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    contractUi.addContract();
                    break;
                case 2:
                    contractUi.displayContractDetails();
                    break;
                case 3:
                    contractUi.displayContractsByPartnerId();
                    break;
                case 4:
                    contractUi.displayContractById();
                    break;
                case 5:
                    contractUi.updateContractById();
                    break;
                case 6:
                    //contractUi.deleteContractById();
                    break;
                case 0:
                    inContractMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handlePromotionMenu(Menu menu, PromotionUi promotionUi) {
        boolean inPromotionMenu = true;
        while (inPromotionMenu) {
            menu.displayPromotionMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    promotionUi.addPromotion();
                    break;
                case 2:
                    promotionUi.listAllPromotionalOffers();
                    break;
                case 3:
                    promotionUi.displayPromotionById();
                    break;
                case 4:
                    promotionUi.updatePromotionalOffer();
                    break;
                case 5:
                    promotionUi.deletePromotionalOffer();
                    break;
                case 0:
                    inPromotionMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleTicketMenu(Menu menu, TicketUi ticketUi) {
        boolean inTicketMenu = true;
        while (inTicketMenu) {
            menu.displayTicketMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    ticketUi.addTicket();
                    break;
                case 2:
                    ticketUi.ListAllTicket();
                    break;
                case 3:
                    ticketUi.updateTicket();
                    break;
                case 4:
                    ticketUi.deleteTicket();
                    break;
                case 5:
                    ticketUi.viewTicketById();
                    break;
                case 0:
                    inTicketMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.println("Not a valid number. Try again!");
            scanner.next();
        }
        return scanner.nextInt();
    }
}