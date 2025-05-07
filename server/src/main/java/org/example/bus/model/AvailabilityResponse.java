package org.example.bus.model;

import java.util.List;

/**
 * @author lakithaprabudh
 */
public class AvailabilityResponse {
    private List<String> availableSeats;
    private int totalPrice;

    public AvailabilityResponse() {
    }

    public AvailabilityResponse(List<String> availableSeats, int totalPrice) {
        this.availableSeats = availableSeats;
        this.totalPrice = totalPrice;
    }

    public List<String> getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(List<String> availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}