package main.java.com.bibliotech.service;

import main.java.com.bibliotech.exception.CustomerException;
import main.java.com.bibliotech.repository.Repository;
import main.java.com.bibliotech.model.Customer;
import java.util.Optional;

public class CustomerService {
    private final Repository<Customer, Integer> customerRepo;

    public CustomerService(Repository<Customer, Integer> customerRepo){
        this.customerRepo=customerRepo;
    }

    public void registerCustomer(Customer customer) throws CustomerException {
        // validar DNI duplicado
        if (customerRepo.searchById(customer.getDni()).isPresent()) {
            throw new CustomerException("A customer with DNI " + customer.getDni() + " is already registered");
        }

        // validar email
        if (!isValidEmail(customer.getEmail())) {
            throw new CustomerException("Invalid email format");
        }

        customerRepo.save(customer);
    }

    private boolean isValidEmail(String email) {
        if (email == null) return false;

        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }
    public Optional<Customer> searchCustomer(int dni){
        return customerRepo.searchById(dni);
    }
}
