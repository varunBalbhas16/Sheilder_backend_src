package com.om.springboot.controller.ui.user;

import com.om.springboot.controller.request.BusinessProfileRequest;
import com.om.springboot.dto.model.user.BusinessProfileDto;
import com.om.springboot.dto.response.ApiResponse;
import com.om.springboot.service.user.BusinessProfileService;
import com.om.springboot.util.ErrorConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@RequestMapping("/api/shielder")
public class BusinessProfileController {

    @Autowired
    BusinessProfileService businessProfileService;

    @CrossOrigin
    @PostMapping("/postLogin/businessProfileRegisteration")
    public ResponseEntity<?> businessRegisteration(@Valid @RequestBody BusinessProfileRequest businessProfileRequest) {
        Boolean isExists = businessProfileService.existByMobileNumber(businessProfileRequest.getMobileNumber());
        //SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
        if (isExists && null != isExists) {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E101.name()), HttpStatus.OK);
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
                .setMobileNumber(businessProfileRequest.getMobileNumber())
                .setRegId(businessProfileRequest.getRegId())
                .setGsaNumber(businessProfileRequest.getGsaNumber())
                .setEmployeeCount(businessProfileRequest.getEmployeeCount())
                .setIsAdmin(businessProfileRequest.getIsAdmin());
        Boolean isInserted = businessProfileService.insertRegisteredDetails(businessProfileDto);
        int isAdmin= businessProfileRequest.getIsAdmin();
        if(0==isAdmin){

        }
        if (isInserted)
            return new ResponseEntity<>(new ApiResponse(true, "Sucessfully inserted"), HttpStatus.OK);
        else {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E102.name()), HttpStatus.OK);
        }

    }
}

