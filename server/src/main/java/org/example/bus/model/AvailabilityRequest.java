package org.example.bus.model;

/**
 * @author lakithaprabudh
 */
public class AvailabilityRequest {
    private String origin;
    private String destination;
    private int passengers;

    public AvailabilityRequest() {
    }

    public AvailabilityRequest(String origin, String destination, int passengers) {
        this.origin = origin;
        this.destination = destination;
        this.passengers = passengers;
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
}