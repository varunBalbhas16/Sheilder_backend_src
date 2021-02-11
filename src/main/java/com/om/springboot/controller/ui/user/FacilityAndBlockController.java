package com.om.springboot.controller.ui.user;

import com.om.springboot.controller.request.AddBlockRequest;
import com.om.springboot.controller.request.AddFacilityRequest;
import com.om.springboot.dto.model.user.*;
import com.om.springboot.dto.response.ApiResponse;
import com.om.springboot.dto.response.user.FacilityRequest;
import com.om.springboot.dto.response.user.FacilityResponse;
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
public class FacilityAndBlockController {

    @Autowired
    AdminService adminService;

    @Autowired
    UserAuthenticationService userAuthenticationService;

    @Autowired
    FacilityService facilityService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    BlockService blockService;


    @CrossOrigin
    @PostMapping("/postLogin/addFacility")
    public ResponseEntity<?> addFacility(@Valid @RequestBody AddFacilityRequest addFacilityRequest) {

        String adminMobileNumber = addFacilityRequest.getAdminMobileNumber();

        AdminDto adminDto = adminService.getAdminDetails(adminMobileNumber);
        if (null == adminDto) {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E110.name()), HttpStatus.OK);
        }

        String company = adminDto.getCompany();
        UserAuthenticationDto userAuthenticationDto = userAuthenticationService.checkAuthentication(adminMobileNumber);
        if (null == userAuthenticationDto) {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E108.name()), HttpStatus.OK);
        }

        int isLoggedIN = userAuthenticationDto.getIsLoggedIn();
        if (isLoggedIN == AppConstants.LOGIN) {
            Boolean isEmployeePresent = employeeService.existByMobile(addFacilityRequest.getMobileNumber());
            if (isEmployeePresent) {

                EmployeeDto employeeDto = employeeService.getEmployeeDetails(addFacilityRequest.getMobileNumber());
                int otpVerified = employeeDto.getOtpVerified();

                if (otpVerified == AppConstants.OTP_VERIFIED) {
                    String facilityName = addFacilityRequest.getFacilityName();
                    Boolean isFacilityExist = facilityService.existByFacilityAndCompany(facilityName, company);
                    if (isFacilityExist) {
                        return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E118.name()), HttpStatus.OK);
                    }

                    FacilityDto facilityDto = new FacilityDto();
                    facilityDto.setFacilityName(addFacilityRequest.getFacilityName())
                            .setFacilityAddress(addFacilityRequest.getFacilityAddress())
                            .setInCharge(addFacilityRequest.getInCharge())
                            .setCountryCode(addFacilityRequest.getCountryCode())
                            .setMobileNumber(addFacilityRequest.getMobileNumber())
                            .setCompany(company);
                    Boolean isFacilityAdded = facilityService.addFacility(facilityDto);
                    if (isFacilityAdded) {
                        return new ResponseEntity<>(new ApiResponse(true, "Facility Added"), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E111.name()), HttpStatus.OK);
                    }
                } else {
                    return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E116.name()), HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E113.name()), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E108.name()), HttpStatus.OK);
        }
    }

    @CrossOrigin
    @GetMapping("/postLogin/getFacility/{adminMobileNumber}")
    public ResponseEntity<?> getFacility(@PathVariable String adminMobileNumber) {
        AdminDto adminDto = adminService.getAdminDetails(adminMobileNumber);
        if (null == adminDto) {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E110.name()), HttpStatus.OK);
        }

        String company = adminDto.getCompany();
        List<FacilityDto> facilityDtoList = facilityService.getFacilityByCompany(company);
        if (null == facilityDtoList || facilityDtoList.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E117.name()), HttpStatus.OK);
        }

        List<FacilityRequest> facilityRequestList = new ArrayList<>();
        FacilityResponse facilityResponse = new FacilityResponse(true, "");

        for (FacilityDto facilityDto1 : facilityDtoList) {
            FacilityRequest facilityRequest = new FacilityRequest();
            facilityRequest.setSafeAccessId(facilityDto1.getSafeAccessId())
                    .setFacilityName(facilityDto1.getFacilityName())
                    .setFacilityAddress(facilityDto1.getFacilityAddress())
                    .setInChargeName(facilityDto1.getInCharge())
                    .setMobileNumber(facilityDto1.getMobileNumber());
            facilityRequestList.add(facilityRequest);
        }
        facilityResponse.setFacilityRequestList(facilityRequestList);
        return new ResponseEntity<>(facilityResponse, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/postLogin/addBlock")
    public ResponseEntity<?> addBlock(@Valid @RequestBody AddBlockRequest addBlockRequest) {
        String adminMobileNumber = addBlockRequest.getAdminMobileNumber();
        AdminDto adminDto = adminService.getAdminDetails(adminMobileNumber);
        if (null == adminDto) {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E110.name()), HttpStatus.OK);
        }

        String company = adminDto.getCompany();
        String facility = addBlockRequest.getFacilityName();
        Boolean isExist = facilityService.existByFacilityAndCompany(facility, company);
        if (isExist) {
            FacilityDto facilityDto = facilityService.getByFacilityAndCompany(facility, company);
            if (null == facilityDto) {
                return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E117.name()), HttpStatus.OK);
            }
            String facilitySafeAccessId = facilityDto.getSafeAccessId();

            BlockDto blockDto = new BlockDto();
            blockDto.setFacilitySafeAccessId(facilitySafeAccessId)
                    .setBlockName(addBlockRequest.getBlockName())
                    .setBlockNumber(addBlockRequest.getBlockNumber())
                    .setFacilityName(facility)
                    .setCompany(company);

            Boolean isBlockAdded = blockService.addBlock(blockDto);
            if (isBlockAdded) {
                return new ResponseEntity<>(new ApiResponse(true, "Block Added"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E111.name()), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E117.name()), HttpStatus.OK);
        }
    }
}
