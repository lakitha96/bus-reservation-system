package org.example.bus.service;

import org.example.bus.dao.BookingDAO;
import org.example.bus.dao.JourneyDAO;
import org.example.bus.dao.SeatDAO;
import org.example.bus.entity.Booking;
import org.example.bus.entity.Journey;
import org.example.bus.entity.Seat;
import org.example.bus.exception.BadRequestException;
import org.example.bus.model.AvailabilityRequest;
import org.example.bus.model.AvailabilityResponse;
import org.example.bus.service.impl.AvailabilityServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author lakithaprabudh
 */
public class AvailabilityServiceImplTest {
    private List<Seat> seats;
    private BookingDAO bookingDAO;
    private AvailabilityServiceImpl availabilityService;

    @Before
    public void setUp() {
        SeatDAO seatDAO = new SeatDAO();
        JourneyDAO journeyDAO = new JourneyDAO();
        bookingDAO = new BookingDAO();

        bookingDAO.clear();

        // 40 seats (1A to 10D)
        seats = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            seats.add(new Seat(i + "A"));
            seats.add(new Seat(i + "B"));
            seats.add(new Seat(i + "C"));
            seats.add(new Seat(i + "D"));
        }
        seatDAO.initialize(seats);

        // define all journeys
        journeyDAO.initialize(Arrays.asList(
                new Journey("A", "D", 150),
                new Journey("A", "C", 150),
                new Journey("B", "D", 150),
                new Journey("C", "D", 150)
        ));

        availabilityService = new AvailabilityServiceImpl(seatDAO, journeyDAO, bookingDAO);
    }

    @Test
    public void testValidAvailabilityCheck() {
        AvailabilityRequest request = new AvailabilityRequest("A", "D", 2);
        AvailabilityResponse response = availabilityService.checkAvailability(request);

        assertEquals(2, response.getAvailableSeats().size());
        assertEquals(300, response.getTotalPrice());
    }

    @Test
    public void testNotEnoughSeats() {
        List<String> seatIds = seats.stream().limit(38).map(Seat::getId).collect(Collectors.toList());
        Booking booking = new Booking("A-D", seatIds, "PREBOOKED-" + seats.size(), 0);
        bookingDAO.save(booking);

        AvailabilityRequest request = new AvailabilityRequest("A", "D", 3);
        AvailabilityResponse response = availabilityService.checkAvailability(request);

        assertTrue(response.getAvailableSeats().isEmpty());
        assertEquals(0, response.getTotalPrice());
    }

    @Test(expected = BadRequestException.class)
    public void testInvalidRoute() {
        AvailabilityRequest request = new AvailabilityRequest("X", "Z", 1);
        availabilityService.checkAvailability(request);
    }

    @Test(expected = BadRequestException.class)
    public void testInvalidRequestNull() {
        availabilityService.checkAvailability(null);
    }

    @Test(expected = BadRequestException.class)
    public void testInvalidRequestSameOriginAndDestination() {
        AvailabilityRequest request = new AvailabilityRequest("A", "A", 1);
        availabilityService.checkAvailability(request);
    }

    @Test(expected = BadRequestException.class)
    public void testInvalidRequestZeroPassengers() {
        AvailabilityRequest request = new AvailabilityRequest("A", "D", 0);
        availabilityService.checkAvailability(request);
    }

    @Test(expected = BadRequestException.class)
    public void testInvalidRequestMinusPassengers() {
        AvailabilityRequest request = new AvailabilityRequest("A", "D", -1);
        availabilityService.checkAvailability(request);
    }

    @Test
    public void testSegmentOverlapBlocksAvailability1() {
        // book all 40 seats from B to D
        List<String> seatIds = seats.stream().map(Seat::getId).collect(Collectors.toList());
        bookingDAO.save(new Booking("B-D", seatIds, "OVERLAP", 0));

        // try to book 2 seats from A to C (overlaps with B-D)
        AvailabilityRequest request = new AvailabilityRequest("A", "C", 2);
        AvailabilityResponse response = availabilityService.checkAvailability(request);

        // expect no available seats due to overlap
        assertEquals(0, response.getAvailableSeats().size());
        assertEquals(0, response.getTotalPrice());
    }

    @Test
    public void testSegmentOverlapBlocksAvailability2() {
        // book all 39 seats from A to D
        List<String> seatIds = seats.stream().limit(39).map(Seat::getId).collect(Collectors.toList());
        bookingDAO.save(new Booking("A-D", seatIds, "OVERLAP", 0));

        // try to book 1 seats from C to D
        AvailabilityRequest request = new AvailabilityRequest("C", "D", 1);
        AvailabilityResponse response = availabilityService.checkAvailability(request);

        // expect 1 seat availability
        assertEquals(1, response.getAvailableSeats().size());
        assertEquals(150, response.getTotalPrice());
    }
}