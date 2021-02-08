package com.om.springboot.service.user;

import com.om.springboot.dto.model.user.UserAuthenticationDto;

public interface UserAuthenticationService {
    Boolean initialLogin(UserAuthenticationDto userAuthenticationDto);
    Boolean login(UserAuthenticationDto userAuthenticationDto);
    UserAuthenticationDto checkAuthentication(String mobileNumber);
    Boolean logout(UserAuthenticationDto userAuthenticationDto);

    }
