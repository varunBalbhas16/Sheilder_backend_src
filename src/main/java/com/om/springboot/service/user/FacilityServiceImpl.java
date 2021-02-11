package com.om.springboot.service.user;

import com.om.springboot.dto.mapper.user.FacilityDtoMapper;
import com.om.springboot.dto.model.user.FacilityDto;
import com.om.springboot.mappers.user.FacilityMapper;
import com.om.springboot.model.user.Facility;
import com.om.springboot.service.Application.SafeAccessServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilityServiceImpl implements FacilityService {

    @Autowired
    @Qualifier("facilityMapper")
    FacilityMapper facilityMapper;

    @Autowired
    SafeAccessServiceImpl safeAccessService;


    @Override
    public Boolean addFacility(FacilityDto facilityDto) {

        Facility facility = new Facility();
        facility.setSafeAccessId(safeAccessService.getSafeAccessId());
        facility.setFacilityName(facilityDto.getFacilityName());
        facility.setFacilityAddress(facilityDto.getFacilityAddress());
        facility.setInCharge(facilityDto.getInCharge());
        facility.setCountryCode(facilityDto.getCountryCode());
        facility.setMobile(facilityDto.getMobileNumber());
        facility.setCompany(facilityDto.getCompany());
        Boolean isInserted = facilityMapper.addFacility(facility);
        if (null == isInserted || !isInserted) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public List<FacilityDto> getFacilityByCompany(String company) {
        return FacilityDtoMapper.toFacilityDtoList(facilityMapper.getFacilityByCompany(company));
    }

    @Override
    public Boolean existByFacilityAndCompany(String facilityName,String company){
        Boolean isExist= facilityMapper.existByFacilityAndCompany(facilityName,company);
        if(null==isExist || !isExist){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public FacilityDto getByFacilityAndCompany(String facilityName,String company){
        return FacilityDtoMapper.toFacilityDto(facilityMapper.getByFacilityAndCompany(facilityName,company));
    }
}
