package com.om.springboot.controller.ui.auth;

import com.om.springboot.controller.request.OtpRequest;
import com.om.springboot.controller.request.auth.ValidateOtpRequest;
import com.om.springboot.dto.model.user.AdminDto;
import com.om.springboot.dto.model.user.UserAuthenticationDto;
import com.om.springboot.dto.response.ApiResponse;
import com.om.springboot.dto.response.OtpResponse;
import com.om.springboot.service.user.AdminService;
import com.om.springboot.service.user.OtpService;
import com.om.springboot.service.user.UserAuthenticationService;
import com.om.springboot.util.AppConstants;
import com.om.springboot.util.ErrorConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@RestController
@RequestMapping("/api/shielder")
public class AuthenticationController {

    @Autowired
    OtpService otpService;

    @Autowired
    AdminService adminService;

    @Autowired
    UserAuthenticationService userAuthenticationService;

    @CrossOrigin
    @PostMapping("/login/otp")
    public ResponseEntity<?> generateOtp(@Valid @RequestBody OtpRequest otpRequest) {
        String otp = null;
        String mobileNumber = otpRequest.getMobileNumber();
        OtpResponse otpResponse = new OtpResponse(true, "");
        Boolean isExist = otpService.otpExistByMobileNumber(mobileNumber);
        if (isExist) {
            UserAuthenticationDto userAuthenticationDto = userAuthenticationService.checkAuthentication(mobileNumber);
            int logStatus = userAuthenticationDto.getIsLoggedIn();
            if (logStatus == AppConstants.LOGOUT){
                otp = otpService.updateOtpWRC(mobileNumber);
            }
            else{
                return new ResponseEntity<>(new ApiResponse(false,ErrorConstants.E114.name()),HttpStatus.OK);
            }
        }
        else {
            otp = otpService.insertOtp(mobileNumber);
        }
        otpResponse.setMobileNumber(mobileNumber).setOtp(otp);

        return new ResponseEntity<>(otpResponse, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/login/resendOtp")
    public ResponseEntity<?> resendOtp(@Valid @RequestBody OtpRequest otpRequest) {
        String otp = null;
        String mobileNumber = otpRequest.getMobileNumber();
        OtpResponse otpResponse = new OtpResponse(true, "");
        Boolean isExist = otpService.otpExistByMobileNumber(mobileNumber);
        if (isExist) {
            otp = otpService.updateOtp(mobileNumber);
            if (otp == null) {
                return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E104.name()), HttpStatus.OK);
            }
        }
        otpResponse.setMobileNumber(mobileNumber).setOtp(otp);
        return new ResponseEntity<>(otpResponse, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/login/validateOtp")
    public ResponseEntity<?> validateLoginOtp(@Valid @RequestBody ValidateOtpRequest validateOtpRequest) {
        String mobile = validateOtpRequest.getMobileNumber();
        String otpUser = validateOtpRequest.getOtp();
        Boolean isExist = otpService.otpExistByMobileNumber(mobile);
        if (!isExist) {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E106.name()), HttpStatus.OK);
        }
        AdminDto adminDto = adminService.getAdminDetails(mobile);
        if (null == adminDto) {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E109.name()), HttpStatus.OK);
        }
        Long adminId = adminDto.getAdminId();
        Boolean isValid = otpService.validateOtp(mobile, otpUser);
        if (isValid) {
            UserAuthenticationDto userAuthenticationDto = new UserAuthenticationDto();
            userAuthenticationDto.setOtp(otpUser).setAdminId(adminId)
                    .setIsLoggedIn(AppConstants.LOGIN)
                    .setMobile(mobile);
            UserAuthenticationDto authenticationDto = userAuthenticationService.checkAuthentication(mobile);
            if (null == authenticationDto) {

                Boolean isLoggedIn = userAuthenticationService.initialLogin(userAuthenticationDto);
                if (!isLoggedIn) {
                    return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E108.name()), HttpStatus.OK);
                }
            } else {
                Boolean isLoggedIn = userAuthenticationService.login(userAuthenticationDto);
                if (!isLoggedIn) {
                    return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E108.name()), HttpStatus.OK);
                }
            }

            return new ResponseEntity<>(new ApiResponse(true, "Valid"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E107.name()), HttpStatus.OK);
        }

    }

    @CrossOrigin
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody OtpRequest otpRequest) {
        String mobile = otpRequest.getMobileNumber();
        UserAuthenticationDto userAuthenticationDto = userAuthenticationService.checkAuthentication(mobile);
        if (null == userAuthenticationDto) {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E108.name()), HttpStatus.OK);
        }
        System.out.println("User authentication -- >" + userAuthenticationDto);
        int isLoggedin = userAuthenticationDto.getIsLoggedIn();
        if (AppConstants.LOGIN == isLoggedin) {
            UserAuthenticationDto userAuthenticationDto1 = new UserAuthenticationDto();
            userAuthenticationDto1.setAdminId(userAuthenticationDto.getAdminId())
                    .setMobile(mobile)
                    .setIsLoggedIn(AppConstants.LOGOUT);
            System.out.println("User authentication 1 -- >" + userAuthenticationDto1);
            Boolean isLogOut = userAuthenticationService.logout(userAuthenticationDto1);
            if (isLogOut) {
                return new ResponseEntity<>(new ApiResponse(true, "Logged Out"), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E108.name()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E108.name()), HttpStatus.OK);
    }
}
