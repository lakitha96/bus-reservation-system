package org.example.bus.model;

import java.util.List;

/**
 * @author lakithaprabudh
 */
public class BookingResponse {
    private String ticketNumber;
    private List<String> seatNumbers;
    private String origin;
    private String destination;
    private int totalPrice;
    private String departureTime;
    private String arrivalTime;

    public BookingResponse() {
    }

    public BookingResponse(String ticketNumber, List<String> seatNumbers,
                           String origin, String destination,
                           int totalPrice, String departureTime,
                           String arrivalTime) {
        this.ticketNumber = ticketNumber;
        this.seatNumbers = seatNumbers;
        this.origin = origin;
        this.destination = destination;
        this.totalPrice = totalPrice;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public List<String> getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(List<String> seatNumbers) {
        this.seatNumbers = seatNumbers;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}