package com.om.springboot.mappers.user;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SafeAccessMapper {
    String getSafeAccessId();
    Boolean insertSafeAccessId(String safeAccessId);
}
