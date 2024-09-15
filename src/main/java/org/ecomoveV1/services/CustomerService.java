package org.ecomoveV1.services;

import org.ecomoveV1.models.entities.Customer;
import org.ecomoveV1.repositories.CustomerRepository;

import java.util.Optional;
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

    public Optional<Customer> findCustomerByEmail(String email) {
        return customerRepository.findCustomerByEmail(email);
    }

    public Optional<Customer> findCustomerByName(String firstName, String lastName) {
        return customerRepository.findCustomerByName(firstName, lastName);
    }

    public Customer findOrCreateCustomer(String firstName, String lastName, String email, String phoneNumber) {
        Optional<Customer> existingCustomer = findCustomerByEmail(email);
        if (existingCustomer.isPresent()) {
            return existingCustomer.get();
        } else {
            addCustomer(firstName, lastName, email, phoneNumber);
            return findCustomerByEmail(email).orElseThrow(() -> new RuntimeException("Failed to create customer"));
        }
    }
    public Optional<Customer> login(String email) {
        return customerRepository.findCustomerByEmail(email);
    }
}
