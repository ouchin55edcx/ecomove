package org.ecomoveV1.presentations;

import org.ecomoveV1.services.CustomerService;

import java.util.Scanner;

public class CustomerUi {
    final private CustomerService customerService;
    final private Scanner scanner = new Scanner(System.in);

    public CustomerUi(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void addCustomer(){

        String firtName = getFirstNameInput();
        String lastName = getLastNameInput();
        String email = getEmailInput();
        String phoneNumber = getPhoneNumberInput();

        customerService.addCustomer(firtName,lastName,email,phoneNumber);
        System.out.println("Client registered successfully!");

    }

    private String getFirstNameInput(){
        while (true){

            System.out.println("Enter your first Name :");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()){
                return input;
            }
            System.out.println("first name cannot be empty. Please try again.");
        }

    }
    private String getLastNameInput(){
        while (true){

            System.out.println("Enter your last Name :");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()){
                return input;
            }
            System.out.println("first name cannot be empty. Please try again.");
        }

    }
    private String getEmailInput(){
        while (true){

            System.out.println("Enter your email :");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()){
                return input;
            }
            System.out.println("first name cannot be empty. Please try again.");
        }

    }
    private String getPhoneNumberInput(){
        while (true){

            System.out.println("Enter your phone number  :");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()){
                return input;
            }
            System.out.println("first name cannot be empty. Please try again.");
        }

    }
}
