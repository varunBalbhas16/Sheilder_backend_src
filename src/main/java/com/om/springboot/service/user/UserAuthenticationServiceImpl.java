package com.om.springboot.service.user;

import com.om.springboot.dto.mapper.user.UserAuthenticationDtoMapper;
import com.om.springboot.dto.model.user.UserAuthenticationDto;
import com.om.springboot.mappers.user.UserAuthenticationMapper;
import com.om.springboot.model.user.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class UserAuthenticationServiceImpl implements UserAuthenticationService {
    @Autowired
    @Qualifier("userAuthenticationMapper")
    UserAuthenticationMapper userAuthenticationMapper;

    @Override
    public Boolean initialLogin(UserAuthenticationDto userAuthenticationDto) {
        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.setUserId(userAuthenticationDto.getUserId());
        userAuthentication.setIsLoggedIn(userAuthenticationDto.getIsLoggedIn());
        userAuthentication.setMobile(userAuthenticationDto.getMobile());
        userAuthentication.setLoginTime(Instant.now());
        userAuthentication.setOtp(userAuthenticationDto.getOtp());
        Boolean isLoggedIn = userAuthenticationMapper.initialLogin(userAuthentication);
        if (null == isLoggedIn || !isLoggedIn) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Boolean login(UserAuthenticationDto userAuthenticationDto) {
        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.setUserId(userAuthenticationDto.getUserId());
        userAuthentication.setIsLoggedIn(userAuthenticationDto.getIsLoggedIn());
        userAuthentication.setMobile(userAuthenticationDto.getMobile());
        userAuthentication.setLoginTime(Instant.now());
        userAuthentication.setOtp(userAuthenticationDto.getOtp());
        Boolean isLoggedIn = userAuthenticationMapper.login(userAuthentication);
        if (null == isLoggedIn || !isLoggedIn) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Boolean logout(UserAuthenticationDto userAuthenticationDto) {
        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.setUserId(userAuthenticationDto.getUserId());
        userAuthentication.setIsLoggedIn(userAuthenticationDto.getIsLoggedIn());
        userAuthentication.setMobile(userAuthenticationDto.getMobile());
        userAuthentication.setLogoutTime(Instant.now());
        Boolean isLoggedIn = userAuthenticationMapper.logout(userAuthentication);
        if (null == isLoggedIn || !isLoggedIn) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public UserAuthenticationDto checkAuthentication(String mobile) {
        return UserAuthenticationDtoMapper.toDto(userAuthenticationMapper.getAuthenticationDetails(mobile));
    }
}
