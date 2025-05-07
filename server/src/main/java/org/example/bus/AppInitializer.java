package org.example.bus;

import org.example.bus.dao.BookingDAO;
import org.example.bus.dao.JourneyDAO;
import org.example.bus.dao.SeatDAO;
import org.example.bus.service.AvailabilityService;
import org.example.bus.service.BookingService;
import org.example.bus.service.impl.AvailabilityServiceImpl;
import org.example.bus.service.impl.BookingServiceImpl;

/**
 * @author lakithaprabudh
 */
public class AppInitializer {
    public static final JourneyDAO journeyDAO = new JourneyDAO();
    public static final SeatDAO seatDAO = new SeatDAO();
    public static final BookingDAO bookingDAO = new BookingDAO();

    public static final AvailabilityService availabilityService =
            new AvailabilityServiceImpl(seatDAO, journeyDAO, bookingDAO);

    public static final BookingService bookingService =
            new BookingServiceImpl(seatDAO, journeyDAO, bookingDAO);
}