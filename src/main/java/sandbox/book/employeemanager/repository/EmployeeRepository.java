package sandbox.book.employeemanager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sandbox.book.employeemanager.model.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository  extends CrudRepository<Employee, Long> {

    List<Employee> findAll();
}
