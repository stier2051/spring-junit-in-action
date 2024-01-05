package kz.project.mun.springunittest;

import kz.project.mun.springunittest.beans.FlightBuilder;
import kz.project.mun.springunittest.model.Country;
import kz.project.mun.springunittest.model.Flight;
import kz.project.mun.springunittest.repository.CountryRepository;
import kz.project.mun.springunittest.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.Map;

@Import(FlightBuilder.class)
@SpringBootApplication
public class SpringUnittestApplication {

	@Autowired
	private Map<String, Country> countriesMap;

	@Autowired
	private Flight flight;
	public static void main(String[] args) {
		SpringApplication.run(SpringUnittestApplication.class, args);
	}

	@Bean
	CommandLineRunner configureRepository(CountryRepository countryRepository, PassengerRepository passengerRepository) {
		return args -> {
            countryRepository.saveAll(countriesMap.values());
			passengerRepository.saveAll(flight.getPassengers());
		};
	}
}
