package org.ecomoveV1.presentations;

import org.ecomoveV1.models.entities.Customer;
import org.ecomoveV1.services.CustomerService;

import java.util.Optional;
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

    public void searchCustomer() {
        System.out.println("Search customer by:");
        System.out.println("1. Email");
        System.out.println("2. Name");
        int choice = Integer.parseInt(scanner.nextLine());

        Optional<Customer> customer;
        switch (choice) {
            case 1:
                String email = getEmailInput();
                customer = customerService.findCustomerByEmail(email);
                break;
            case 2:
                String firstName = getFirstNameInput();
                String lastName = getLastNameInput();
                customer = customerService.findCustomerByName(firstName, lastName);
                break;
            default:
                System.out.println("Invalid choice");
                return;
        }

        if (customer.isPresent()) {
            System.out.println("Customer found: " + customer.get());
        } else {
            System.out.println("Customer not found. Would you like to register? (Y/N)");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("Y")) {
                addCustomer();
            }
        }
    }



    public Customer login() {
        while (true) {
            System.out.println("Enter your email to log in (or 'register' to create a new account):");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("register")) {
                return registerNewCustomer();
            }

            Optional<Customer> customer = customerService.login(input);
            if (customer.isPresent()) {
                System.out.println("Login successful!");
                return customer.get();
            } else {
                System.out.println("No account found with this email. Would you like to register? (Y/N)");
                if (scanner.nextLine().trim().equalsIgnoreCase("Y")) {
                    return registerNewCustomer();
                }
            }
        }
    }

    private Customer registerNewCustomer() {
        addCustomer();
        return customerService.findCustomerByEmail(getEmailInput()).orElseThrow(() -> new RuntimeException("Failed to create customer"));
    }
}
