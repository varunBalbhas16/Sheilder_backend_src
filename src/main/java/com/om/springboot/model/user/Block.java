package com.om.springboot.model.user;

import com.om.springboot.model.audit.DateAudit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name="block")
public class Block extends DateAudit {
    @Id
    private int id;
    private String safeAccessId;
    private String facilityName;
    private String blockName;
    private String blockNumber;
    private String facilityId;
    private String company;
}
