package org.example.bus.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lakithaprabudh
 */
public class ValidateUtil {
    public static List<String> validateCommonFields(String origin, String destination, int passengers) {
        List<String> errors = new ArrayList<>();

        if (origin == null || destination == null) {
            errors.add("Origin and destination are required.");
        } else if (origin.equals(destination)) {
            errors.add("Origin and destination cannot be the same.");
        }

        if (passengers <= 0) {
            errors.add("Passenger count must be greater than 0.");
        }

        return errors;
    }
}
