package org.example.bus.dao;

import org.example.bus.entity.Booking;

import java.util.*;

/**
 * @author lakithaprabudh
 */
public class BookingDAO {
    private static final List<Booking> BOOKINGS = new ArrayList<>();
    private static final List<String> STOPS = Arrays.asList("A", "B", "C", "D");


    public void save(Booking booking) {
        BOOKINGS.add(booking);
    }

    public void clear() {
        BOOKINGS.clear();
    }

    public Set<String> loadBookedSeatIds(String journeyKey) {
        Set<String> result = new HashSet<>();
        List<String> currentSegments = getSegments(journeyKey); // break current journey into segments

        for (Booking booking : BOOKINGS) {
            List<String> bookedSegments = getSegments(booking.getJourneyKey()); // Break booked journey into segments

            // check if any segment is exactly the same (including direction)
            for (String segment : currentSegments) {
                if (bookedSegments.contains(segment)) {
                    result.addAll(booking.getSeatNumbers()); // If overlap found, add booked seats
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Breaks a journey like "A-D" or "D-A" into individual segments.
     * Includes direction, so A-B not equal B-A.
     */
    private List<String> getSegments(String journeyKey) {
        String[] parts = journeyKey.split("-");
        int start = STOPS.indexOf(parts[0]);
        int end = STOPS.indexOf(parts[1]);

        List<String> segments = new ArrayList<>();

        if (start < end) {
            for (int i = start; i < end; i++) {
                segments.add(STOPS.get(i) + "-" + STOPS.get(i + 1)); // forward direction
            }
        } else {
            for (int i = start; i > end; i--) {
                segments.add(STOPS.get(i) + "-" + STOPS.get(i - 1)); // reverse direction
            }
        }

        return segments;
    }
}