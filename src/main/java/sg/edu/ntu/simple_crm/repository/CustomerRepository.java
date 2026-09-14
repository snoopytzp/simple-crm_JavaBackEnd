package sg.edu.ntu.simple_crm.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import sg.edu.ntu.simple_crm.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
}
