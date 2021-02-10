package com.om.springboot.dto.mapper.user;

import com.om.springboot.dto.model.user.EmployeeDto;
import com.om.springboot.model.user.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDtoMapper {
    public static EmployeeDto toDto(Employee employee) {
        System.out.println("EmpId " + employee.getEmployeeId());
        return new EmployeeDto()
                .setShielderId(employee.getShielderId())
                .setEmployeeId(employee.getEmployeeId())
                .setName(employee.getName())
                .setCountryCode(employee.getCountryCode())
                .setMobileNumber(employee.getMobile())
                .setEmailId(employee.getEmailId())
                .setCompany(employee.getCompany())
                .setStatusId(employee.getStatusId())
                .setOtpVerified(employee.getOtpVerified());
    }

    public static List<EmployeeDto> toDtoList(List<Employee> allEmployee) {
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        if (null != allEmployee) {
            for (Employee employee : allEmployee) {
                employeeDtoList.add(toDto(employee));
            }
        }
        return employeeDtoList;
    }
}
