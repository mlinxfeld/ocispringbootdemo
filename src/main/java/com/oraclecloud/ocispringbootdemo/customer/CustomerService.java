package com.oraclecloud.ocispringbootdemo.customer;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public void addNewCustomer(Customer customer) {
        Optional<Customer> customerOptional = customerRepository.
                findCustomerByEmail(customer.getEmail());
        if (customerOptional.isPresent()) {
            throw new IllegalStateException("email already in a database!");
        }
        customerRepository.save(customer);
    }

    public void deleteCustomer(Long customerId) {
        boolean exists = customerRepository.existsById(customerId);
        if (!exists) {
            throw new IllegalStateException(
                    "Customer with Id=" + customerId + " does not exists!");
        }
        customerRepository.deleteById(customerId);
    }

    @Transactional
    public void updateCustomer(Long customerId,
                               String first_name,
                               String last_name,
                               String email) {
        Customer customer = customerRepository.findById(customerId).
                orElseThrow(() -> new IllegalStateException(
                        "customer with Id=" + customerId + " does not exists!")
                );

        if (first_name != null &&
                first_name.length() > 0 &&
                !Objects.equals(customer.getFirst_name(), first_name)) {
            customer.setFirst_name(first_name);
        }

        if (last_name != null &&
                last_name.length() > 0 &&
                !Objects.equals(customer.getLast_name(), last_name)) {
            customer.setLast_name(last_name);
        }

        if (email != null &&
                email.length() > 0 &&
                !Objects.equals(customer.getEmail(), email)) {
            Optional<Customer> customerOptional = customerRepository
                    .findCustomerByEmail(email);
            if (customerOptional.isPresent()) {
                throw new IllegalStateException("email already in a database!");
            }
            customer.setEmail(email);
        }
    }
}
