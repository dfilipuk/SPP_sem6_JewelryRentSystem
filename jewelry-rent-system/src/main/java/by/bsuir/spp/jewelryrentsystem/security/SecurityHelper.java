package by.bsuir.spp.jewelryrentsystem.security;

import by.bsuir.spp.jewelryrentsystem.service.exception.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityHelper {
    public static Authentication getAuthenticationWithCheck() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean checkAuthenticationExists = authentication != null && authentication.isAuthenticated();

        if (!checkAuthenticationExists) {
            throw new UnauthorizedException("Authentication failed");
        }

        return authentication;
    }
}
