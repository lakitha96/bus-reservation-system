package org.example.bus.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bus.AppInitializer;
import org.example.bus.model.BookingRequest;
import org.example.bus.model.BookingResponse;
import org.example.bus.service.BookingService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.example.bus.util.RestAPIUtil.sendJsonResponse;

/**
 * @author lakithaprabudh
 */
@WebServlet("/api/v1/bookings")
public class BookingServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final BookingService bookingService = AppInitializer.bookingService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BookingRequest request;

        try {
            request = objectMapper.readValue(req.getInputStream(), BookingRequest.class);
        } catch (IOException e) {
            sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, Collections.singletonList("Invalid request body"));
            return;
        }

        BookingResponse response = bookingService.bookSeats(request);
        sendJsonResponse(resp, HttpServletResponse.SC_OK, response);
    }

    private void sendErrorResponse(HttpServletResponse resp, int status, List<String> errors) throws IOException {
        sendJsonResponse(resp, status, Collections.singletonMap("errors", errors));
    }
}