package com.om.springboot.mappers.user;

import com.om.springboot.model.user.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    String getMaxShieldId();

    Boolean addEmployee(Employee employee);

    Employee getEmployee(String mobileNumber);

    List<Employee> getAllEmployee(String company);

    Boolean existByMobile(String mobileNumber);

    Boolean updateOtpVerify(Employee employee);
}
