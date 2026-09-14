package sg.edu.ntu.simple_crm.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import java.util.List;
import sg.edu.ntu.simple_crm.model.Customer;
import sg.edu.ntu.simple_crm.repository.CustomerRepository;
import sg.edu.ntu.simple_crm.exceptions.CustomerNotFoundException;

@Primary
@Service
public class CustomerServiceImpl implements CustomerService {
  private CustomerRepository customerRepository;

  @Autowired
  public CustomerServiceImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
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
    customerRepository.deleteById(id);
  }
}