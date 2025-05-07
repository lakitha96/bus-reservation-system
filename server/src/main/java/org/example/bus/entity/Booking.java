package org.example.bus.entity;

import java.util.List;

/**
 * @author lakithaprabudh
 */
public class Booking {

    private final String journeyKey;
    private final List<String> seatNumbers;
    private final String ticketNumber;
    private final int totalPrice;

    public Booking(String journeyKey, List<String> seatNumbers, String ticketNumber, int totalPrice) {
        this.journeyKey = journeyKey;
        this.seatNumbers = seatNumbers;
        this.ticketNumber = ticketNumber;
        this.totalPrice = totalPrice;
    }

    public String getJourneyKey() {
        return journeyKey;
    }

    public List<String> getSeatNumbers() {
        return seatNumbers;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}