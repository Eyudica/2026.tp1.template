package main.java.com.bibliotech.repository;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import main.java.com.bibliotech.model.Customer;

public class CustomerRepository implements Repository<Customer, Integer> {

    private Map<Integer, Customer> data = new HashMap<>();

    @Override
    public void save(Customer customer) {
        data.put(customer.getDni(), customer);
    }

    @Override
    public Optional<Customer> searchById(Integer dni) {
        return Optional.ofNullable(data.get(dni));
    }


}