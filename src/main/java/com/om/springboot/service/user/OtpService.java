package com.om.springboot.service.user;




public interface OtpService {
    String insertOtp(String mobileNumber);

    String updateOtp(String mobileNumber);

    Boolean otpExistByMobileNumber(String mobileNumber);

    Boolean validateOtp(String mobile, String otpUser);
}
