package com.om.springboot.service.user;

import com.om.springboot.dto.model.user.CountryDto;

import java.util.List;

public interface CountriesService {
    List<CountryDto> getAllCountries();
}
