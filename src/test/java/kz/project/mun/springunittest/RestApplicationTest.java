package kz.project.mun.springunittest;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.project.mun.springunittest.beans.FlightBuilder;
import kz.project.mun.springunittest.exception.PassengerNotFoundException;
import kz.project.mun.springunittest.model.Country;
import kz.project.mun.springunittest.model.Flight;
import kz.project.mun.springunittest.model.Passenger;
import kz.project.mun.springunittest.repository.CountryRepository;
import kz.project.mun.springunittest.repository.PassengerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(FlightBuilder.class)
public class RestApplicationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private Flight flight;

    @Autowired
    private Map<String, Country> countriesMap;

    @MockBean
    private PassengerRepository passengerRepository;

    @MockBean
    private CountryRepository countryRepository;

    @Test
    void testGetAllCountries() throws Exception {
        when(countryRepository.findAll()).thenReturn(new ArrayList<>(countriesMap.values()));
        mvc.perform(get("/countries"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));

        verify(countryRepository, times(1)).findAll();
    }

    @Test
    void testAllPassengers() throws Exception {
        when(passengerRepository.findAll()).thenReturn(new ArrayList<>(flight.getPassengers()));
        mvc.perform(get("/passengers"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(20)));

        verify(passengerRepository, times(1)).findAll();
    }

    @Test
    void testPassengerNotFound() {
        Throwable throwable = assertThrows(NestedServletException.class, () -> mvc.perform(get("/passengers/30"))
                .andExpect(status().isNotFound()));

        assertEquals(PassengerNotFoundException.class, throwable.getCause().getClass());
    }

    @Test
    void testPostPassenger() throws Exception {
        Passenger passenger = new Passenger("Peter Michelsen");
        passenger.setCountry(countriesMap.get("US"));
        passenger.setIsRegistered(false);
        when(passengerRepository.save(passenger)).thenReturn(passenger);

        mvc.perform(post("/passengers")
                .content(new ObjectMapper().writeValueAsString(passenger))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Peter Michelsen")))
                .andExpect(jsonPath("$.country.codeName", is("US")))
                .andExpect(jsonPath("$.country.name", is("USA")))
                .andExpect(jsonPath("$.registered", is(Boolean.FALSE)));

        verify(passengerRepository, times(1)).save(passenger);
    }
}
