package org.example.bus.dao;

import org.example.bus.entity.Journey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author lakithaprabudh
 */
public class JourneyDAO {
    private static final List<Journey> JOURNEYS = new ArrayList<>();

    static {
        JOURNEYS.add(new Journey("A", "B", 50));
        JOURNEYS.add(new Journey("A", "C", 100));
        JOURNEYS.add(new Journey("A", "D", 150));
        JOURNEYS.add(new Journey("B", "C", 50));
        JOURNEYS.add(new Journey("B", "D", 100));
        JOURNEYS.add(new Journey("C", "D", 50));

        // Return routes
        JOURNEYS.add(new Journey("D", "C", 50));
        JOURNEYS.add(new Journey("D", "B", 100));
        JOURNEYS.add(new Journey("D", "A", 150));
        JOURNEYS.add(new Journey("C", "B", 50));
        JOURNEYS.add(new Journey("C", "A", 100));
        JOURNEYS.add(new Journey("B", "A", 50));
    }

    public List<Journey> loadAll() {
        return Collections.unmodifiableList(JOURNEYS);
    }

    public Journey findByOriginAndDestination(String origin, String destination) {
        for (Journey journey : JOURNEYS) {
            if (journey.getOrigin().equals(origin) && journey.getDestination().equals(destination)) {
                return journey;
            }
        }
        return null;
    }

    public String getJourneyKey(String origin, String destination) {
        return origin + "-" + destination;
    }

    public void initialize(List<Journey> journeys) {
        JOURNEYS.clear();
        JOURNEYS.addAll(journeys);
    }
}
