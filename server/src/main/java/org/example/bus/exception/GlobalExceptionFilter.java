package org.example.bus.exception;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author lakithaprabudh
 */
@WebFilter("/*")
public class GlobalExceptionFilter implements Filter {
    private static final Logger logger = Logger.getLogger(GlobalExceptionFilter.class.getName());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException {
        try {
            chain.doFilter(request, response);
        } catch (BadRequestException e) {
            handleError((HttpServletResponse) response, HttpServletResponse.SC_BAD_REQUEST, e.getErrors());
        } catch (Exception e) {
            logger.severe("Unhandled exception: " + e.getMessage());
            handleError((HttpServletResponse) response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    Collections.singletonList("Internal server error"));
        }
    }

    private void handleError(HttpServletResponse response, int statusCode, List<String> errors) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");

        String json = objectMapper.writeValueAsString(Collections.singletonMap("errors", errors));
        response.getWriter().write(json);
        response.getWriter().flush();
    }
}
