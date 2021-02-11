package com.om.springboot.service.user;

import com.om.springboot.dto.model.user.FacilityDto;

import java.util.List;

public interface FacilityService {
    Boolean addFacility(FacilityDto facilityDto);
    List<FacilityDto> getFacilityByCompany(String company);

    Boolean existByFacilityAndCompany(String facilityName,String company);
    FacilityDto getByFacilityAndCompany(String facilityName,String company);
}
