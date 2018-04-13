package by.bsuir.spp.jewelryrentsystem.security.service;

import by.bsuir.spp.jewelryrentsystem.model.Employee;
import by.bsuir.spp.jewelryrentsystem.repository.EmployeeRepository;
import by.bsuir.spp.jewelryrentsystem.security.model.JwtUserDetails;
import by.bsuir.spp.jewelryrentsystem.service.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findFirstByLogin(username);

        if (employee != null) {
            return new JwtUserDetails(employee);
        } else {
            throw new UnauthorizedException("Invalid login or password");
        }
    }
}
