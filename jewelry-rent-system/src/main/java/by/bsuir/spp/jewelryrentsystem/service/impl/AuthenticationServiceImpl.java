package by.bsuir.spp.jewelryrentsystem.service.impl;

import by.bsuir.spp.jewelryrentsystem.dto.EmployeeDto;
import by.bsuir.spp.jewelryrentsystem.dto.login.LoginRequestDto;
import by.bsuir.spp.jewelryrentsystem.dto.login.LoginResponseDto;
import by.bsuir.spp.jewelryrentsystem.model.Employee;
import by.bsuir.spp.jewelryrentsystem.repository.EmployeeRepository;
import by.bsuir.spp.jewelryrentsystem.security.SecurityHelper;
import by.bsuir.spp.jewelryrentsystem.security.service.AuthenticationHelper;
import by.bsuir.spp.jewelryrentsystem.service.AuthenticationService;

import by.bsuir.spp.jewelryrentsystem.service.exception.UnauthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private EmployeeRepository employeeRepository;
    private AuthenticationHelper authenticationHelper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Employee employee = employeeRepository.findFirstByLogin(loginRequestDto.getLogin());

        if (employee == null) {
            throw new UnauthorizedException("Employee with specified login does not exists");
        }

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), employee.getPassword())) {
            throw new UnauthorizedException("Invalid password");
        }

        String token = authenticationHelper.generateToken(employee.getId());
        return new LoginResponseDto(token);
    }

    @Override
    public EmployeeDto getCurrentUser() {
        Authentication authentication = SecurityHelper.getAuthenticationWithCheck();
        String login = authentication.getName();
        Employee employee = employeeRepository.findFirstByLogin(login);

        if (employee == null) {
            throw new UnauthorizedException("Not authorized");
        }

        return new EmployeeDto(
                employee.getId(),
                employee.getName(),
                employee.getSurname(),
                employee.getSecondName(),
                employee.getSalary(),
                employee.getPosition(),
                employee.getLogin(),
                "",
                employee.getRole(),
                employee.getBranch().getId()
        );
    }
}
