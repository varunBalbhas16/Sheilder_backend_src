package com.om.springboot.service.user;

import com.om.springboot.dto.mapper.user.StatusMasterDtoMapper;
import com.om.springboot.dto.model.user.StatusMasterDto;
import com.om.springboot.mappers.user.StatusMasterMapper;
import com.om.springboot.model.user.StatusMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class StatusMasterServiceImpl implements StatusMasterService{
    @Autowired
    @Qualifier("statusMasterMapper")
    StatusMasterMapper statusMasterMapper;

    @Override
    public StatusMasterDto getStatusFromMaster(String status){
        System.out.println("Status >> "+status);
        return StatusMasterDtoMapper.toDto(statusMasterMapper.getByStatus(status));
    }

    @Override
    public StatusMasterDto getStatusById(int statusId){
        return StatusMasterDtoMapper.toDto(statusMasterMapper.getStatusById(statusId));
    }

}
