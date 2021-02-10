package com.om.springboot.dto.mapper.user;

import com.om.springboot.dto.model.user.AdminDto;
import com.om.springboot.model.user.Admin;

public class AdminDtoMapper {
    public static AdminDto toAdminDto(Admin admin){
        return new AdminDto()
                .setAdminId(admin.getId())
                .setAdminName(admin.getName())
                .setAdminMobileNumber(admin.getMobile())
                .setAdminEmailId(admin.getEmail())
                .setCompany(admin.getCompany());
    }
}
