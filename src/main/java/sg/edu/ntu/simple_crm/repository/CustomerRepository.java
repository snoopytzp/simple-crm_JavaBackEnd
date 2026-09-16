package sg.edu.ntu.simple_crm.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import sg.edu.ntu.simple_crm.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    // Custom query to find all customers with a certain first name
    List<Customer> findByFirstName(String firstName);

    // Find first names starting with a given string
    List<Customer> findByFirstNameStartingWith(String firstName);

    // Search by two fields at once, chained with And
    List<Customer> findByFirstNameAndLastName(String firstName, String lastName);

    // Find last names containing a given string
    List<Customer> findByLastNameContaining(String lastName);

    // JPQL — uses the entity name (Customer) and field name (jobTitle)
    @Query("SELECT c FROM Customer c WHERE c.jobTitle = :jobTitle")
    List<Customer> findByJobTitleJPQL(@Param("jobTitle") String jobTitle);

    // Native SQL — uses the table name (customer) and column name (job_title)
    @Query(value = "SELECT * FROM customer WHERE job_title = :jobTitle", nativeQuery = true)
    List<Customer> findByJobTitleNative(@Param("jobTitle") String jobTitle);
}
