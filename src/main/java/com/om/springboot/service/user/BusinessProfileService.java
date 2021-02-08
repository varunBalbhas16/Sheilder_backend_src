package com.om.springboot.service.user;

import com.om.springboot.dto.model.user.BusinessProfileDto;

public interface BusinessProfileService {
    Boolean existByMobileNumber(String mobileNumber);
    Boolean insertRegisteredDetails(BusinessProfileDto businessProfileDto);
    Long getUserId(String mobileNumber);
    Boolean existByCompany(String company);
    BusinessProfileDto getBusinessDetails(String mobileNumber);
}
