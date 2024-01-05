package kz.project.mun.springunittest.controller;

import kz.project.mun.springunittest.model.Country;
import kz.project.mun.springunittest.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CountryController {

    @Autowired
    private CountryRepository countryRepository;

    @GetMapping("/countries")
    List<Country> findAll() {
        return countryRepository.findAll();
    }
}
