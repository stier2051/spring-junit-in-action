package kz.project.mun.springunittest.exception;

public class PassengerNotFoundException extends RuntimeException {
    public PassengerNotFoundException(Long id) {
        super("Passenger id not found : " + id);
    }
}
