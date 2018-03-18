package by.bsuir.spp.jewelryrentsystem.service;

import by.bsuir.spp.jewelryrentsystem.dto.EmployeeDto;
import by.bsuir.spp.jewelryrentsystem.dto.login.LoginRequestDto;
import by.bsuir.spp.jewelryrentsystem.dto.login.LoginResponseDto;

public interface AuthenticationService {
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    EmployeeDto getCurrentUser();
}
