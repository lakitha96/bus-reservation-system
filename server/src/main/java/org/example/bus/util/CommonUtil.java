package org.example.bus.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author lakithaprabudh
 */
public class CommonUtil {
    public static String buildKey(String origin, String destination) {
        return origin + "-" + destination;
    }

    public static String generateTicketNumber() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public static List<String> findAvailableSeats(List<String> allSeats, Set<String> bookedSeats, int requiredCount) {
        List<String> available = new ArrayList<>();
        for (String seatId : allSeats) {
            if (!bookedSeats.contains(seatId)) {
                available.add(seatId);
                if (available.size() == requiredCount) break;
            }
        }
        return available;
    }
}