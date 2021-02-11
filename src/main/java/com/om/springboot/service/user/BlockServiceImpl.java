package com.om.springboot.service.user;

import com.om.springboot.dto.model.user.BlockDto;
import com.om.springboot.mappers.user.BlockMapper;
import com.om.springboot.model.user.Block;
import com.om.springboot.service.Application.SafeAccessServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public class BlockServiceImpl implements BlockService{

    @Autowired
    @Qualifier("blockMapper")
    BlockMapper blockMapper;

    @Autowired
    SafeAccessServiceImpl safeAccessService;

    public Boolean addBlock(BlockDto blockDto){
        Block block=new Block();
        block.setSafeAccessId(safeAccessService.getSafeAccessId());
        block.setBlockName(blockDto.getBlockName());
        block.setBlockNumber(blockDto.getBlockNumber());
        block.setFacilityName(blockDto.getFacilityName());
        block.setFacilityId(blockDto.getFacilitySafeAccessId());
        block.setCompany(blockDto.getCompany());
        Boolean isAdded=blockMapper.addBlock(block);
        if(null==isAdded||!isAdded){
            return false;
        }
        else{
            return true;
        }
    }
}
