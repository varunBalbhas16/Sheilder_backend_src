package com.om.springboot.service.user;

import com.om.springboot.dto.model.user.EmployeeDto;

public interface EmployeeService {
    Boolean addEmployeeDetails(EmployeeDto employeeDto);
}
