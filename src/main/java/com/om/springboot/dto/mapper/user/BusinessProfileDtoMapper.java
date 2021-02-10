package com.om.springboot.dto.mapper.user;

import com.om.springboot.dto.model.user.BusinessProfileDto;
import com.om.springboot.model.user.BusinessProfile;

public class BusinessProfileDtoMapper {
    public static BusinessProfileDto toDto(BusinessProfile businessProfile){
        if(null==businessProfile){
            return null;
        }
        return new BusinessProfileDto()
                .setUserId(businessProfile.getId())
                .setFirstName(businessProfile.getFirstName())
                .setLastName(businessProfile.getLastName())
                .setGender(businessProfile.getGender())
                .setDob(businessProfile.getDob())
                .setCountryCode(businessProfile.getCountryCode())
                .setCountry(businessProfile.getCountry())
                .setMobileNumber(businessProfile.getMobileNumber())
                .setEmailId(businessProfile.getEmailId())
                .setCompany(businessProfile.getCompany())
                .setRegId(businessProfile.getRegId())
                .setGsaNumber(businessProfile.getGsaNumber())
                .setEmployeeCount(businessProfile.getEmployeeCount())
                .setIsAdmin(businessProfile.getIsAdmin());
    }
}
