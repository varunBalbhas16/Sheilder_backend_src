package com.om.springboot.model.user;

import com.om.springboot.model.audit.DateAudit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shielderId;
    private String employeeId;
    private String name;
    private String countryCode;
    private String mobileNumber;
    private String emailId;
}
