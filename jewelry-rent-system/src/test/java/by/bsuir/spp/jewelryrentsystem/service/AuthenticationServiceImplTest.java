package by.bsuir.spp.jewelryrentsystem.service;

import by.bsuir.spp.jewelryrentsystem.dto.EmployeeDto;
import by.bsuir.spp.jewelryrentsystem.dto.login.LoginRequestDto;
import by.bsuir.spp.jewelryrentsystem.dto.login.LoginResponseDto;
import by.bsuir.spp.jewelryrentsystem.model.Branch;
import by.bsuir.spp.jewelryrentsystem.model.Employee;
import by.bsuir.spp.jewelryrentsystem.repository.EmployeeRepository;
import by.bsuir.spp.jewelryrentsystem.security.service.AuthenticationHelper;
import by.bsuir.spp.jewelryrentsystem.service.exception.UnauthorizedException;
import by.bsuir.spp.jewelryrentsystem.service.impl.AuthenticationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AuthenticationServiceImplTest {
    @TestConfiguration
    static class AuthenticationServiceImplTestContextConfiguration {

        @Autowired
        private EmployeeRepository employeeRepository;

        private ObjectMapper objectMapper = new ObjectMapper();
        private AuthenticationHelper authenticationHelper = new AuthenticationHelper(objectMapper);

        private PasswordEncoder passwordEncoder = new StandardPasswordEncoder();

        @Bean
        public AuthenticationService authenticationService() {
            return new AuthenticationServiceImpl(employeeRepository, authenticationHelper, passwordEncoder);
        }
    }

    @Autowired
    private AuthenticationService authenticationService;

    private PasswordEncoder passwordEncoder = new StandardPasswordEncoder();

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
        employee.setPassword(passwordEncoder.encode("pass"));
        employee.setRole("ROLE_ADMIN");
        employee.setBranch(branch);
        employee.setOrders(new HashSet<>());

        Mockito.when(employeeRepository.findFirstByLogin(employee.getLogin()))
                .thenReturn(employee);
    }

    @Test
    public void whenLogin_thenGetToken() {
        LoginRequestDto loginRequestDto = new LoginRequestDto("login-test", "pass");
        LoginResponseDto loginResponseDto = authenticationService.login(loginRequestDto);

        assert (!loginResponseDto.getToken().equals(""));
    }

    @Test(expected = UnauthorizedException.class)
    public void whenNotFoundEmployee_thenUnauthorizedException() {
        LoginRequestDto loginRequestDto = new LoginRequestDto("fed", "555");
        LoginResponseDto loginResponseDto = authenticationService.login(loginRequestDto);

        assert (!loginResponseDto.getToken().equals(""));
    }

    @Test(expected = UnauthorizedException.class)
    public void whenNotMatchesPassword_thenUnauthorizedException() {
        LoginRequestDto loginRequestDto = new LoginRequestDto("login-test", "555");
        LoginResponseDto loginResponseDto = authenticationService.login(loginRequestDto);

        assert (!loginResponseDto.getToken().equals(""));
    }

    @Test(expected = UnauthorizedException.class)
    public void whenNotValid_thenUnauthorizedException() {
        EmployeeDto found = authenticationService.getCurrentUser();

        assert (found == null);
    }
}
