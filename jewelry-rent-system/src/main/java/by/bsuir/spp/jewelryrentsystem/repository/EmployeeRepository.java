package by.bsuir.spp.jewelryrentsystem.repository;

import by.bsuir.spp.jewelryrentsystem.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findFirstByLogin(String login);
}
