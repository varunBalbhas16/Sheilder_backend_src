package com.om.springboot.mappers.user;

import com.om.springboot.model.user.Facility;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FacilityMapper {
    Boolean addFacility(Facility facility);

    List<Facility> getFacilityByCompany(String company);

    Boolean existByFacilityAndCompany(String facilityName,String company);

    Facility getByFacilityAndCompany(String facilityName,String company);
}
