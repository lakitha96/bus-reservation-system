package org.example.bus.service.impl;

import org.example.bus.dao.BookingDAO;
import org.example.bus.dao.JourneyDAO;
import org.example.bus.dao.SeatDAO;
import org.example.bus.entity.Journey;
import org.example.bus.entity.Seat;
import org.example.bus.exception.BadRequestException;
import org.example.bus.model.AvailabilityRequest;
import org.example.bus.model.AvailabilityResponse;
import org.example.bus.service.AvailabilityService;
import org.example.bus.util.CommonUtil;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.example.bus.util.ValidateUtil.validateCommonFields;

/**
 * @author lakithaprabudh
 */
public class AvailabilityServiceImpl implements AvailabilityService {
    private static final Logger logger = Logger.getLogger(AvailabilityServiceImpl.class.getName());
    private final SeatDAO seatDAO;
    private final JourneyDAO journeyDAO;
    private final BookingDAO bookingDAO;

    public AvailabilityServiceImpl(SeatDAO seatDAO, JourneyDAO journeyDAO, BookingDAO bookingDAO) {
        this.seatDAO = seatDAO;
        this.journeyDAO = journeyDAO;
        this.bookingDAO = bookingDAO;
    }

    @Override
    public AvailabilityResponse checkAvailability(AvailabilityRequest request) {
        validateRequest(request);

        String origin = request.getOrigin();
        String destination = request.getDestination();
        int passengerCount = request.getPassengers();
        String journeyKey = journeyDAO.getJourneyKey(origin, destination);

        Journey journey = journeyDAO.findByOriginAndDestination(origin, destination);
        if (journey == null) {
            throw new BadRequestException("Invalid route: " + origin + " -> " + destination);
        }

        Set<String> bookedSeats = bookingDAO.loadBookedSeatIds(journeyKey);
        List<String> allSeats = seatDAO.loadAll().stream().map(Seat::getId).collect(Collectors.toList());
        List<String> availableSeats = CommonUtil.findAvailableSeats(allSeats, bookedSeats, passengerCount);


        if (availableSeats.size() < passengerCount) {
            logger.info("Not enough seats available for journey: " + journeyKey);
            return new AvailabilityResponse(Collections.emptyList(), 0);
        }

        int totalPrice = journey.getPrice() * passengerCount;
        return new AvailabilityResponse(availableSeats, totalPrice);
    }

    private void validateRequest(AvailabilityRequest request) {
        if (request == null) {
            throw new BadRequestException("Request body is missing.");
        }

        List<String> errors = validateCommonFields(request.getOrigin(), request.getDestination(), request.getPassengers());

        if (!errors.isEmpty()) {
            throw new BadRequestException(errors);
        }
    }
}