package com.om.springboot.mappers.user;

import com.om.springboot.model.user.BusinessProfile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BusinessProfileMapper {
    Boolean existByMobile(String mobileNumber);
    Boolean insertRegisterDetail(BusinessProfile businessProfile);
    Long getUserId(String mobileNumber);
    Boolean existByCompany(String company);

    BusinessProfile getBusinessDetail(String mobileNumber);
}
