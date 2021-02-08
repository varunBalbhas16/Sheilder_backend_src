package com.om.springboot.model.user;

import com.om.springboot.model.audit.DateAudit;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "business_profile", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email_id", "mobile_number","regId"
        })
})
public class BusinessProfile extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotEmpty
    @Size(min = 1, max = 50)
    private String firstName;

    @NotEmpty
    @Size(min = 1, max = 50)
    private String lastName;

    @NotEmpty
    @Size(min = 1, max = 15)
    private String gender;

    private Date dob;

    @NotEmpty
    @Size(min = 1, max = 45)
    private String country;

    @NotEmpty
    @Size(min=1,max=10)
    private String countryCode;

    @NotEmpty
    @Size(min = 1, max = 15)
    private String mobileNumber;

    @NotEmpty
    @Size(min = 1, max = 50)
    private String emailId;

    @NotEmpty
    @Size(min = 1, max = 30)
    private String company;

    @NotNull
    private Long regId;

    @NotEmpty
    @Size(min = 1, max = 30)
    private String gsaNumber;

    @NotNull
    private Long employeeCount;

    @NotNull
    private int isAdmin;
}
