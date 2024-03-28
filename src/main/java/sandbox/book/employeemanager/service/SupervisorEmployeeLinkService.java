package sandbox.book.employeemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sandbox.book.employeemanager.model.Employee;
import sandbox.book.employeemanager.model.SupervisorEmployeeLink;
import sandbox.book.employeemanager.repository.EmployeeRepository;
import sandbox.book.employeemanager.repository.SupervisorEmployeeLinkRepository;
import sandbox.book.employeemanager.service.error.CircularSupervisionException;
import sandbox.book.employeemanager.service.error.EmployeeNotFoundException;
import sandbox.book.employeemanager.service.error.ExtraSupervisorException;
import sandbox.book.employeemanager.service.error.SupervisorEmployeeLinkNotFoundException;

import java.time.LocalDate;
import java.util.*;

@Service
public class SupervisorEmployeeLinkService {

    private final SupervisorEmployeeLinkRepository supervisorEmployeeLinkRepository;

    private final EmployeeRepository employeeRepository;

    @Autowired
    public SupervisorEmployeeLinkService(SupervisorEmployeeLinkRepository supervisorEmployeeLinkRepository,
                                         EmployeeRepository employeeRepository){
        this.supervisorEmployeeLinkRepository = supervisorEmployeeLinkRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void createNewSupervisorEmployeeLink(Long supervisorId, Long employeeId) throws ExtraSupervisorException, CircularSupervisionException, EmployeeNotFoundException {
        Employee supervisor = employeeRepository.findById(supervisorId).orElseThrow(() -> new EmployeeNotFoundException(supervisorId));
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        validateNewEmployeeLinks(supervisor, employee);

        SupervisorEmployeeLink link = SupervisorEmployeeLink.builder()
                .supervisor(supervisor)
                .employee(employee)
                .build();
        supervisorEmployeeLinkRepository.save(link);
    }

    @Transactional
    public void updateSupervisorEmployeeLink(Long employeeId, Long oldSupervisorId, Long newSupervisorId) throws ExtraSupervisorException, CircularSupervisionException, EmployeeNotFoundException, SupervisorEmployeeLinkNotFoundException {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        Employee newSupervisor = employeeRepository.findById(newSupervisorId).orElseThrow(() -> new EmployeeNotFoundException(newSupervisorId));

        checkForCircularSupervision(employee, newSupervisor);

        SupervisorEmployeeLink link = supervisorEmployeeLinkRepository.findByEmployeeIdAndSupervisorId(employeeId, oldSupervisorId)
                .orElseThrow(() -> new SupervisorEmployeeLinkNotFoundException(employeeId));

        SupervisorEmployeeLink newLink = SupervisorEmployeeLink.builder()
                .employee(employee)
                .supervisor(newSupervisor)
                .build();

        supervisorEmployeeLinkRepository.delete(link);
        supervisorEmployeeLinkRepository.save(newLink);
    }

    @Transactional
    public void deleteSupervisorEmployeeLink(Long employeeId) throws SupervisorEmployeeLinkNotFoundException {
        SupervisorEmployeeLink link = supervisorEmployeeLinkRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new SupervisorEmployeeLinkNotFoundException(employeeId));

        supervisorEmployeeLinkRepository.delete(link);
    }

    private void validateNewEmployeeLinks(Employee supervisor, Employee employee) throws ExtraSupervisorException, CircularSupervisionException {
        SupervisorEmployeeLink supervisorLink = employee.getSupervisorLink();
        if(supervisorLink != null){
            String message = String.format("%s %s already has a supervisor.", employee.getFirstName(), employee.getLastName());
            throw new ExtraSupervisorException(message);
        }

        checkForCircularSupervision(employee, supervisor);
    }

    public void checkForCircularSupervision(Employee employee, Employee supervisor) throws CircularSupervisionException {
        if(Objects.equals(employee.getId(), supervisor.getId())){
            throw new CircularSupervisionException(String.format("%s %s is not trusted to supervise themself", employee.getFirstName(), employee.getLastName()));
        }
        Employee tmpSupervisor = supervisor;

        while (tmpSupervisor != null) {
            if (Objects.equals(tmpSupervisor.getId(), employee.getId())) {
                String message = String.format("%s %s is already above %s %s in the company hierarchy.",
                        employee.getFirstName(), employee.getLastName(), supervisor.getFirstName(), supervisor.getLastName());
                throw new CircularSupervisionException(message);
            }

            tmpSupervisor = tmpSupervisor.getSupervisorLink() != null ? tmpSupervisor.getSupervisorLink().getSupervisor() : null;
        }
    }
}
