package by.bsuir.spp.jewelryrentsystem.security.service;

import by.bsuir.spp.jewelryrentsystem.model.Employee;
import by.bsuir.spp.jewelryrentsystem.repository.EmployeeRepository;
import by.bsuir.spp.jewelryrentsystem.security.exception.ExpiredTokenAuthenticationException;
import by.bsuir.spp.jewelryrentsystem.security.exception.InvalidTokenAuthenticationException;
import by.bsuir.spp.jewelryrentsystem.security.model.JwtToken;
import by.bsuir.spp.jewelryrentsystem.security.model.JwtTokenPayload;
import by.bsuir.spp.jewelryrentsystem.security.model.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private static final long MILLIS_IN_SECOND = 1000L;

    private final EmployeeRepository employeeRepository;
    private final AuthenticationHelper authenticationHelper;

    @Override
    public Authentication authenticate(final Authentication authRequest) {
        String token = StringUtils.trimToNull((String) authRequest.getCredentials());
        JwtTokenPayload tokenPayload = authenticationHelper.decodeToken(token);

        checkIsExpired(tokenPayload.getExp());

        Long employeeEntityId = tokenPayload.getUserId();
        if (employeeEntityId == null) {
            throw new InvalidTokenAuthenticationException("Token does not contain an employee id");
        }

        Employee employee = employeeRepository.findOne(employeeEntityId);

        if (employee == null) {
            throw new InvalidTokenAuthenticationException("Token does not contain existed employee id");
        }

        JwtUserDetails userDetails = new JwtUserDetails(employee);

        return new JwtToken(userDetails);
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return JwtToken.class.isAssignableFrom(authentication);
    }

    private void checkIsExpired(final Long tokenExpirationTime) {
        if ((System.currentTimeMillis() / MILLIS_IN_SECOND) > tokenExpirationTime) {
            throw new ExpiredTokenAuthenticationException();
        }
    }
}
