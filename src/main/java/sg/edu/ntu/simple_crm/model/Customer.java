package sg.edu.ntu.simple_crm.model;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;

@Data
@NoArgsConstructor
//@JsonPropertyOrder({ "id", "firstName", "lastName", "email", "contactNo", "jobTitle", "yearOfBirth" })
@Entity 
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    //private final String id = UUID.randomUUID().toString();

    @Column(name = "first_name")
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    @Email(message = "Email should be valid")
    private String email;
    @Column(name = "contact_no")
    private String contactNo;
    @Column(name = "job_title")
    private String jobTitle;
    @Column(name = "year_of_birth")
    private int yearOfBirth;

    // public Customer() {
    //     this.id = UUID.randomUUID().toString();
    // }
    
    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
