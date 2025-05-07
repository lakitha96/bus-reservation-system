package org.example.bus.entity;

/**
 * @author lakithaprabudh
 */
public class Journey {
    private final String origin;
    private final String destination;
    private final int price;

    public Journey(String origin, String destination, int price) {
        this.origin = origin;
        this.destination = destination;
        this.price = price;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public int getPrice() {
        return price;
    }

    public String getKey() {
        return origin + "-" + destination;
    }
}
