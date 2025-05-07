package org.example.bus;

import org.example.bus.service.AvailabilityServiceImplTest;
import org.example.bus.service.BookingServiceImplTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author lakithaprabudh
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AvailabilityServiceImplTest.class,
        BookingServiceImplTest.class
})
public class MainTest {}
