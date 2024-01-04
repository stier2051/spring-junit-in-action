package kz.project.mun.springunittest;

import kz.project.mun.springunittest.beans.FlightBuilder;
import kz.project.mun.springunittest.model.Flight;
import kz.project.mun.springunittest.model.Passenger;
import kz.project.mun.springunittest.registration.PassengerRegistrationEvent;
import kz.project.mun.springunittest.registration.RegistrationManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(FlightBuilder.class)
public class FlightTest {
    @Autowired
    private Flight flight;

    @Autowired
    private RegistrationManager registrationManager;

    @Test
    void testFlightPassengersRegistration() {
        for (Passenger passenger : flight.getPassengers()) {
            assertFalse(passenger.isRegistered());
            registrationManager.getApplicationContext().publishEvent(new PassengerRegistrationEvent(passenger));
        }

        System.out.println("All passengers from the flight are now confirmed as registered");

        for (Passenger passenger : flight.getPassengers()) {
            assertTrue(passenger.isRegistered());
        }
    }
}
