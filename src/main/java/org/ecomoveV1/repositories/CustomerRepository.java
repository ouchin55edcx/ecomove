package org.ecomoveV1.repositories;

import org.ecomoveV1.models.entities.Customer;

import java.util.Optional;

public interface CustomerRepository {
    void addCustomer(Customer customer);
    Optional<Customer> findCustomerByEmail(String email);
    Optional<Customer> findCustomerByName(String firstName, String lastName);

}
