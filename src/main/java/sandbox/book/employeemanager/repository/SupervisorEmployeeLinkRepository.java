package sandbox.book.employeemanager.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sandbox.book.employeemanager.model.SupervisorEmployeeLink;

import java.util.Optional;

@Repository
public interface SupervisorEmployeeLinkRepository extends CrudRepository<SupervisorEmployeeLink, Long> {

    Optional<SupervisorEmployeeLink> findByEmployeeIdAndSupervisorId(Long employeeId, Long supervisorId);

    Optional<SupervisorEmployeeLink> findByEmployeeId(Long employeeId);

}
