package com.om.springboot.controller.ui.user;

import com.om.springboot.controller.request.AddEmployeeRequest;
import com.om.springboot.controller.request.BusinessProfileRequest;
import com.om.springboot.controller.request.OtpRequest;
import com.om.springboot.controller.request.auth.ValidateOtpRequest;
import com.om.springboot.dto.model.user.*;
import com.om.springboot.dto.response.ApiResponse;
import com.om.springboot.dto.response.OtpResponse;
import com.om.springboot.dto.response.user.EmployeeRequest;
import com.om.springboot.dto.response.user.EmployeeResponse;
import com.om.springboot.model.user.StatusMaster;
import com.om.springboot.service.user.*;
import com.om.springboot.util.AppConstants;
import com.om.springboot.util.ErrorConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/safeAccess")
public class BusinessProfileController {

    @Autowired
    BusinessProfileService businessProfileService;

    @Autowired
    AdminService adminService;


    @CrossOrigin
    @PostMapping("/postLogin/businessProfileRegisteration")
    public ResponseEntity<?> businessRegisteration(@Valid @RequestBody BusinessProfileRequest businessProfileRequest) {
        String mobileNumber = businessProfileRequest.getMobileNumber();
        String company = businessProfileRequest.getCompany();

        Boolean isExists = businessProfileService.existByMobileNumber(mobileNumber);
        //SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
        if (isExists) {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E101.name()), HttpStatus.OK);
        }

        Boolean isExistByCompany = businessProfileService.existByCompany(company);
        if (isExistByCompany) {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E105.name()), HttpStatus.OK);

        }
        //String dateOfBirth = sd.format(businessProfileRequest.getDob());

        BusinessProfileDto businessProfileDto = new BusinessProfileDto();
        businessProfileDto.setFirstName(businessProfileRequest.getFirstName())
                .setLastName(businessProfileRequest.getLastName())
                .setCompany(businessProfileRequest.getCompany())
                .setCountry(businessProfileRequest.getCountry())
                .setDob(businessProfileRequest.getDob())
                .setEmailId(businessProfileRequest.getEmailId())
                .setGender(businessProfileRequest.getGender())
                .setCountryCode(businessProfileRequest.getCountryCode())
                .setMobileNumber(mobileNumber)
                .setRegId(businessProfileRequest.getRegId())
                .setGsaNumber(businessProfileRequest.getGsaNumber())
                .setEmployeeCount(businessProfileRequest.getEmployeeCount())
                .setIsAdmin(businessProfileRequest.getIsAdmin());
        Boolean isInserted = businessProfileService.insertRegisteredDetails(businessProfileDto);
        int isAdmin = businessProfileRequest.getIsAdmin();
        if (AppConstants.ANOTHER_ADMIN == isAdmin) {
            String anotherMobileNumber = businessProfileRequest.getAnotherAdminMobileNumber();

            Boolean isAlreadyExist = adminService.adminByMobileNumberAndCompany(anotherMobileNumber, company);
            if (isAlreadyExist) {
                return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E103.name()), HttpStatus.OK);
            }
            AdminDto adminDto = new AdminDto();
            adminDto.setAdminName(businessProfileRequest.getAnotherAdminName())
                    .setAdminEmailId(businessProfileRequest.getAnotherAdminEmailId());
            adminDto.setAdminMobileNumber(anotherMobileNumber);
            adminDto.setCompany(businessProfileRequest.getCompany());

            Boolean isAdminInserted = adminService.insertAdmin(adminDto);
            if (null == isAdminInserted || !isAdminInserted) {
                return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E102.name()), HttpStatus.OK);
            }

        }
        if (AppConstants.SELF_ADMIN == isAdmin) {

            Boolean isAlreadyExist = adminService.adminByMobileNumberAndCompany(mobileNumber, company);
            if (isAlreadyExist) {
                return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E103.name()), HttpStatus.OK);
            }
            Long userId = businessProfileService.getUserId(mobileNumber);
            AdminDto adminDto = new AdminDto();
            String name = businessProfileRequest.getFirstName() + " " + businessProfileRequest.getLastName();
            adminDto.setAdminName(name)
                    .setAdminEmailId(businessProfileRequest.getEmailId());
            adminDto.setAdminMobileNumber(mobileNumber);
            adminDto.setCompany(businessProfileRequest.getCompany());
            Boolean isAdminInserted = adminService.insertAdmin(adminDto);
            if (null == isAdminInserted || !isAdminInserted) {
                return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E102.name()), HttpStatus.OK);
            }
        }
        if (isInserted)
            return new ResponseEntity<>(new ApiResponse(true, "Sucessfully inserted"), HttpStatus.OK);
        else {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E102.name()), HttpStatus.OK);
        }

    }
}