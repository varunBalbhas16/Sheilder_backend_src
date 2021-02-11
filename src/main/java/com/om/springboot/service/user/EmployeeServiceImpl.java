package com.om.springboot.service.user;

import com.om.springboot.dto.mapper.user.EmployeeDtoMapper;
import com.om.springboot.dto.model.user.EmployeeDto;
import com.om.springboot.mappers.user.EmployeeMapper;
import com.om.springboot.model.user.Employee;
import com.om.springboot.model.user.SafeAccess;
import com.om.springboot.service.Application.SafeAccessServiceImpl;
import com.om.springboot.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    @Qualifier("employeeMapper")
    EmployeeMapper employeeMapper;

    @Autowired
    SafeAccessServiceImpl safeAccessService;

    @Override
    public Boolean addEmployeeDetails(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeDto.getEmployeeId());
        employee.setEmailId(employeeDto.getEmailId());
        employee.setCountryCode(employeeDto.getCountryCode());
        employee.setMobile(employeeDto.getMobileNumber());
        employee.setName(employeeDto.getName());
        employee.setStatusId(employeeDto.getStatusId());
        employee.setCompany(employeeDto.getCompany());
        employee.setOtpVerified(employeeDto.getOtpVerified());
        String shieldId = safeAccessService.getSafeAccessId();

        employee.setShielderId(shieldId);
        Boolean isAdded = employeeMapper.addEmployee(employee);
        if (null == isAdded || !isAdded) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public EmployeeDto getEmployeeDetails(String mobileNumber){
        return EmployeeDtoMapper.toDto(employeeMapper.getEmployee(mobileNumber));
    }

    @Override
    public List<EmployeeDto> getAllEmployeeDetails(String company){
        return EmployeeDtoMapper.toDtoList(employeeMapper.getAllEmployee(company));
    }

    @Override
    public Boolean existByMobile(String mobileNumber){
        Boolean isExist=employeeMapper.existByMobile(mobileNumber);
        if(null==isExist || !isExist){
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public Boolean updateOtpVerify(EmployeeDto employeeDto){
        Employee employee=new Employee();
        employee.setOtpVerified(employeeDto.getOtpVerified());
        employee.setStatusId(employeeDto.getStatusId());
        employee.setShielderId(employeeDto.getShielderId());
        Boolean isUpdated=employeeMapper.updateOtpVerify(employee);
        if(null==isUpdated || !isUpdated){
            return false;
        }
        else {
            return true;
        }
    }


}
