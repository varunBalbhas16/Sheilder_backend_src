package com.om.springboot.mappers.user;

import com.om.springboot.model.user.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
    Boolean adminExistByMobAndCompany(String mobile,String company);
    Boolean insertAdminDetails(Admin admin);
    Admin getAdmin(String mobile);
    Boolean existByMobile(String mobile);
}
