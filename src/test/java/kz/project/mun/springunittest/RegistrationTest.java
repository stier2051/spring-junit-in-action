package kz.project.mun.springunittest;

import kz.project.mun.springunittest.beans.TestBeans;
import kz.project.mun.springunittest.model.Passenger;
import kz.project.mun.springunittest.registration.PassengerRegistrationEvent;
import kz.project.mun.springunittest.registration.RegistrationManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(TestBeans.class)
public class RegistrationTest {

    @Autowired
    private Passenger passenger;

    @Autowired
    private RegistrationManager registrationManager;

    @Test
    void testPersonRegistration() {
        registrationManager.getApplicationContext().publishEvent(new PassengerRegistrationEvent(passenger));
        System.out.println("After registering:");
        System.out.println(passenger);
        assertTrue(passenger.isRegistered());
    }
}
