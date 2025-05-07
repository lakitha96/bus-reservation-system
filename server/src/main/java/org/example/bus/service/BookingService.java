package org.example.bus.service;

import org.example.bus.model.BookingRequest;
import org.example.bus.model.BookingResponse;

/**
 * @author lakithaprabudh
 */
public interface BookingService {
    BookingResponse bookSeats(BookingRequest request);
}