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
@Table(name="facility")
public class Facility extends DateAudit {
    @Id
    private int id;

    private String safeAccessId;

    private String facilityName;
    private String facilityAddress;
    private String inCharge;
    private String countryCode;
    private String mobile;
    private String company;

}
