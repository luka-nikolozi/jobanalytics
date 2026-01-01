package com.lukanikoloz.jobanalytics.Service.Impl;

import com.lukanikoloz.jobanalytics.Repository.CountryRepository;
import com.lukanikoloz.jobanalytics.Service.CountryService;
import com.lukanikoloz.jobanalytics.domain.Entity.Country;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }
}
