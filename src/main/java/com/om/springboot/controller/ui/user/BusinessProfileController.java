package com.om.springboot.controller.ui.user;

import com.om.springboot.controller.request.AddEmployeeRequest;
import com.om.springboot.controller.request.BusinessProfileRequest;
import com.om.springboot.controller.request.OtpRequest;
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
@RequestMapping("/api/shielder")
public class BusinessProfileController {

    @Autowired
    BusinessProfileService businessProfileService;

    @Autowired
    AdminService adminService;

    @Autowired
    UserAuthenticationService userAuthenticationService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    StatusMasterService statusMasterService;

    @Autowired
    OtpService otpService;

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

    @CrossOrigin
    @PostMapping("/postLogin/addEmployee")
    public ResponseEntity<?> addEmployee(@Valid @RequestBody AddEmployeeRequest addEmployeeRequest) {
        String adminMobileNumber = addEmployeeRequest.getAdminMobileNumber();

        AdminDto adminDto = adminService.getAdminDetails(adminMobileNumber);
        if (null == adminDto) {
            return new ResponseEntity(new ApiResponse(false, ErrorConstants.E110.name()), HttpStatus.OK);
        }

        String company = adminDto.getCompany();

        UserAuthenticationDto userAuthenticationDto = userAuthenticationService.checkAuthentication(adminMobileNumber);
        if (null == userAuthenticationDto) {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E108.name()), HttpStatus.OK);
        }

        int isLoggedIN = userAuthenticationDto.getIsLoggedIn();

        if (isLoggedIN == AppConstants.LOGIN) {
            Boolean isEmployeePresent = employeeService.existByMobile(addEmployeeRequest.getMobileNumber());
            if (isEmployeePresent) {
                return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E112.name()), HttpStatus.OK);
            }
            StatusMasterDto statusMasterDto = statusMasterService.getStatusFromMaster(AppConstants.UPLOAD);
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setEmployeeId(addEmployeeRequest.getEmployeeId())
                    .setName(addEmployeeRequest.getName())
                    .setEmailId(addEmployeeRequest.getEmailId())
                    .setCountryCode(addEmployeeRequest.getCountryCode())
                    .setMobileNumber(addEmployeeRequest.getMobileNumber())
                    .setStatusId(statusMasterDto.getStatusId())
                    .setCompany(company);
            System.out.println("Employee Profile " + employeeDto);

            Boolean isUploaded = employeeService.addEmployeeDetails(employeeDto);

            if (isUploaded) {

                return new ResponseEntity<>(new ApiResponse(true, "Uploaded"), HttpStatus.OK);

            } else {
                return new ResponseEntity(new ApiResponse(false, ErrorConstants.E111.name()), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(new ApiResponse(false, ErrorConstants.E108.name()), HttpStatus.OK);
        }
    }

    @CrossOrigin
    @GetMapping("/postLogin/getEmployee/{adminMobileNumber}")
    public ResponseEntity<?> getEmployee(@PathVariable String adminMobileNumber) {
        AdminDto adminDto = adminService.getAdminDetails(adminMobileNumber);

        if (null == adminDto) {
            return new ResponseEntity(new ApiResponse(false, ErrorConstants.E110.name()), HttpStatus.OK);
        }
        String company = adminDto.getCompany();

        EmployeeRequest employeeRequest = new EmployeeRequest();
        EmployeeResponse employeeResponse = new EmployeeResponse(true, "");
        List<EmployeeRequest> finalList = new ArrayList<>();

        List<EmployeeDto> employeeDtoList = employeeService.getAllEmployeeDetails(company);
        if (null == employeeDtoList || employeeDtoList.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E113.name()), HttpStatus.OK);
        }
        System.out.println(employeeDtoList);

        for (EmployeeDto employeeDto1 : employeeDtoList) {
            StatusMasterDto statusMasterDto = statusMasterService.getStatusById(employeeDto1.getStatusId());
            String mobile = employeeDto1.getCountryCode() + " " + employeeDto1.getMobileNumber();

            employeeRequest.setEmployeeId(employeeDto1.getEmployeeId())
                    .setName(employeeDto1.getName())
                    .setEmailId(employeeDto1.getEmailId())
                    .setMobileNumber(mobile)
                    .setShielderId(employeeDto1.getShielderId())
                    .setStatus(statusMasterDto.getStatus());
            finalList.add(employeeRequest);
        }
        employeeResponse.setEmployeeRequests(finalList);
        return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/postLogin/sendOtp")
    public ResponseEntity sendOtp(@Valid @RequestBody OtpRequest otpRequest) {
        String otp = null;
        String mobileNumber = otpRequest.getMobileNumber();
        OtpResponse otpResponse = new OtpResponse(true, "");
        Boolean isExist = otpService.otpExistByMobileNumber(mobileNumber);
        if (!isExist) {
            otp = otpService.insertOtp(mobileNumber);
        } else if (isExist) {
            otp = otpService.updateOtp(mobileNumber);
            if (otp == null) {
                return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E104.name()), HttpStatus.OK);
            }
        }
        otpResponse.setMobileNumber(mobileNumber).setOtp(otp);
        return new ResponseEntity<>(otpResponse, HttpStatus.OK);
    }
}


