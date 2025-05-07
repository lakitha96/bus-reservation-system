package org.example.bus.api;

import org.example.bus.AppInitializer;
import org.example.bus.model.AvailabilityRequest;
import org.example.bus.model.AvailabilityResponse;
import org.example.bus.service.AvailabilityService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.example.bus.util.RestAPIUtil.sendJsonResponse;

/**
 * @author lakithaprabudh
 */
@WebServlet("/api/v1/availability")
public class AvailabilityServlet extends HttpServlet {

    private static final AvailabilityService availabilityService = AppInitializer.availabilityService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String origin = req.getParameter("origin");
        String destination = req.getParameter("destination");
        String passengerStr = req.getParameter("passengers");

        int passengers;
        try {
            passengers = Integer.parseInt(passengerStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid passenger count: must be an integer");
        }

        AvailabilityRequest request = new AvailabilityRequest(origin, destination, passengers);
        AvailabilityResponse response = availabilityService.checkAvailability(request);

        sendJsonResponse(resp, HttpServletResponse.SC_OK, response);
    }
}