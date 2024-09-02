package org.ecomoveV1;


import org.ecomoveV1.presentations.ContractUi;
import org.ecomoveV1.presentations.Menu;
import org.ecomoveV1.presentations.PartnerUi;
import org.ecomoveV1.repositories.ContactRepository;
import org.ecomoveV1.repositories.PartnerRepository;
import org.ecomoveV1.repositories.impl.ContactRepositoryImpl;
import org.ecomoveV1.repositories.impl.PartnerRepositoryImpl;
import java.util.Scanner;

public class Main {
    public static final Scanner scanner = new Scanner(System.in);
    private static final PartnerRepository repository = new PartnerRepositoryImpl();
    private static final ContactRepository repositoryCnt = new ContactRepositoryImpl();


    public static void main(String[] args){

        final Menu menu = new Menu();
        final PartnerUi partnerUi = new PartnerUi(repository);
        final ContractUi contractUi = new ContractUi(repositoryCnt);

        boolean running = true ;


        while (running){
            menu.displayMenu();

            int choice = getUserChoice();

            switch (choice){
                case 1 :
                    partnerUi.displayAllPartnerNames();
                    break;
                case 2 :
                    partnerUi.addPartner();
                    break;
                case 3 :
                    partnerUi.displayPartnerByName();
                    break;
                case 4 :
                    partnerUi.UpdatePartnerStatus();
                    break;
                case 5 :
                    partnerUi.deletePartner();
                    break;
                case 6 :
                    partnerUi.updatePartner();
                    break;
                case 7 :
                    contractUi.addContract();
                    break;
                case 8 :
                    contractUi.displayContractDetails();
                    break;
                case 9 :
                    contractUi.displayContractsByPartnerId();
                    break;
                case 10 :
                    contractUi.displayContractById();
                    break;
                case 0 :
                    running = false;
                    System.out.println("Exit ! \n Good By");
                    break;
            }
        }
    }


    private static  int getUserChoice(){
        while (!scanner.hasNext()){
            System.out.println("Not a valid number . Try again!");
            scanner.next();
        }
        return scanner.nextInt();
    }


}