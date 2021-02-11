package com.om.springboot.controller.ui.user;

import com.om.springboot.controller.request.AddEmployeeRequest;
import com.om.springboot.controller.request.OtpRequest;
import com.om.springboot.controller.request.auth.ValidateOtpRequest;
import com.om.springboot.dto.model.user.*;
import com.om.springboot.dto.response.ApiResponse;
import com.om.springboot.dto.response.OtpResponse;
import com.om.springboot.dto.response.user.CountryResponse;
import com.om.springboot.dto.response.user.EmployeeRequest;
import com.om.springboot.dto.response.user.EmployeeResponse;
import com.om.springboot.service.user.*;
import com.om.springboot.util.AppConstants;
import com.om.springboot.util.ErrorConstants;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/safeAccess")
public class EmployeeController {

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

    @Autowired
    CountriesService countriesService;

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
                    .setCompany(company)
                    .setOtpVerified(0);
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
    @PostMapping("/postLogin/uploadEmployee")
    public ResponseEntity<?> uploadEmployee(@RequestParam("mobile") String mobile,
                                            @RequestParam(value = "template") MultipartFile excelData) throws IOException {
        String adminMobileNumber = mobile;
        String empMobile = null;
        int checkUpload = 0;
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

            StatusMasterDto statusMasterDto = statusMasterService.getStatusFromMaster(AppConstants.UPLOAD);

            XSSFWorkbook workbook = new XSSFWorkbook(excelData.getInputStream());
            XSSFSheet worksheet = workbook.getSheetAt(0);

            for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
                EmployeeDto employeeDto = new EmployeeDto();
                XSSFRow row = worksheet.getRow(i);

                DecimalFormat df = new DecimalFormat("#");
                df.setMaximumFractionDigits(0);

                if (row.getCell(0).getCellType() == Cell.CELL_TYPE_STRING)
                    employeeDto.setEmployeeId(row.getCell(0).getStringCellValue());

                else if (row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC)
                    employeeDto.setEmployeeId(df.format(row.getCell(0).getNumericCellValue()));

                if (row.getCell(1).getCellType() == Cell.CELL_TYPE_STRING)
                    employeeDto.setName(row.getCell(1).getStringCellValue());

                else if (row.getCell(1).getCellType() == Cell.CELL_TYPE_NUMERIC)
                    employeeDto.setName(df.format(row.getCell(1).getNumericCellValue()));

                if (row.getCell(2).getCellType() == Cell.CELL_TYPE_STRING)
                    employeeDto.setCountryCode("+" + row.getCell(2).getStringCellValue());

                else if (row.getCell(2).getCellType() == Cell.CELL_TYPE_NUMERIC)
                    employeeDto.setCountryCode("+" + df.format(row.getCell(2).getNumericCellValue()));

                if (row.getCell(3).getCellType() == Cell.CELL_TYPE_STRING) {
                    empMobile = row.getCell(3).getStringCellValue();
                    employeeDto.setMobileNumber(empMobile);
                } else if (row.getCell(3).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    empMobile = df.format(row.getCell(3).getNumericCellValue());
                    employeeDto.setMobileNumber(empMobile);
                }

                if (row.getCell(4).getCellType() == Cell.CELL_TYPE_STRING)
                    employeeDto.setEmailId(row.getCell(4).getStringCellValue());

                else if (row.getCell(4).getCellType() == Cell.CELL_TYPE_NUMERIC)
                    employeeDto.setEmailId(df.format(row.getCell(4).getNumericCellValue()));


                employeeDto.setStatusId(statusMasterDto.getStatusId());
                employeeDto.setCompany(company);
                employeeDto.setOtpVerified(0);

                Boolean isEmployeePresent = employeeService.existByMobile(empMobile);
                if (isEmployeePresent) {
                    return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E112.name()), HttpStatus.OK);
                }

                Boolean isUploaded = employeeService.addEmployeeDetails(employeeDto);
                if (!isUploaded) {
                    checkUpload++;
                    return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E111.name()), HttpStatus.OK);
                }
            }
            if (checkUpload == 0) {
                return new ResponseEntity<>(new ApiResponse(true, "All data's uploaded successfully"), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E108.name()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E111.name()), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/postLogin/getEmployee/{adminMobileNumber}")
    public ResponseEntity<?> getEmployee(@PathVariable String adminMobileNumber) {
        AdminDto adminDto = adminService.getAdminDetails(adminMobileNumber);

        if (null == adminDto) {
            return new ResponseEntity(new ApiResponse(false, ErrorConstants.E110.name()), HttpStatus.OK);
        }
        String company = adminDto.getCompany();


        EmployeeResponse employeeResponse = new EmployeeResponse(true, "");
        List<EmployeeRequest> finalList = new ArrayList<>();

        List<EmployeeDto> employeeDtoList = employeeService.getAllEmployeeDetails(company);
        if (null == employeeDtoList || employeeDtoList.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E113.name()), HttpStatus.OK);
        }
        System.out.println(employeeDtoList);

        for (EmployeeDto employeeDto1 : employeeDtoList) {
            EmployeeRequest employeeRequest = new EmployeeRequest();
            StatusMasterDto statusMasterDto = statusMasterService.getStatusById(employeeDto1.getStatusId());
            String mobile = employeeDto1.getCountryCode() + " " + employeeDto1.getMobileNumber();

            employeeRequest.setEmployeeId(employeeDto1.getEmployeeId())
                    .setName(employeeDto1.getName())
                    .setEmailId(employeeDto1.getEmailId())
                    .setMobileNumber(mobile)
                    .setSafeAccessId(employeeDto1.getShielderId())
                    .setStatus(statusMasterDto.getStatus());
            finalList.add(employeeRequest);

        }
        System.out.println("Final list" + finalList);
        employeeResponse.setEmployeeRequests(finalList);
        return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/postLogin/sendEmpOtp")
    public ResponseEntity<?> sendOtp(@Valid @RequestBody OtpRequest otpRequest) {
        String otp = null;
        String mobileNumber = otpRequest.getMobileNumber();
        EmployeeDto employeeDto = employeeService.getEmployeeDetails(mobileNumber);
        OtpResponse otpResponse = new OtpResponse(true, "");

        if (null != employeeDto) {
            int isVerified = employeeDto.getOtpVerified();

            if (isVerified != AppConstants.OTP_VERIFIED) {

                Boolean isExist = otpService.otpExistByMobileNumber(mobileNumber);
                if (!isExist) {
                    otp = otpService.insertOtp(mobileNumber);
                } else {
                    otp = otpService.updateOtpWRC(mobileNumber);
                }
                otpResponse.setMobileNumber(mobileNumber).setOtp(otp);
                return new ResponseEntity<>(otpResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E115.name()), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E113.name()), HttpStatus.OK);
        }
    }


    @CrossOrigin
    @PostMapping("/postLogin/validateEmpOtp")
    public ResponseEntity<?> validateEmpOtp(@Valid @RequestBody ValidateOtpRequest validateOtpRequest) {
        String mobile = validateOtpRequest.getMobileNumber();
        String otp = validateOtpRequest.getOtp();
        EmployeeDto employeeDto = employeeService.getEmployeeDetails(mobile);

        if (null != employeeDto) {

            Boolean isValid = otpService.validateOtp(mobile, otp);

            if (isValid) {

                StatusMasterDto statusMasterDto = statusMasterService.getStatusFromMaster(AppConstants.ADD);
                EmployeeDto employeeDto1 = new EmployeeDto();
                employeeDto1.setOtpVerified(AppConstants.OTP_VERIFIED)
                        .setStatusId(statusMasterDto.getStatusId())
                        .setShielderId(employeeDto.getShielderId());
                Boolean isUpdated = employeeService.updateOtpVerify(employeeDto1);
                if (isUpdated)
                    return new ResponseEntity<>(new ApiResponse(true, "Valid"), HttpStatus.OK);

            } else {

                return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E107.name()), HttpStatus.OK);

            }

        }
        return new ResponseEntity<>(new ApiResponse(false, ErrorConstants.E113.name()), HttpStatus.OK);
    }

   /* @CrossOrigin
    @GetMapping("/countries")
    public ResponseEntity<?> displayCountries() {
        List<CountryDto> countryDtoList = countriesService.getAllCountries();
        CountryResponse countryResponse=new CountryResponse(true,"");
        countryResponse.setCountries(countryDtoList);
        return new ResponseEntity<>(countryResponse,HttpStatus.OK);
    }*/


}




