package sg.edu.ntu.simple_crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import java.util.List;

import org.springframework.stereotype.Service;

import sg.edu.ntu.simple_crm.exceptions.CustomerNotFoundException;
import sg.edu.ntu.simple_crm.exceptions.InvalidCustomerException;
import sg.edu.ntu.simple_crm.model.Customer;
import sg.edu.ntu.simple_crm.model.Interaction;
import sg.edu.ntu.simple_crm.repository.CustomerRepository;
import sg.edu.ntu.simple_crm.repository.InteractionRepository;

//@Primary
@Service 
public class CustomerServiceValidationImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private InteractionRepository interactionRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceValidationImpl.class);

    public CustomerServiceValidationImpl(CustomerRepository customerRepository, InteractionRepository interactionRepository) {
        logger.warn("Initializing CustomerServiceValidationImpl with CustomerRepository and InteractionRepository");
        this.customerRepository = customerRepository;
        this.interactionRepository = interactionRepository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        validateCustomer(customer);
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        validateCustomer(customer); 

        // Retrieve the customer from the database
        Customer customerToUpdate = customerRepository.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException(id));
            
        // Update the fields
        customerToUpdate.setFirstName(customer.getFirstName());
        customerToUpdate.setLastName(customer.getLastName());
        customerToUpdate.setEmail(customer.getEmail());
        customerToUpdate.setContactNo(customer.getContactNo());
        customerToUpdate.setJobTitle(customer.getJobTitle());
        customerToUpdate.setYearOfBirth(customer.getYearOfBirth());

        // Save and return the updated customer
        return customerRepository.save(customerToUpdate);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Interaction addInteractionToCustomer(Long id, Interaction interaction) {
        // Step 1: Find the customer — throw an error if not found
        Customer selectedCustomer = customerRepository.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException(id));
        // Step 2: Link the customer to the interaction
        interaction.setCustomer(selectedCustomer);
        // Step 3: Save and return the interaction
        return interactionRepository.save(interaction);
    }

    // private int getCustomerIndex(Long id) {
    //     for (Customer customer : customerRepository.getAllCustomers()) {
    //     if (customer.getId().equals(id)) {
    //         return customerRepository.getAllCustomers().indexOf(customer);
    //     }
    //     }
    //     throw new CustomerNotFoundException(id);
    // }

    private void validateCustomer(Customer customer) {
        logger.warn("Validating customer: " + customer);

        if (customer.getFirstName() == null || customer.getFirstName().length() == 0)
        {
            logger.error("Customer first name is null");
            throw new InvalidCustomerException("Customer first name cannot be null");
        }
        else if (customer.getLastName() == null || customer.getLastName().length() == 0)
        {
            logger.error("Customer last name is null");
            throw new InvalidCustomerException("Customer last name cannot be null");
        }
        else if (customer.getEmail() == null || !customer.getEmail().contains("@"))
        {
            logger.error("Customer email is invalid");
            throw new InvalidCustomerException("Customer email must contain '@'");
        }
    }
}
