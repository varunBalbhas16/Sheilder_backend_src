package com.om.springboot.mappers.user;


import com.om.springboot.model.user.StatusMaster;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StatusMasterMapper {

    StatusMaster getByStatus(String status);
    StatusMaster getStatusById(int statusId);


}
