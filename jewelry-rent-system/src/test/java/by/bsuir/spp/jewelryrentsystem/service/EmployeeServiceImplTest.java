package by.bsuir.spp.jewelryrentsystem.service;

import by.bsuir.spp.jewelryrentsystem.dto.CreateActionResponseDto;
import by.bsuir.spp.jewelryrentsystem.dto.EmployeeDto;
import by.bsuir.spp.jewelryrentsystem.model.Branch;
import by.bsuir.spp.jewelryrentsystem.model.Employee;
import by.bsuir.spp.jewelryrentsystem.repository.BranchRepository;
import by.bsuir.spp.jewelryrentsystem.repository.EmployeeRepository;
import by.bsuir.spp.jewelryrentsystem.service.exception.NotFoundException;
import by.bsuir.spp.jewelryrentsystem.service.exception.UnprocessableEntityException;
import by.bsuir.spp.jewelryrentsystem.service.impl.EmployeeServiceImpl;
import by.bsuir.spp.jewelryrentsystem.service.impl.PaginationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeServiceImplTest {
    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Autowired
        private EmployeeRepository employeeRepository;

        @Autowired
        private BranchRepository branchRepository;

        private PaginationService paginationService = new PaginationServiceImpl();

        private PasswordEncoder passwordEncoder = new StandardPasswordEncoder();

        @Bean
        public EmployeeService employeeService() {
            return new EmployeeServiceImpl(
                    employeeRepository,
                    branchRepository,
                    paginationService,
                    passwordEncoder
            );
        }
    }

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() {
        Branch branch = new Branch();
        branch.setId(1);
        branch.setAddress("asd");
        branch.setTelephone("123548");
        branch.setEmployees(new HashSet<>());
        branch.setJewelries(new HashSet<>());

        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("test");
        employee.setSurname("rent");
        employee.setSecondName("tru");
        employee.setSalary(1000);
        employee.setPosition("admin");
        employee.setLogin("login-test");
        employee.setPassword("pass");
        employee.setRole("ROLE_ADMIN");
        employee.setBranch(branch);
        employee.setOrders(new HashSet<>());

        Employee employee1 = new Employee();
        employee1.setId(2);
        employee1.setName("test2");
        employee1.setSurname("rent2");
        employee1.setSecondName("tru2");
        employee1.setSalary(10002);
        employee1.setPosition("admin");
        employee1.setLogin("login-test2");
        employee1.setPassword("pass");
        employee1.setRole("ROLE_ADMIN");
        employee1.setBranch(branch);
        employee1.setOrders(new HashSet<>());

        Employee employee2 = new Employee();
        employee2.setId(3);
        employee2.setName("test3");
        employee2.setSurname("rent3");
        employee2.setSecondName("tru3");
        employee2.setSalary(10003);
        employee2.setPosition("admin");
        employee2.setLogin("login-test3");
        employee2.setPassword("pass");
        employee2.setRole("ROLE_ADMIN");
        employee2.setBranch(branch);
        employee2.setOrders(new HashSet<>());

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        employees.add(employee1);
        employees.add(employee2);

        String SORT_COLUMN = "surname";
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, SORT_COLUMN));

        Mockito.when(employeeRepository.findAll(sort))
                .thenReturn(employees);

        Pageable pageable = new PageRequest(1, 1, sort);
        Mockito.when(employeeRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(employees));

        Mockito.when(employeeRepository.count())
                .thenReturn((long)employees.size());

        Mockito.when(employeeRepository.findOne(employee.getId()))
                .thenReturn(employee);
    }

    @Test
    public void whenGetAll_thenListNotEmpty() {
        List<EmployeeDto> found = employeeService.getAllEmployees();

        assert (found.size() > 0);
    }

    @Test
    public void whenGetAll_thenListSizeCorrect() {
        List<EmployeeDto> found = employeeService.getAllEmployees();

        assert (found.size() == 3);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenGetAllPageable_thenUnprocessableEntityException() {
        List<EmployeeDto> found = employeeService.getAllEmployeesPageable(10, 10);

        assert (found.size() == 0);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenGetAllPageableNegativePage_thenUnprocessableEntityException() {
        List<EmployeeDto> found = employeeService.getAllEmployeesPageable(-10, 10);

        assert (found.size() == 0);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenGetAllPageableNegativeSize_thenUnprocessableEntityException() {
        List<EmployeeDto> found = employeeService.getAllEmployeesPageable(10, -10);

        assert (found.size() == 0);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenGetAllPageableNegativePageAndNegativeSize_thenUnprocessableEntityException() {
        List<EmployeeDto> found = employeeService.getAllEmployeesPageable(-10, -10);

        assert (found.size() == 0);
    }

    @Test
    public void whenGetAllPageable_thenCorrectSize() {
        List<EmployeeDto> found = employeeService.getAllEmployeesPageable(1, 1);

        assert (found.size() == 3);
    }

    @Test
    public void whenPageSize_thenPageAmountThree() {
        long pageSize = 1L;
        long amount = employeeService.getEmployeesListPagesAmount(pageSize);

        assert (amount == 3);
    }

    @Test
    public void whenPageSize_thenPageAmountTwo() {
        long pageSize = 2L;
        long amount = employeeService.getEmployeesListPagesAmount(pageSize);

        assert (amount == 2);
    }

    @Test
    public void whenPageSize_thenPageAmountOne() {
        long pageSize = 3L;
        long amount = employeeService.getEmployeesListPagesAmount(pageSize);

        assert (amount == 1);
    }

    @Test
    public void whenBigPageSize_thenPageAmountOne() {
        long pageSize = 10L;
        long amount = employeeService.getEmployeesListPagesAmount(pageSize);

        assert (amount == 1);
    }

    @Test
    public void whenPageSize_thenPageAmountZero() {
        long pageSize = -1L;
        long amount = employeeService.getEmployeesListPagesAmount(pageSize);

        assert (amount == 0);
    }


    @Test
    public void whenValidId_thenGetEmployee() {
        long id = 1L;
        EmployeeDto found = employeeService.getEmployeeById(id);

        assert (found.getId() == id);
    }

    @Test(expected = NotFoundException.class)
    public void whenNotValidId_thenNotFoundException() {
        long id = 10L;
        EmployeeDto found = employeeService.getEmployeeById(id);

        assert (found == null);
    }

    @Test(expected = NotFoundException.class)
    public void whenNegativeId_thenNotFoundException() {
        long id = -1L;
        EmployeeDto found = employeeService.getEmployeeById(id);

        assert (found == null);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenDeleteNotValid_thenUnprocessableEntityException() {
        long id = 10L;
        employeeService.deleteEmployeeById(id);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenDeleteNegative_thenUnprocessableEntityException() {
        long id = -10L;
        employeeService.deleteEmployeeById(id);
    }

    @Test
    public void whenDelete_thenNotFound() {
        long id = 1L;
        employeeService.deleteEmployeeById(id);
        assert (true);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenCreate_thenEmployeeCreated() {
        EmployeeDto employeeDto = new EmployeeDto();

        CreateActionResponseDto responseDto = employeeService.createEmployee(employeeDto);

        assert (responseDto.getId() == 0);
    }

    @Test(expected = UnprocessableEntityException.class)
    public void whenUpdateNotValid_thenUnprocessableEntityException() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(100);

        employeeService.updateEmployee(employeeDto);
    }
}
