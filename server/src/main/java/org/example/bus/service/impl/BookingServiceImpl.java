package org.example.bus.service.impl;

import org.example.bus.dao.BookingDAO;
import org.example.bus.dao.JourneyDAO;
import org.example.bus.dao.SeatDAO;
import org.example.bus.entity.Booking;
import org.example.bus.entity.Journey;
import org.example.bus.entity.Seat;
import org.example.bus.exception.BadRequestException;
import org.example.bus.model.BookingRequest;
import org.example.bus.model.BookingResponse;
import org.example.bus.service.BookingService;
import org.example.bus.util.CommonUtil;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.example.bus.util.ValidateUtil.validateCommonFields;

/**
 * @author lakithaprabudh
 */
public class BookingServiceImpl implements BookingService {
    private static final Logger logger = Logger.getLogger(BookingServiceImpl.class.getName());

    private final SeatDAO seatDAO;
    private final JourneyDAO journeyDAO;
    private final BookingDAO bookingDAO;

    public BookingServiceImpl(SeatDAO seatDAO, JourneyDAO journeyDAO, BookingDAO bookingDAO) {
        this.seatDAO = seatDAO;
        this.journeyDAO = journeyDAO;
        this.bookingDAO = bookingDAO;
    }

    @Override
    public BookingResponse bookSeats(BookingRequest request) {
        validateRequest(request);

        Journey journey = journeyDAO.findByOriginAndDestination(request.getOrigin(), request.getDestination());
        if (journey == null) {
            throw new BadRequestException("Invalid route.");
        }

        int expectedAmount = journey.getPrice() * request.getPassengers();
        if (request.getAmount() != expectedAmount) {
            throw new BadRequestException("Payment mismatch. expected: " + expectedAmount);
        }

        String journeyKey = CommonUtil.buildKey(journey.getOrigin(), journey.getDestination());
        String ticketNumber = CommonUtil.generateTicketNumber();
        Booking booking;

        List<String> availableSeats;

        //used synchronized to lock seat booking it will release once the completion done.
        //this helps to avoid when we do booking same time with several clients to same seat.
        synchronized (this) {
            Set<String> booked = bookingDAO.loadBookedSeatIds(journeyKey);
            List<String> availableSeatIds = seatDAO.loadAll().stream().map(Seat::getId).collect(Collectors.toList());
            availableSeats = CommonUtil.findAvailableSeats(availableSeatIds, booked, request.getPassengers());

            if (availableSeats.size() < request.getPassengers()) {
                logger.info("Not enough seats available for " + journeyKey);
                throw new BadRequestException("Not enough seats available.");
            }

            booking = new Booking(journeyKey, availableSeats, ticketNumber, expectedAmount);
            bookingDAO.save(booking);
            logger.info("Reservation successful: " + ticketNumber + " seats" + availableSeats);
        }

        return new BookingResponse(
                booking.getTicketNumber(),
                booking.getSeatNumbers(),
                journey.getOrigin(),
                journey.getDestination(),
                booking.getTotalPrice(),
                getDepartureTime(journey.getOrigin()),
                getArrivalTime(journey.getDestination())
        );
    }

    private String getDepartureTime(String origin) {
        return origin.equals("A") ? "08:00 AM" : "01:00 PM";
    }

    private String getArrivalTime(String destination) {
        return destination.equals("D") ? "12:00 PM" : "05:00 PM";
    }

    private void validateRequest(BookingRequest request) {
        if (request == null) {
            throw new BadRequestException("Request body is missing.");
        }

        List<String> errors = validateCommonFields(request.getOrigin(), request.getDestination(), request.getPassengers());

        if (request.getAmount() <= 0) {
            errors.add("Payment amount must be greater than 0.");
        }

        if (!errors.isEmpty()) {
            throw new BadRequestException(errors);
        }
    }
}