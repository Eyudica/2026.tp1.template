package main.java.com.bibliotech.service;

import main.java.com.bibliotech.exception.LibraryException;
import main.java.com.bibliotech.repository.Repository;
import main.java.com.bibliotech.model.Customer;
import java.util.Optional;

public class CustomerService {
    private final Repository<Customer, Integer> customerRepo;

    public CustomerService(Repository<Customer, Integer> customerRepo){
        this.customerRepo=customerRepo;
    }

    public void registerCustomer(Customer customer) throws LibraryException {
        // validar si el dni ya esta registrado
        if (customerRepo.searchById(customer.getDni()).isPresent()){
            throw new LibraryException("A customer with DNI " + customer.getDni()+" is already registered");
        }
        customerRepo.save(customer);
    }
    public Optional<Customer> searchCustomer(int dni){
        return customerRepo.searchById(dni);
    }
}
