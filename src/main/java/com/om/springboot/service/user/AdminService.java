package com.om.springboot.service.user;

import com.om.springboot.dto.model.user.AdminDto;

public interface AdminService {
    Boolean insertAdmin(AdminDto adminDto);
    AdminDto getAdminDetails(String mobile);
    Boolean adminByMobileNumberAndCompany(String mobileNumber,String country);
    Boolean adminByMobile(String mobileNumber);
}
