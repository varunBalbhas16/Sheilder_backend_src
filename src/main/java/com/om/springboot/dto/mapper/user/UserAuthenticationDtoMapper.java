package com.om.springboot.dto.mapper.user;

import com.om.springboot.dto.model.user.UserAuthenticationDto;
import com.om.springboot.model.user.UserAuthentication;

public class UserAuthenticationDtoMapper {
    public static UserAuthenticationDto toDto(UserAuthentication userAuthentication){
        if(null==userAuthentication){
            return null;
        }
        return new UserAuthenticationDto()
                .setAdminId(userAuthentication.getAdminId())
                .setMobile(userAuthentication.getMobile())
                .setOtp(userAuthentication.getOtp())
                .setIsLoggedIn(userAuthentication.getIsLoggedIn());
    }
}
