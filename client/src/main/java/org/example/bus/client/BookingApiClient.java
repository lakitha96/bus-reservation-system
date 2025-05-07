package org.example.bus.client;

import org.example.bus.util.ApiClient;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author lakithaprabudh
 */
public class BookingApiClient {
    private static final Logger logger = Logger.getLogger(BookingApiClient.class.getName());

    public static void callBookingAPI() {
        String bookingRequestJson = "{"
                + "\"origin\":\"A\","
                + "\"destination\":\"D\","
                + "\"passengers\":2,"
                + "\"amount\":300"
                + "}";

        try {
            String response = ApiClient.post("/v1/bookings", bookingRequestJson);
            logger.info("Booking Response: " + response);
        } catch (IOException e) {
            logger.severe("Booking failed: " + e.getMessage());
        }
    }
}