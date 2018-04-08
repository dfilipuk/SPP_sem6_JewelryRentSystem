package by.bsuir.spp.jewelryrentsystem.repository;

import by.bsuir.spp.jewelryrentsystem.model.Branch;
import by.bsuir.spp.jewelryrentsystem.model.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
        Employee employee = new Employee();
        employee.setLogin("test");

        Branch branch = new Branch();
        employee.setBranch(branch);

        entityManager.persist(branch);
        entityManager.flush();

        entityManager.persist(employee);
        entityManager.flush();

        // when
        Employee found = employeeRepository.findFirstByLogin(employee.getLogin());

        // then
        assert (found.getLogin()).equals(employee.getLogin());
    }

    @Test
    public void whenFindByName_thenReturnNull() {
        // given
        Employee employee = new Employee();
        employee.setLogin("test");

        Branch branch = new Branch();
        employee.setBranch(branch);

        entityManager.persist(branch);
        entityManager.flush();

        entityManager.persist(employee);
        entityManager.flush();

        // when
        Employee found = employeeRepository.findFirstByLogin("ivan");

        // then
        assert (found == null);
    }
}
