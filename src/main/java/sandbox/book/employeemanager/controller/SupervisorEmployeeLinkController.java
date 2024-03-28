package sandbox.book.employeemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sandbox.book.employeemanager.service.SupervisorEmployeeLinkService;
import sandbox.book.employeemanager.service.error.CircularSupervisionException;
import sandbox.book.employeemanager.service.error.EmployeeNotFoundException;
import sandbox.book.employeemanager.service.error.ExtraSupervisorException;
import sandbox.book.employeemanager.service.error.SupervisorEmployeeLinkNotFoundException;

@RestController
@RequestMapping(value = "/supervisor-link")
public class SupervisorEmployeeLinkController {

    private final SupervisorEmployeeLinkService supervisorEmployeeLinkService;

    @Autowired
    public SupervisorEmployeeLinkController(SupervisorEmployeeLinkService supervisorEmployeeLinkService){
        this.supervisorEmployeeLinkService = supervisorEmployeeLinkService;
    }

    @PostMapping
    public ResponseEntity<?> createLink(@RequestParam() Long supervisorId,
                                        @RequestParam() Long employeeId) {
        try{
            supervisorEmployeeLinkService.createNewSupervisorEmployeeLink(supervisorId, employeeId);
            return ResponseEntity.ok().body("Successfully created new supervisor employee link");
        } catch (ExtraSupervisorException | CircularSupervisionException | EmployeeNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{employeeId}")
    public ResponseEntity<?> updateLink(@PathVariable Long employeeId,
                                        @RequestParam Long oldSupervisorId,
                                        @RequestParam Long newSupervisorId) {
        try {
            supervisorEmployeeLinkService.updateSupervisorEmployeeLink(employeeId, oldSupervisorId, newSupervisorId);
            return ResponseEntity.ok().body("Successfully updated supervisor for employee");
        } catch (ExtraSupervisorException | CircularSupervisionException | EmployeeNotFoundException | SupervisorEmployeeLinkNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<?> deleteLink(@PathVariable Long employeeId) {
        try {
            supervisorEmployeeLinkService.deleteSupervisorEmployeeLink(employeeId);
            return ResponseEntity.ok().body("Successfully deleted supervisor employee link");
        } catch (SupervisorEmployeeLinkNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
