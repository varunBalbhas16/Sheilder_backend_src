package com.om.springboot.service.user;

import com.om.springboot.dto.model.user.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    Boolean addEmployeeDetails(EmployeeDto employeeDto);
     List<EmployeeDto> getAllEmployeeDetails(String company);
    EmployeeDto getEmployeeDetails(String mobileNumber);
    Boolean updateOtpVerify(EmployeeDto employeeDto);
    Boolean existByMobile(String mobileNumber);
}
