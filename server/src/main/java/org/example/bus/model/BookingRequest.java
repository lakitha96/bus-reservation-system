package org.example.bus.model;

/**
 * @author lakithaprabudh
 */
public class BookingRequest {
    private String origin;
    private String destination;
    private int passengers;
    private int amount;

    public BookingRequest() {
    }

    public BookingRequest(String origin, String destination, int passengers, int amount) {
        this.origin = origin;
        this.destination = destination;
        this.passengers = passengers;
        this.amount = amount;
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

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}