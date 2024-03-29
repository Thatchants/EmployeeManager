package sandbox.book.employeemanager.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sandbox.book.employeemanager.dto.AddEmployeeDto;
import sandbox.book.employeemanager.model.Employee;
import sandbox.book.employeemanager.service.EmployeeService;
import sandbox.book.employeemanager.service.error.EmployeeNotFoundException;

import java.util.List;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {

    Logger log = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<?> getEmployees(){
        List<Employee> employees;
        try{
            employees = employeeService.getAllEmployees();
            return ResponseEntity.ok().body(employees);
        } catch (Exception e){
            return ResponseEntity.internalServerError().body("There was an internal server error while processing your request.");
        }
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody AddEmployeeDto addEmployeeDto){
        try{
            Employee employee = employeeService.createEmployee(addEmployeeDto);
            addEmployeeDto.setId(employee.getId());
            return ResponseEntity.ok().body(addEmployeeDto);
        } catch (Exception e){
            log.error("Unexpected error", e);
            return ResponseEntity.internalServerError().body("There was an internal server error while processing your request.");
        }
    }

    @PatchMapping("/{employeeId}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long employeeId, @Valid @RequestBody AddEmployeeDto addEmployeeDto){
        try{
            employeeService.updateEmployee(employeeId, addEmployeeDto);
            return ResponseEntity.ok().body("Successfully created an employee");
        } catch (EmployeeNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long employeeId){
        try{
            employeeService.deleteEmployee(employeeId);
            return ResponseEntity.ok().body("Successfully deleted an employee");
        } catch (EmployeeNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
