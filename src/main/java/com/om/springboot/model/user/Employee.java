package com.om.springboot.model.user;

import com.om.springboot.model.audit.DateAudit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@Entity
@Table(name = "employee", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"SHIELDER_ID", "MOBILE"
        })
})
public class Employee extends DateAudit {
    @Id
    private String shielderId;

    @NotEmpty
    @Size(min = 1, max = 50)
    private String employeeId;

    @NotEmpty
    @Size(min = 1, max = 50)
    private String name;

    @NotEmpty
    @Size(min = 1, max = 10)
    private String countryCode;

    @NotEmpty
    @Size(min = 1, max = 20)
    private String mobile;

    @NotEmpty
    @Size(min = 1, max = 50)
    private String emailId;

    @NotEmpty
    @Size(min = 1, max = 50)
    private String company;

    @NotNull
    private int statusId;

    private int otpVerified;


}
