package com.om.springboot.mappers.user;

import com.om.springboot.model.user.Block;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlockMapper {
    Boolean addBlock(Block block);

}
