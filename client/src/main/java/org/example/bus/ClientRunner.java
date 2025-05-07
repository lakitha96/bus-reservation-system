package org.example.bus;

import org.example.bus.client.AvailabilityApiClient;
import org.example.bus.client.BookingApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

/**
 * @author lakithaprabudh
 */
public class ClientRunner {
    private static final Logger logger = Logger.getLogger(ClientRunner.class.getName());


    public static void main(String[] args) {
        logger.info("Starting client...");

        AvailabilityApiClient.callAvailabilityAPI();

        int numberOfUsers = 10;
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < numberOfUsers; i++) {
            Future<?> future = executor.submit(BookingApiClient::callBookingAPI);
            futures.add(future);
        }

        executor.shutdown();

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                logger.warning("Booking task failed: " + e.getMessage());
            }
        }

        logger.info("Client finished.");
    }
}