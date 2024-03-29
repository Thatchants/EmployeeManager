package sandbox.book.employeemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sandbox.book.employeemanager.dto.AddEmployeeDto;
import sandbox.book.employeemanager.model.Employee;
import sandbox.book.employeemanager.repository.EmployeeRepository;
import sandbox.book.employeemanager.service.error.EmployeeNotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    @Transactional
    public Employee createEmployee(AddEmployeeDto addEmployeeDto) {
        Employee newEmployee = Employee.builder()
                .firstName(addEmployeeDto.getFirstName())
                .lastName(addEmployeeDto.getLastName())
                .position(addEmployeeDto.getPosition())
                .creationDate(LocalDate.now())
                .build();

        return employeeRepository.save(newEmployee);
    }

    @Transactional
    public void updateEmployee(Long employeeId, AddEmployeeDto addEmployeeDto) throws EmployeeNotFoundException {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        employee.setFirstName(addEmployeeDto.getFirstName());
        employee.setLastName(addEmployeeDto.getLastName());
        employee.setPosition(addEmployeeDto.getPosition());

        employeeRepository.save(employee);
    }

    @Transactional
    public void deleteEmployee(Long employeeId) throws EmployeeNotFoundException {
        if(employeeRepository.existsById(employeeId)){
            employeeRepository.deleteById(employeeId);
        }else {
            throw new EmployeeNotFoundException(employeeId);
        }
    }
}
