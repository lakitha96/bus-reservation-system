package org.example.bus.client;


import org.example.bus.util.ApiClient;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author lakithaprabudh
 */
public class AvailabilityApiClient {
    private static final Logger logger = Logger.getLogger(AvailabilityApiClient.class.getName());

    public static void callAvailabilityAPI() {
        String origin = "A";
        String destination = "D";
        int passengers = 2;

        String endpoint = String.format(
                "/v1/availability?origin=%s&destination=%s&passengers=%d",
                origin, destination, passengers
        );

        try {
            String response = ApiClient.get(endpoint);
            logger.info("Availability Response: " + response);
        } catch (IOException e) {
            logger.severe("Availability check failed: " + e.getMessage());
        }
    }
}