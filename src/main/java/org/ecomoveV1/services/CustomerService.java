package org.ecomoveV1.services;

import org.ecomoveV1.models.entities.Customer;
import org.ecomoveV1.repositories.CustomerRepository;

import java.util.UUID;

public class CustomerService {

    final private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void addCustomer(String firstName, String lastName, String email, String phoneNumber){

        UUID id = UUID.randomUUID();

        Customer newCustomer = new Customer(id, firstName, lastName , email, phoneNumber);
        customerRepository.addCustomer(newCustomer);

    }
}
