package com.om.springboot.mappers.user;

import com.om.springboot.model.user.BusinessProfile;

public interface BusinessProfileMapper {
    Boolean existByMobile(String mobileNumber);
    Boolean insertRegisterDetail(BusinessProfile businessProfile);
}
