package org.example.bus.dao;

import org.example.bus.entity.Seat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lakithaprabudh
 */
public class SeatDAO {
    private static final List<Seat> SEATS = new ArrayList<>();

    static {
        for (int row = 1; row <= 10; row++) {
            SEATS.add(new Seat(row + "A"));
            SEATS.add(new Seat(row + "B"));
            SEATS.add(new Seat(row + "C"));
            SEATS.add(new Seat(row + "D"));
        }
    }

    public List<Seat> loadAll() {
        return SEATS;
    }

    public void initialize(List<Seat> seats) {
        SEATS.clear();
        SEATS.addAll(seats);
    }
}