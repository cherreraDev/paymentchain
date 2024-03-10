package com.paymentchain.product.controller;

import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerRestCustomer {
    private final CustomerRepository customerRepository;
    public CustomerRestCustomer(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping()
    public List<Customer> findAll() {
        return this.customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable long id)
    {
     Optional<Customer> optionalCustomer = customerRepository.findById(id);
     if(optionalCustomer.isPresent()){
         return new ResponseEntity<>( optionalCustomer.get(), HttpStatus.OK);
     }
        else {
          return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable long id, @RequestBody Customer input) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if(optionalCustomer.isPresent()){
            Customer newCustomer = optionalCustomer.get();
            newCustomer.setName(input.getName());
            newCustomer.setPhone(input.getPhone());
            Customer save = customerRepository.save(newCustomer);
            return new ResponseEntity<>(save, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Customer input) {
        Customer save = customerRepository.save(input);
        return ResponseEntity.ok(save);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        customerRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
