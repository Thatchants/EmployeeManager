package sandbox.book.employeemanager.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class SupervisorEmployeeLinkId implements Serializable {

    private Employee supervisor;

    private Employee employee;

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupervisorEmployeeLinkId that = (SupervisorEmployeeLinkId) o;
        return Objects.equals(employee, that.employee) && Objects.equals(supervisor, that.supervisor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supervisor, employee);
    }
}
