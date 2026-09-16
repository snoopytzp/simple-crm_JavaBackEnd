package sg.edu.ntu.simple_crm.service;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import java.util.List;
import sg.edu.ntu.simple_crm.model.Customer;
import sg.edu.ntu.simple_crm.repository.CustomerRepository;
import sg.edu.ntu.simple_crm.exceptions.CustomerNotFoundException;
import sg.edu.ntu.simple_crm.model.Interaction;
import sg.edu.ntu.simple_crm.repository.InteractionRepository;

@Primary
@Service
public class CustomerServiceImpl implements CustomerService {
  private CustomerRepository customerRepository;
  private InteractionRepository interactionRepository;

  //@Autowired    Because it's the only constructor, Spring will automatically use it for dependency injection.
  public CustomerServiceImpl(CustomerRepository customerRepository, InteractionRepository interactionRepository) {
    this.customerRepository = customerRepository;
    this.interactionRepository = interactionRepository;
  }

  @Override
  public Customer createCustomer(Customer customer) {
    return customerRepository.save(customer);
  }

  @Override
  public Customer getCustomer(Long id) {
    return customerRepository.findById(id)
        .orElseThrow(() -> new CustomerNotFoundException(id));
  }

  @Override
  public List<Customer> getAllCustomers() {
    return customerRepository.findAll();
  }

  @Override
  public Customer updateCustomer(Long id, Customer customer) {
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
    customerRepository.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException(id));
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

  @Override
  public List<Customer> searchCustomers(String firstName) {
      return customerRepository.findByFirstName(firstName);
  }

  @Override
  public List<Customer> searchCustomersByJobTitle(String jobTitle) {
      //return customerRepository.findByJobTitleNative(jobTitle);
      return customerRepository.findByJobTitleJPQL(jobTitle);
  }
}