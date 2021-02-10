package com.om.springboot.service.user;

import com.om.springboot.dto.model.user.StatusMasterDto;


public interface StatusMasterService {
    StatusMasterDto getStatusFromMaster(String status);
    StatusMasterDto getStatusById(int statusId);
}
