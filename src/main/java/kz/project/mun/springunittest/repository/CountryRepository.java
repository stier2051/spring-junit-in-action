package kz.project.mun.springunittest.repository;

import kz.project.mun.springunittest.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String> {
}
