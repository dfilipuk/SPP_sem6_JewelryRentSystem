package by.bsuir.spp.jewelryrentsystem.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends RuntimeException {
    public UnprocessableEntityException(final String message) {
        super(message);
    }

    public UnprocessableEntityException(final String message, Throwable exception) {
        super(message, exception);
    }
}
