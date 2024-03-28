package sandbox.book.employeemanager.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String position;

    private LocalDate creationDate;

    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "employee")
    @JsonIgnoreProperties("employee")
    private SupervisorEmployeeLink supervisorLink;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "supervisor")
    @JsonIgnoreProperties("supervisor")
    private Set<SupervisorEmployeeLink> employeeLinks;

}
