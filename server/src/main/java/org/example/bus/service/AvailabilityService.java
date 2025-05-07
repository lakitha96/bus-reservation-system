package org.example.bus.service;

import org.example.bus.model.AvailabilityRequest;
import org.example.bus.model.AvailabilityResponse;

/**
 * @author lakithaprabudh
 */
public interface AvailabilityService {
    AvailabilityResponse checkAvailability(AvailabilityRequest request);
}