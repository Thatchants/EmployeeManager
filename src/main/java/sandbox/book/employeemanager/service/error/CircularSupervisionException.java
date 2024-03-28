package sandbox.book.employeemanager.service.error;

public class CircularSupervisionException extends Exception{

    public CircularSupervisionException(String message){
        super(message);
    }
}
