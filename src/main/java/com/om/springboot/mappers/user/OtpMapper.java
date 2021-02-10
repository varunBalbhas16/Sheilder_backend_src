package com.om.springboot.mappers.user;

import com.om.springboot.model.user.Otp;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OtpMapper {
    Boolean insertOtp(Otp otp);
    Boolean updateOtp(Otp otp);
    Boolean existByMobile(String mobileNumber);
    Otp getOtpDetails(String mobileNumber);

    Boolean updateOtpWRC(Otp otp);
}
