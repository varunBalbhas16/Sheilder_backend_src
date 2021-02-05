package com.om.springboot.service.user;

import com.om.springboot.dto.model.user.BusinessProfileDto;
import com.om.springboot.mappers.user.BusinessProfileMapper;
import com.om.springboot.model.user.BusinessProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BusinessProfileServiceImpl implements BusinessProfileService {
    @Autowired
    @Qualifier("businessProfileMapper")
    BusinessProfileMapper businessProfileMapper;


    @Override
    public Boolean existByMobileNumber(String mobileNumber) {
        Boolean isExist = businessProfileMapper.existByMobile(mobileNumber);
        if (null == isExist || !isExist) {
            return false;
        } else {
            return true;
        }
    }


    public Boolean insertRegisteredDetails(BusinessProfileDto businessProfileDto) {

        BusinessProfile businessProfile = new BusinessProfile();
        businessProfile.setFirstName(businessProfileDto.getFirstName());
        businessProfile.setLastName(businessProfileDto.getLastName());
        businessProfile.setCompany(businessProfileDto.getCompany());
        businessProfile.setCountry(businessProfileDto.getCountry());
        businessProfile.setDob(businessProfileDto.getDob());
        businessProfile.setEmailId(businessProfileDto.getEmailId());
        businessProfile.setGender(businessProfileDto.getGender());
        businessProfile.setMobileNumber(businessProfileDto.getMobileNumber());
        businessProfile.setRegId(businessProfileDto.getRegId());
        businessProfile.setGsaNumber(businessProfileDto.getGsaNumber());
        businessProfile.setEmployeeCount(businessProfileDto.getEmployeeCount());
        businessProfile.setIsAdmin(businessProfileDto.getIsAdmin());
        return businessProfileMapper.insertRegisterDetail(businessProfile);

    }
}
