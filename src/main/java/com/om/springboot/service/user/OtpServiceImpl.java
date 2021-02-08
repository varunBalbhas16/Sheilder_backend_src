package com.om.springboot.service.user;

import com.om.springboot.mappers.user.OtpMapper;
import com.om.springboot.model.user.Otp;
import com.om.springboot.service.otpIntegration.TimeBasedOneTimePasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
@Component
public class OtpServiceImpl implements OtpService {
    @Autowired
    @Qualifier("otpMapper")
    OtpMapper otpMapper;

    public String generateSixDigitInteger() {
        try {
            final TimeBasedOneTimePasswordGenerator totp = new TimeBasedOneTimePasswordGenerator();
            final Key key;
            {
                final KeyGenerator keyGenerator = KeyGenerator.getInstance(totp.getAlgorithm());
                // SHA-1 and SHA-256 prefer 64-byte (512-bit) keys; SHA512 prefers 128-byte (1024-bit) keys
                keyGenerator.init(512);
                key = keyGenerator.generateKey();
            }

            final Instant now = Instant.now();
            final Instant later = now.plus(totp.getTimeStep());
            System.out.format("Current password: %06d\n", totp.generateOneTimePassword(key, now));
            System.out.format("Future password:  %06d\n", totp.generateOneTimePassword(key, later));

            return String.format("%06d", totp.generateOneTimePassword(key, now));
        } catch (NoSuchAlgorithmException e) {
            System.err.println("...Invalid Algorithm..");
        } catch (InvalidKeyException e) {
            System.err.println("...Invalid KeyException..");
        }
        return "Out of try - catch Block";
    }


    @Override
    public String insertOtp(String mobileNumber) {
        Otp otp = new Otp();
        otp.setMobileNumber(mobileNumber);
        otp.setOtp(this.generateSixDigitInteger());
        otp.setCreatedOn(Instant.now());
        otp.setResentCount(0L);
        Boolean isInserted = otpMapper.insertOtp(otp);
        if (null != isInserted && isInserted) {
            return otp.getOtp();
        }
        return null;
    }

    @Override
    public String updateOtp(String mobileNumber) {
        Otp otp = new Otp();
        Otp model=otpMapper.getOtpDetails(mobileNumber);
        Long count=model.getResentCount();
        if(count==2L){
            return null;
        }
        otp.setId(model.getId());
        otp.setMobileNumber(mobileNumber);
        otp.setOtp(this.generateSixDigitInteger());
        otp.setCreatedOn(model.getCreatedOn());
        otp.setResentOn(Instant.now());
        otp.setResentCount(count+1L);
        Boolean isUpdated = otpMapper.updateOtp(otp);
        if (null != isUpdated && isUpdated) {
            return otp.getOtp();
        }
        return null;
    }

    @Override
    public Boolean otpExistByMobileNumber(String mobileNumber) {
        Boolean isExist = otpMapper.existByMobile(mobileNumber);
        if (null == isExist || !isExist) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Boolean validateOtp(String mobile,String otpUser){
        Otp otpModel= otpMapper.getOtpDetails(mobile);
        if(null==otpModel){
            return false;
        }
        String otpDb=otpModel.getOtp();
        if(otpUser.equals(otpDb)){
            return true;
        }
        else
        {
            return false;
        }
    }
}
