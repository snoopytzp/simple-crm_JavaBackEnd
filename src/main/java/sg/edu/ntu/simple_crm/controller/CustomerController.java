package sg.edu.ntu.simple_crm.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

import sg.edu.ntu.simple_crm.exceptions.CustomerNotFoundException;
import sg.edu.ntu.simple_crm.model.Customer;
import sg.edu.ntu.simple_crm.model.Interaction;
import sg.edu.ntu.simple_crm.service.CustomerService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping ("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(@Qualifier("customerServiceValidationImpl") CustomerService customerService) {
    //public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // create a customer by calling an API
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody @Valid Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    @GetMapping   // Label: "this method handles GET /customers"
    public ResponseEntity<List<Customer>> getCustomers() {
        List<Customer> allCustomers = customerService.getAllCustomers();
        return new ResponseEntity<>(allCustomers, HttpStatus.OK);     // returned as JSON data
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomer(@PathVariable Long id) {
        // try {
        //     Customer foundCustomer = customerService.getCustomer(id);
        //     return new ResponseEntity<>(foundCustomer, HttpStatus.OK);
        // } catch (CustomerNotFoundException e) {
        //     return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        // }
        Customer foundCustomer = customerService.getCustomer(id);
        return new ResponseEntity<>(foundCustomer, HttpStatus.OK);
    }

    // Removed getCustomerIndex method as it is now handled by the service

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        // try {
        //     Customer updatedCustomer = customerService.updateCustomer(id, customer);
        //     return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        // } catch (CustomerNotFoundException e) {
        //     return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        // }
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/interactions")
    public ResponseEntity<Interaction> addInteractionToCustomer(
        @PathVariable Long id,
        @RequestBody Interaction interaction) {
        Interaction newInteraction = customerService.addInteractionToCustomer(id, interaction);
        return new ResponseEntity<>(newInteraction, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Customer>> searchCustomers(@RequestParam String firstName) {
        List<Customer> foundCustomers = customerService.searchCustomers(firstName);
        return new ResponseEntity<>(foundCustomers, HttpStatus.OK);
    }

    @GetMapping("/search/job")
    public ResponseEntity<List<Customer>> searchCustomersByJobTitle(@RequestParam String jobTitle) {
        List<Customer> foundCustomers = customerService.searchCustomersByJobTitle(jobTitle);
        return new ResponseEntity<>(foundCustomers, HttpStatus.OK);
    }
}
    