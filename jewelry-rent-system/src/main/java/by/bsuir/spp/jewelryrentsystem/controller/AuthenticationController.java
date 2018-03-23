package by.bsuir.spp.jewelryrentsystem.controller;

import by.bsuir.spp.jewelryrentsystem.dto.EmployeeDto;
import by.bsuir.spp.jewelryrentsystem.dto.login.LoginRequestDto;
import by.bsuir.spp.jewelryrentsystem.dto.login.LoginResponseDto;
import by.bsuir.spp.jewelryrentsystem.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/login")
    public LoginResponseDto login(@Validated @RequestBody LoginRequestDto loginRequestDto) {
        return authenticationService.login(loginRequestDto);
    }

    @GetMapping(value = "/user")
    @PreAuthorize("hasAnyRole('ROLE_SELLER', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    public EmployeeDto getCurrentUser() {
        return authenticationService.getCurrentUser();
    }
}
