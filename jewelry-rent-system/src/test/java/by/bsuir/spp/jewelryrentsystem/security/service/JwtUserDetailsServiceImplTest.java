package by.bsuir.spp.jewelryrentsystem.security.service;

import by.bsuir.spp.jewelryrentsystem.model.Employee;
import by.bsuir.spp.jewelryrentsystem.repository.EmployeeRepository;
import by.bsuir.spp.jewelryrentsystem.service.exception.UnauthorizedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JwtUserDetailsServiceImplTest {
    @TestConfiguration
    static class JwtUserDetailsServiceImplTestContextConfiguration {
        @Autowired
        private EmployeeRepository employeeRepository;

        @Bean
        public UserDetailsService userDetailsService() {
            return new JwtUserDetailsServiceImpl(employeeRepository);
        }
    }

    @Autowired
    private UserDetailsService userDetailsService;

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

    @Test
    public void whenValidUserName_thenUserDetailsShouldBeFound() {
        String userName = "test";
        UserDetails found = userDetailsService.loadUserByUsername(userName);

        assert (found.getUsername())
                .equals(userName);
    }

    @Test(expected = UnauthorizedException.class)
    public void whenNotValidUserName_thenUnauthorizedException() {
        String userName = "ttt";
        UserDetails found = userDetailsService.loadUserByUsername(userName);

        assert (found == null);
    }
}
