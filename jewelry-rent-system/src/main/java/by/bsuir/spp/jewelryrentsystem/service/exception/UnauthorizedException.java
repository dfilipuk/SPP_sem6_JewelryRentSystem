package by.bsuir.spp.jewelryrentsystem.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(final String message) {
        super(message);
    }

    public UnauthorizedException(final String message, Throwable exception) {
        super(message, exception);
    }
}
