package backendBME.repository;

import backendBME.model.Employee;
import backendBME.enums.EmployeeRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    public Employee findByEmail(String email);
    public boolean existsByEmail(String email);
    public List<Employee> findAllByEnabled(boolean b);
    public void deleteByEmail(String email);
    @Query(value = "SELECT role from project.employees where id = ?1 ;", nativeQuery = true)
    public EmployeeRole getRole(Long id);
}
