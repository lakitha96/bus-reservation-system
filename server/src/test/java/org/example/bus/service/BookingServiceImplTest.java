package org.example.bus.service;

import org.example.bus.dao.BookingDAO;
import org.example.bus.dao.JourneyDAO;
import org.example.bus.dao.SeatDAO;
import org.example.bus.entity.Booking;
import org.example.bus.entity.Journey;
import org.example.bus.entity.Seat;
import org.example.bus.exception.BadRequestException;
import org.example.bus.model.BookingRequest;
import org.example.bus.model.BookingResponse;
import org.example.bus.service.impl.BookingServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author lakithaprabudh
 */
public class BookingServiceImplTest {
    private BookingDAO bookingDAO;
    private BookingServiceImpl bookingService;
    private List<Seat> seats;

    @Before
    public void setUp() {
        SeatDAO seatDAO = new SeatDAO();
        JourneyDAO journeyDAO = new JourneyDAO();
        bookingDAO = new BookingDAO();
        bookingDAO.clear();

        // initialize 40 seats (1A to 10D)
        seats = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            seats.add(new Seat(i + "A"));
            seats.add(new Seat(i + "B"));
            seats.add(new Seat(i + "C"));
            seats.add(new Seat(i + "D"));
        }
        seatDAO.initialize(seats);

        // initialize journey routes
        journeyDAO.initialize(Arrays.asList(
                new Journey("A", "B", 50),
                new Journey("B", "C", 50),
                new Journey("C", "D", 50),
                new Journey("A", "C", 100),
                new Journey("B", "D", 100),
                new Journey("A", "D", 150),
                new Journey("D", "C", 50),
                new Journey("C", "B", 50),
                new Journey("B", "A", 50),
                new Journey("D", "B", 100),
                new Journey("C", "A", 100),
                new Journey("D", "A", 150)
        ));

        bookingService = new BookingServiceImpl(seatDAO, journeyDAO, bookingDAO);
    }

    @Test
    public void testSuccessfulBooking() {
        BookingRequest request = new BookingRequest();
        request.setOrigin("A");
        request.setDestination("D");
        request.setPassengers(2);
        request.setAmount(300);

        BookingResponse response = bookingService.bookSeats(request);

        assertNotNull(response.getTicketNumber());
        assertEquals(2, response.getSeatNumbers().size());
        assertEquals("A", response.getOrigin());
        assertEquals("D", response.getDestination());
        assertEquals(300, response.getTotalPrice());
        assertNotNull(response.getDepartureTime());
        assertNotNull(response.getArrivalTime());
    }

    @Test(expected = BadRequestException.class)
    public void testBookingFailsWhenSeatsUnavailable() {
        // book 40 seats
        List<String> prebookedSeats = seats.stream().map(Seat::getId).collect(Collectors.toList());
        bookingDAO.save(new Booking("A-D", prebookedSeats, "PREBOOKED", 0));

        BookingRequest request = new BookingRequest();
        request.setOrigin("A");
        request.setDestination("D");
        request.setPassengers(1);
        request.setAmount(150);

        bookingService.bookSeats(request);
    }

    @Test(expected = BadRequestException.class)
    public void testBookingFailsWithInvalidJourney() {
        BookingRequest request = new BookingRequest();
        request.setOrigin("X");
        request.setDestination("Z");
        request.setPassengers(1);
        request.setAmount(150);

        bookingService.bookSeats(request);
    }

    @Test(expected = BadRequestException.class)
    public void testBookingFailsWithOverlappingJourney() {
        seats = Collections.singletonList(new Seat("1A"));
        SeatDAO seatDAO = new SeatDAO();
        seatDAO.initialize(seats);

        JourneyDAO journeyDAO = new JourneyDAO();
        journeyDAO.initialize(Arrays.asList(
                new Journey("A", "C", 100),
                new Journey("B", "D", 100)
        ));

        bookingDAO = new BookingDAO();
        bookingDAO.save(new Booking("B-D", Collections.singletonList("1A"), "OVERLAP", 0));

        bookingService = new BookingServiceImpl(seatDAO, journeyDAO, bookingDAO);

        BookingRequest request = new BookingRequest();
        request.setOrigin("A");
        request.setDestination("C");
        request.setPassengers(1);
        request.setAmount(100);

        bookingService.bookSeats(request);
    }

    @Test(expected = BadRequestException.class)
    public void testBookingFailsWhenRequestIsNull() {
        bookingService.bookSeats(null);
    }

    @Test
    public void testBookingReturnJourneyIsAllowed() {
        // book 2 seats from A → D
        BookingRequest forwardRequest = new BookingRequest();
        forwardRequest.setOrigin("A");
        forwardRequest.setDestination("D");
        forwardRequest.setPassengers(2);
        forwardRequest.setAmount(300);

        BookingResponse forwardResponse = bookingService.bookSeats(forwardRequest);
        assertEquals(2, forwardResponse.getSeatNumbers().size());

        // book return journey D → A
        BookingRequest returnRequest = new BookingRequest();
        returnRequest.setOrigin("D");
        returnRequest.setDestination("A");
        returnRequest.setPassengers(2);
        returnRequest.setAmount(300);

        BookingResponse returnResponse = bookingService.bookSeats(returnRequest);
        assertEquals(2, returnResponse.getSeatNumbers().size());
        assertEquals("D", returnResponse.getOrigin());
        assertEquals("A", returnResponse.getDestination());
    }
}