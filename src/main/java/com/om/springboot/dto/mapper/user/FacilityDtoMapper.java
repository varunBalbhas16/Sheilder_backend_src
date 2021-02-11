package com.om.springboot.dto.mapper.user;

import com.om.springboot.dto.model.user.FacilityDto;
import com.om.springboot.model.user.Facility;

import java.util.ArrayList;
import java.util.List;

public class FacilityDtoMapper {
    public static FacilityDto toFacilityDto(Facility facility){
        return new FacilityDto()
                .setSafeAccessId(facility.getSafeAccessId())
                .setFacilityName(facility.getFacilityName())
                .setFacilityAddress(facility.getFacilityAddress())
                .setInCharge(facility.getInCharge())
                .setCountryCode(facility.getCountryCode())
                .setMobileNumber(facility.getMobile())
                .setCompany(facility.getCompany());

    }

    public static List<FacilityDto> toFacilityDtoList(List<Facility> facilities) {
        List<FacilityDto> facilityDtoList=new ArrayList<>();
        for(Facility facility:facilities){
            FacilityDto facilityDto=toFacilityDto(facility);
            facilityDtoList.add(facilityDto);
        }
        return facilityDtoList;
    }
}
