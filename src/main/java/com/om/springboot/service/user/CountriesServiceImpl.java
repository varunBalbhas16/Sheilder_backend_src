package com.om.springboot.service.user;

import com.om.springboot.dto.model.user.CountryDto;
import com.om.springboot.mappers.user.CountryMapper;
import com.om.springboot.model.user.Countries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountriesServiceImpl implements CountriesService{
    @Autowired
    @Qualifier("countryMapper")
    CountryMapper countryMapper;


    @Override
    public List<CountryDto> getAllCountries(){
        List<CountryDto> finalList = new ArrayList<>();
        CountryDto countryDto=new CountryDto();
        List<Countries> countries=countryMapper.getCountries();
        for(Countries c:countries){
            countryDto.setCountry(c.getCountry());
            finalList.add(countryDto);
        }
        return finalList;
    }
}
