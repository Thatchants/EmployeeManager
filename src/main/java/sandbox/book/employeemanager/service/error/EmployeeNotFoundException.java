package sandbox.book.employeemanager.service.error;

public class EmployeeNotFoundException extends Exception{

    public EmployeeNotFoundException(Long id){
        super(String.format("Employee not found with id: %s", id));
    }
}
