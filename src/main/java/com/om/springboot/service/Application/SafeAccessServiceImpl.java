package com.om.springboot.service.Application;

import com.om.springboot.mappers.user.SafeAccessMapper;
import com.om.springboot.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SafeAccessServiceImpl {

    @Autowired
    @Qualifier("safeAccessMapper")
    SafeAccessMapper safeAccessMapper;

    @Transactional
    public synchronized String getSafeAccessId() {
        String safeAccessId = safeAccessMapper.getSafeAccessId();
        if (null == safeAccessId || safeAccessId.isEmpty()) {
            safeAccessId = AppConstants.SAFE_ACCESS_ID;
            Boolean isInserted = safeAccessMapper.insertSafeAccessId(safeAccessId);
            if (null != isInserted && isInserted) {
                System.out.println("Safe AccessId inserted");
            }
            return safeAccessId;
        }
        Long safeId = Long.parseLong(safeAccessId);
        safeId += 1L;
        safeAccessId = safeId.toString();
        Boolean isInserted = safeAccessMapper.insertSafeAccessId(safeAccessId);
        if (null != isInserted && isInserted) {
            System.out.println("Safe AccessId inserted");
        }
        return safeAccessId;
    }
}
