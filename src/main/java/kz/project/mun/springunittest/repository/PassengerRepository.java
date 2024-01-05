package kz.project.mun.springunittest.repository;

import kz.project.mun.springunittest.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
