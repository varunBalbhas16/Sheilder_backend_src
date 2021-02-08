package com.om.springboot.service.user;

import com.om.springboot.dto.model.user.EmployeeDto;
import com.om.springboot.model.user.Employee;
import com.om.springboot.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    @Qualifier("employeeMapper")
    EmployeeMapper employeeMapper;

    @Override
    public Boolean addEmployeeDetails(EmployeeDto employeeDto){
        Employee employee=new Employee();
        employee.setEmployeeId(employeeDto.getEmployeeId());
        employee.setEmailId(employeeDto.getEmailId());
        employee.setCountryCode(employeeDto.getCountryCode());
        employee.setMobileNumber(employeeDto.getMobileNumber());
        employee.setName(employeeDto.getName());
        Long shieldId=this.getMaxShieldId();
        if(null==shieldId){
            shieldId=AppConstants.EMPLOYEE_SHIELDER_ID;
        }
        else {
            shieldId=this.getMaxShieldId();
        }
        employee.setShielderId(shieldId);
    }

    @Transactional
    private synchronized Long getMaxShieldId(){
        Long shId=employeeMapper.getMaxShieldId();
        if(null==shId){
            return null;
        }
        shId+=1L;
        return shId;
    }
}
