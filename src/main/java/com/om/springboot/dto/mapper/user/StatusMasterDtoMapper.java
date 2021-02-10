package com.om.springboot.dto.mapper.user;

import com.om.springboot.dto.model.user.StatusMasterDto;
import com.om.springboot.model.user.StatusMaster;

public class StatusMasterDtoMapper {
    public static StatusMasterDto toDto(StatusMaster statusMaster) {
        System.out.println(statusMaster);
        return new StatusMasterDto()
                .setStatusId(statusMaster.getStatusId())
                .setStatus(statusMaster.getStatus());
    }
}
