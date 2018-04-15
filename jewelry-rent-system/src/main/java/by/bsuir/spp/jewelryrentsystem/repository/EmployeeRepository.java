package by.bsuir.spp.jewelryrentsystem.repository;

import by.bsuir.spp.jewelryrentsystem.model.Branch;
import by.bsuir.spp.jewelryrentsystem.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findFirstByLogin(String login);
    List<Employee> findByBranch(Branch branch);
}
