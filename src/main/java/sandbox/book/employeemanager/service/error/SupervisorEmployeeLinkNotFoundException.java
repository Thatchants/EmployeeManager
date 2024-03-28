package sandbox.book.employeemanager.service.error;

public class SupervisorEmployeeLinkNotFoundException extends Exception {
    public SupervisorEmployeeLinkNotFoundException(Long employeeId){
        super(String.format("Failed to update supervisor link for employee %s. " +
                "There is no existing link to be updated", employeeId));
    }
}
