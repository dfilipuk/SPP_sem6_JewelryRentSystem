package by.bsuir.spp.jewelryrentsystem.service;

import by.bsuir.spp.jewelryrentsystem.dto.EmployeeDto;
import by.bsuir.spp.jewelryrentsystem.model.Employee;
import by.bsuir.spp.jewelryrentsystem.repository.EmployeeRepository;
import by.bsuir.spp.jewelryrentsystem.service.exception.UnauthorizedException;
import by.bsuir.spp.jewelryrentsystem.service.impl.AuthenticationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AuthenticationServiceImplTest {
    @TestConfiguration
    static class AuthenticationServiceImplTestContextConfiguration {

        @Autowired
        private EmployeeRepository employeeRepository;

        @Bean
        public AuthenticationService authenticationService() {
            return new AuthenticationServiceImpl(employeeRepository, null, null);
        }
    }

    @Autowired
    private AuthenticationService authenticationService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() {
        Employee employee = new Employee();
        employee.setLogin("test");
        employee.setRole("ROLE_SELLER");

        Mockito.when(employeeRepository.findFirstByLogin(employee.getLogin()))
                .thenReturn(employee);
    }

    @Test(expected = UnauthorizedException.class)
    public void whenValid_thenOk() {
        EmployeeDto found = authenticationService.getCurrentUser();

        assert (found == null);
    }
}
