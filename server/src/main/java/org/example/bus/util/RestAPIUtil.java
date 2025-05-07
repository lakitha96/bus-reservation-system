package org.example.bus.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author lakithaprabudh
 */
public class RestAPIUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void sendJsonResponse(HttpServletResponse resp, int status, Object body) throws IOException {
        resp.setStatus(status);
        resp.setContentType("application/json");

        String json = objectMapper.writeValueAsString(body);
        PrintWriter out = resp.getWriter();
        out.write(json);
        out.flush();
    }
}
