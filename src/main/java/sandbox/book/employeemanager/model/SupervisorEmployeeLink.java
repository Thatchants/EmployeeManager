package sandbox.book.employeemanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@IdClass(SupervisorEmployeeLinkId.class)
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SupervisorEmployeeLink {

    @Id
    @ManyToOne
    @JoinColumn(name = "supervisor_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"position", "creationDate", "supervisorLink", "employeeLinks"})
    private Employee supervisor;

    @Id
    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", unique = true)
    @JsonIgnoreProperties({"position", "creationDate", "supervisorLink", "employeeLinks"})
    private Employee employee;

    @JsonIgnore
    private String feedback;

}