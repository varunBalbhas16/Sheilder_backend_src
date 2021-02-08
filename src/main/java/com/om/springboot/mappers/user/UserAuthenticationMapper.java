package com.om.springboot.mappers.user;

import com.om.springboot.model.user.UserAuthentication;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAuthenticationMapper {
    Boolean initialLogin(UserAuthentication userAuthentication);
    UserAuthentication getAuthenticationDetails(String mobile);

    Boolean login(UserAuthentication userAuthentication);

    Boolean logout(UserAuthentication userAuthentication);
}
