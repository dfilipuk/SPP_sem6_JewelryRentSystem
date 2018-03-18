package by.bsuir.spp.jewelryrentsystem.security.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestAccessDeniedHandler implements AccessDeniedHandler {
    private static final String DENIED_MESSAGE = "You can not perform this operation";

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response,
                       final AccessDeniedException exception) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, DENIED_MESSAGE);
    }
}
