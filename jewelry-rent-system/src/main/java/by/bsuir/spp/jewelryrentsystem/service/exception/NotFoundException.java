package by.bsuir.spp.jewelryrentsystem.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(final String message) {
        super(message);
    }

    public NotFoundException(final String message, Throwable exception) {
        super(message, exception);
    }
}
